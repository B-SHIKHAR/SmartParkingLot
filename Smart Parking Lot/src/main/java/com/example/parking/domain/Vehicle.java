package main.java.com.example.parking.domain;

import com.example.parking.domain.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor
@Table(indexes = @Index(name="idx_vehicle_plate", columnList="plate", unique = true))
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 24, unique = true)
    private String plate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;
}
