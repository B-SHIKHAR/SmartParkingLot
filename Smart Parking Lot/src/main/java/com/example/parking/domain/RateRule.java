package main.java.com.example.parking.domain;

import com.example.parking.domain.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor
public class RateRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private RatePlan ratePlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private String unit; // PER_HOUR, PER_30_MIN, FLAT

    @Column(nullable = false)
    private Integer baseCents;

    private Integer perUnitCents;
    private Integer graceMinutes = 0;
    private Integer maxDailyCents; // optional cap

    private java.time.LocalTime startTime; // optional band
    private java.time.LocalTime endTime;   // optional band
    private boolean weekendOnly = false;
}
