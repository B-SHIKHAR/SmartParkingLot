package main.java.com.example.parking.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {
    public static ZonedDateTime nowIn(ZoneId zoneId) {
        return ZonedDateTime.ofInstant(Instant.now(), zoneId);
    }
}
