package main.java.com.example.parking.repo;

import com.example.parking.domain.ParkingSpot;
import com.example.parking.domain.enums.SpotStatus;
import com.example.parking.domain.enums.SpotType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    @Query("select s from ParkingSpot s where s.status = :status and s.spotType = :type and s.reserved = false order by s.floor.level asc, s.spotCode asc")
    List<ParkingSpot> findAvailableByTypeOrdered(@Param("status") SpotStatus status, @Param("type") SpotType type);

    Optional<ParkingSpot> findBySpotCode(String spotCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ParkingSpot s where s.id = :id")
    Optional<ParkingSpot> lockById(@Param("id") Long id);
}
