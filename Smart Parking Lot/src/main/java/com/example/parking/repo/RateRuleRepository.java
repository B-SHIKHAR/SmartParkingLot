package main.java.com.example.parking.repo;

import main.java.com.example.parking.domain.RatePlan;
import main.java.com.example.parking.domain.RateRule;
import com.example.parking.domain.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRuleRepository extends JpaRepository<RateRule, Long> {
    List<RateRule> findByRatePlanAndVehicleType(RatePlan plan, VehicleType type);
}
