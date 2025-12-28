package main.java.com.example.parking.web;

import main.java.com.example.parking.service.AllocationService;
import main.java.com.example.parking.web.dto.AdminSpotStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/spots")
public class AdminController {

    private final AllocationService allocationService;

    public AdminController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<Void> setStatus(@PathVariable Long id, @RequestBody AdminSpotStatusRequest req) {
        allocationService.setMaintenance(id, req.isMaintenance());
        return ResponseEntity.ok().build();
    }
}
