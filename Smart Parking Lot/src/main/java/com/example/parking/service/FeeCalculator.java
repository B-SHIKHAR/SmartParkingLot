package main.java.com.example.parking.service;

import main.java.com.example.parking.domain.RateRule;
import main.java.com.example.parking.domain.enums.VehicleType;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;

@Component
public class FeeCalculator {

    public int computeFeeCents(Instant entry, Instant exit, VehicleType vehicleType, List<RateRule> rules, ZoneId zoneId) {
        long minutes = Duration.between(entry, exit).toMinutes();
        if (minutes < 0) minutes = 0;

        // Pick first matching rule by simple conditions; production would segment across time bands
        RateRule rule = rules.stream()
                .filter(r -> matchBand(r, entry, exit, zoneId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No matching rate rule"));

        if (minutes <= (rule.getGraceMinutes() == null ? 0 : rule.getGraceMinutes())) {
            return 0;
        }

        String unit = rule.getUnit();
        int fee = rule.getBaseCents();
        if ("FLAT".equals(unit)) {
            // baseCents covers entire stay
            fee = rule.getBaseCents();
        } else {
            int unitMinutes = "PER_30_MIN".equals(unit) ? 30 : 60;
            int extraMinutes = (int) Math.max(0, minutes - unitMinutes); // base covers first unit
            int units = (int) Math.ceil(extraMinutes / (double) unitMinutes);
            int perUnit = rule.getPerUnitCents() == null ? 0 : rule.getPerUnitCents();
            fee = rule.getBaseCents() + units * perUnit;
        }

        Integer cap = rule.getMaxDailyCents();
        if (cap != null) {
            long days = Math.max(1, Duration.between(entry, exit).toDaysPart() + (Duration.between(entry, exit).toHoursPart() > 0 ? 1 : 0));
            fee = Math.min(fee, cap * (int) days);
        }
        return fee;
    }

    private boolean matchBand(RateRule r, Instant entry, Instant exit, ZoneId zoneId) {
        if (Boolean.TRUE.equals(r.isWeekendOnly())) {
            DayOfWeek dow = LocalDateTime.ofInstant(entry, zoneId).getDayOfWeek();
            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) return false;
        }
        if (r.getStartTime() != null && r.getEndTime() != null) {
            LocalTime start = r.getStartTime();
            LocalTime end = r.getEndTime();
            LocalTime entryLocal = LocalDateTime.ofInstant(entry, zoneId).toLocalTime();
            if (entryLocal.isBefore(start) || entryLocal.isAfter(end)) return false;
        }
        return true;
    }
}
