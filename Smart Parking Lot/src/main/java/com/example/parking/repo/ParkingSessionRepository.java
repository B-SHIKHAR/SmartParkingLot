package main.java.com.example.parking.repo;

import main.java.com.example.parking.domain.ParkingSession;
import main.java.com.example.parking.domain.Vehicle;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    @Query("select ps from ParkingSession ps where ps.vehicle = :vehicle and ps.status = 'ACTIVE'")
    Optional<ParkingSession> findActiveByVehicle(@Param("vehicle") Vehicle vehicle);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select ps from ParkingSession ps where ps.id = :id")
    Optional<ParkingSession> lockById(@Param("id") Long id);
}
