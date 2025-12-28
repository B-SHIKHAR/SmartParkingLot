package main.java.com.example.parking.web.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor
public class CheckInResponse {
    private Long sessionId;
    private String spotCode;
    private Long spotId;
    private String floorName;
}
