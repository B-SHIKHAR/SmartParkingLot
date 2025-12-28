package main.java.com.example.parking.service;

import main.java.com.example.parking.domain.RatePlan;
import main.java.com.example.parking.domain.RateRule;
import main.java.com.example.parking.domain.enums.VehicleType;
import main.java.com.example.parking.repo.RatePlanRepository;
import main.java.com.example.parking.repo.RateRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {
    private final RatePlanRepository planRepo;
    private final RateRuleRepository ruleRepo;

    public RateService(RatePlanRepository planRepo, RateRuleRepository ruleRepo) {
        this.planRepo = planRepo;
        this.ruleRepo = ruleRepo;
    }

    public RatePlan getActivePlanOrThrow() {
        return planRepo.findByActiveTrue().orElseThrow(() -> new IllegalStateException("No active rate plan"));
    }

    public List<RateRule> getRules(RatePlan plan, VehicleType type) {
        return ruleRepo.findByRatePlanAndVehicleType(plan, type);
    }
}
