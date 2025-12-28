package main.java.com.example.parking.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Getter @Setter @NoArgsConstructor
@Table(indexes = {
    @Index(name="idx_session_vehicle_active", columnList="vehicle_id, status"),
    @Index(name="idx_session_spot_active", columnList="spot_id, status")
})
public class ParkingSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Vehicle vehicle;

    @ManyToOne(optional = false)
    private ParkingSpot spot;

    @Column(nullable = false)
    private Instant entryTime;

    private Instant exitTime;

    @Column(nullable = false, length = 16)
    private String status; // ACTIVE, CLOSED, CANCELLED

    private Integer feeCents;
    private String currency = "INR";
    private String allocationPolicy;
}
