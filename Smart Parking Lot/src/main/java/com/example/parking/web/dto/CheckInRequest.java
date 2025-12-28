package main.java.com.example.parking.web.dto;

import main.java.com.example.parking.domain.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
public class CheckInRequest {
    @NotBlank
    private String plate;
    @NotNull
    private VehicleType vehicleType;
    private String policy = "NEAREST_AVAILABLE";
}
