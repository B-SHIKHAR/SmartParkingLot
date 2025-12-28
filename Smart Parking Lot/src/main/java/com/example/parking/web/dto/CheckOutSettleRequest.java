package main.java.com.example.parking.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
public class CheckOutSettleRequest {
    @NotBlank
    private String plate;
}
