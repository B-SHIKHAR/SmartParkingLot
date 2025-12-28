package main.java.com.example.parking.service;

import main.java.com.example.parking.domain.enums.SpotStatus;
import main.java.com.example.parking.domain.enums.SpotType;
import main.java.com.example.parking.repo.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class AvailabilityService {
    private final ParkingSpotRepository spotRepo;

    public AvailabilityService(ParkingSpotRepository spotRepo) {
        this.spotRepo = spotRepo;
    }

    // Simple aggregated counts per spot type
    public Map<SpotType, Integer> getAvailableCounts() {
        Map<SpotType, Integer> map = new EnumMap<>(SpotType.class);
        for (SpotType type : SpotType.values()) {
            int count = spotRepo.findAvailableByTypeOrdered(SpotStatus.AVAILABLE, type).size();
            map.put(type, count);
        }
        return map;
    }
}
