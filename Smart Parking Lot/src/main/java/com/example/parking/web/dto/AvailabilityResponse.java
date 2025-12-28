package main.java.com.example.parking.web.dto;

import main.java.com.example.parking.domain.enums.SpotType;
import lombok.*;

import java.util.Map;

@Getter @Setter @AllArgsConstructor
public class AvailabilityResponse {
    private Map<SpotType, Integer> counts;
}
