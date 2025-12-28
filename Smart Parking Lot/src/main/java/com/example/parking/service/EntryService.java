package main.java.com.example.parking.service;

import main.java.com.example.parking.domain.ParkingSession;
import main.java.com.example.parking.domain.ParkingSpot;
import main.java.com.example.parking.domain.Vehicle;
import main.java.com.example.parking.domain.enums.VehicleType;
import main.java.com.example.parking.repo.ParkingSessionRepository;
import main.java.com.example.parking.repo.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class EntryService {

    private final VehicleRepository vehicleRepo;
    private final ParkingSessionRepository sessionRepo;
    private final AllocationService allocationService;

    public EntryService(VehicleRepository vehicleRepo,
                        ParkingSessionRepository sessionRepo,
                        AllocationService allocationService) {
        this.vehicleRepo = vehicleRepo;
        this.sessionRepo = sessionRepo;
        this.allocationService = allocationService;
    }

    @Transactional
    public ParkingSession checkIn(String plate, VehicleType type, String policy) {
        Vehicle vehicle = vehicleRepo.findByPlate(plate).orElseGet(() -> {
            Vehicle v = new Vehicle();
            v.setPlate(plate);
            v.setType(type);
            return vehicleRepo.save(v);
        });

        sessionRepo.findActiveByVehicle(vehicle).ifPresent(s -> {
            throw new IllegalStateException("Active session already exists for vehicle");
        });

        ParkingSpot spot = allocationService.allocate(type, policy);

        ParkingSession session = new ParkingSession();
        session.setVehicle(vehicle);
        session.setSpot(spot);
        session.setEntryTime(Instant.now());
        session.setStatus("ACTIVE");
        session.setAllocationPolicy(policy);
        return sessionRepo.save(session);
    }

    @Transactional
    public void confirmOccupancy(Long spotId) {
        allocationService.finalizeOccupancy(spotId);
    }
}
