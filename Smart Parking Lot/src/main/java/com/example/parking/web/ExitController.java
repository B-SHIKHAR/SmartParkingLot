package main.java.com.example.parking.web;

import main.java.com.example.parking.domain.ParkingSession;
import main.java.com.example.parking.service.ExitService;
import main.java.com.example.parking.web.dto.CheckOutQuoteResponse;
import main.java.com.example.parking.web.dto.CheckOutSettleRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;

@RestController
@RequestMapping("/exit")
public class ExitController {

    private final ExitService exitService;

    public ExitController(ExitService exitService) {
        this.exitService = exitService;
    }

    @GetMapping("/quote/{plate}")
    public ResponseEntity<CheckOutQuoteResponse> quote(@PathVariable String plate) {
        int fee = exitService.quote(plate, ZoneId.of("Asia/Kolkata"));
        return ResponseEntity.ok(new CheckOutQuoteResponse(fee, "INR"));
    }

    @PostMapping("/settle")
    public ResponseEntity<ParkingSession> settle(@Valid @RequestBody CheckOutSettleRequest req) {
        ParkingSession closed = exitService.settle(req.getPlate(), ZoneId.of("Asia/Kolkata"));
        return ResponseEntity.ok(closed);
    }
}
