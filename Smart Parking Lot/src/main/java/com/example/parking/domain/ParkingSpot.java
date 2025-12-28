package main.java.com.example.parking.domain;

import com.example.parking.domain.enums.SpotStatus;
import com.example.parking.domain.enums.SpotType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Getter @Setter @NoArgsConstructor
@Table(indexes = {
    @Index(name = "idx_spot_status_type_floor", columnList = "status, spotType, floor_id")
})
public class ParkingSpot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 32)
    private String spotCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotType spotType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotStatus status = SpotStatus.AVAILABLE;

    @ManyToOne(optional = false)
    private Floor floor;

    @ManyToOne
    private Zone zone;

    private boolean reserved = false;
    private Instant lastStatusChange = Instant.now();
}
