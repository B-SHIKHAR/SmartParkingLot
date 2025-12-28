package main.java.com.example.parking.service;

import main.java.com.example.parking.domain.ParkingSession;
import main.java.com.example.parking.domain.RatePlan;
import main.java.com.example.parking.domain.Vehicle;
import main.java.com.example.parking.repo.ParkingSessionRepository;
import main.java.com.example.parking.repo.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class ExitService {

    private final VehicleRepository vehicleRepo;
    private final ParkingSessionRepository sessionRepo;
    private final RateService rateService;
    private final FeeCalculator feeCalculator;
    private final AllocationService allocationService;

    public ExitService(VehicleRepository vehicleRepo,
                       ParkingSessionRepository sessionRepo,
                       RateService rateService,
                       FeeCalculator feeCalculator,
                       AllocationService allocationService) {
        this.vehicleRepo = vehicleRepo;
        this.sessionRepo = sessionRepo;
        this.rateService = rateService;
        this.feeCalculator = feeCalculator;
        this.allocationService = allocationService;
    }

    @Transactional(readOnly = true)
    public int quote(String plate, ZoneId zoneId) {
        Vehicle vehicle = vehicleRepo.findByPlate(plate)
                .orElseThrow(() -> new IllegalArgumentException("Unknown vehicle"));
        ParkingSession session = sessionRepo.findActiveByVehicle(vehicle)
                .orElseThrow(() -> new IllegalStateException("No active session"));
        RatePlan plan = rateService.getActivePlanOrThrow();
        var rules = rateService.getRules(plan, vehicle.getType());
        Instant now = Instant.now();
        return feeCalculator.computeFeeCents(session.getEntryTime(), now, vehicle.getType(), rules, zoneId);
    }

    @Transactional
    public ParkingSession settle(String plate, ZoneId zoneId) {
        Vehicle vehicle = vehicleRepo.findByPlate(plate)
                .orElseThrow(() -> new IllegalArgumentException("Unknown vehicle"));
        ParkingSession session = sessionRepo.findActiveByVehicle(vehicle)
                .orElseThrow(() -> new IllegalStateException("No active session"));
        session = sessionRepo.lockById(session.getId()).orElseThrow();

        RatePlan plan = rateService.getActivePlanOrThrow();
        var rules = rateService.getRules(plan, vehicle.getType());
        Instant exit = Instant.now();
        int fee = feeCalculator.computeFeeCents(session.getEntryTime(), exit, vehicle.getType(), rules, zoneId);

        session.setExitTime(exit);
        session.setFeeCents(fee);
        session.setStatus("CLOSED");
        ParkingSession saved = sessionRepo.save(session);

        allocationService.release(session.getSpot().getId());
        return saved;
    }
}
