package main.java.com.example.parking.service;

import main.java.com.example.parking.domain.ParkingSpot;
import main.java.com.example.parking.domain.enums.SpotStatus;
import main.java.com.example.parking.domain.enums.SpotType;
import main.java.com.example.parking.domain.enums.VehicleType;
import main.java.com.example.parking.repo.ParkingSpotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class AllocationService {

    private final ParkingSpotRepository spotRepo;

    public AllocationService(ParkingSpotRepository spotRepo) {
        this.spotRepo = spotRepo;
    }

    public List<SpotType> allowedSpotTypes(VehicleType vt) {
        return switch (vt) {
            case MOTORCYCLE -> List.of(SpotType.SMALL, SpotType.MEDIUM);
            case CAR -> List.of(SpotType.MEDIUM, SpotType.LARGE);
            case BUS -> List.of(SpotType.LARGE);
        };
    }

    @Transactional
    public ParkingSpot allocate(VehicleType vt, String policy) {
        for (SpotType type : allowedSpotTypes(vt)) {
            List<ParkingSpot> candidates = spotRepo.findAvailableByTypeOrdered(SpotStatus.AVAILABLE, type);
            for (ParkingSpot s : candidates) {
                // lock spot
                ParkingSpot locked = spotRepo.lockById(s.getId()).orElseThrow();
                if (locked.getStatus() == SpotStatus.AVAILABLE && !locked.isReserved()) {
                    locked.setStatus(SpotStatus.HELD);
                    locked.setLastStatusChange(Instant.now());
                    spotRepo.save(locked);
                    return locked;
                }
            }
        }
        throw new IllegalStateException("Lot full for vehicle type: " + vt);
    }

    @Transactional
    public void finalizeOccupancy(Long spotId) {
        ParkingSpot locked = spotRepo.lockById(spotId).orElseThrow();
        if (locked.getStatus() != SpotStatus.HELD) {
            throw new IllegalStateException("Spot not in HELD state");
        }
        locked.setStatus(SpotStatus.OCCUPIED);
        locked.setLastStatusChange(Instant.now());
        spotRepo.save(locked);
    }

    @Transactional
    public void release(Long spotId) {
        ParkingSpot locked = spotRepo.lockById(spotId).orElseThrow();
        locked.setStatus(SpotStatus.AVAILABLE);
        locked.setLastStatusChange(Instant.now());
        spotRepo.save(locked);
    }

    @Transactional
    public void setMaintenance(Long spotId, boolean maintenance) {
        ParkingSpot locked = spotRepo.lockById(spotId).orElseThrow();
        locked.setStatus(maintenance ? SpotStatus.MAINTENANCE : SpotStatus.AVAILABLE);
        locked.setLastStatusChange(Instant.now());
        spotRepo.save(locked);
    }
}
