package main.java.com.example.parking.web;

import main.java.com.example.parking.service.AvailabilityService;
import main.java.com.example.parking.web.dto.AvailabilityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public ResponseEntity<AvailabilityResponse> get() {
        return ResponseEntity.ok(new AvailabilityResponse(availabilityService.getAvailableCounts()));
    }
}
