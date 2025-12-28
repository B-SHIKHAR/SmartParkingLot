package main.java.com.example.parking.web;

import main.java.com.example.parking.domain.ParkingSession;
import main.java.com.example.parking.service.EntryService;
import main.java.com.example.parking.web.dto.CheckInRequest;
import main.java.com.example.parking.web.dto.CheckInResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entry")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping
    public ResponseEntity<CheckInResponse> checkIn(@Valid @RequestBody CheckInRequest req) {
        ParkingSession session = entryService.checkIn(req.getPlate(), req.getVehicleType(), req.getPolicy());
        var spot = session.getSpot();
        return ResponseEntity.ok(new CheckInResponse(
                session.getId(), spot.getSpotCode(), spot.getId(),
                spot.getFloor().getName()
        ));
    }

    @PostMapping("/confirm/{spotId}")
    public ResponseEntity<Void> confirm(@PathVariable Long spotId) {
        entryService.confirmOccupancy(spotId);
        return ResponseEntity.ok().build();
    }
}
