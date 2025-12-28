package main.java.com.example.parking.repo;

import main.java.com.example.parking.domain.RatePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatePlanRepository extends JpaRepository<RatePlan, Long> {
    Optional<RatePlan> findByActiveTrue();
}
