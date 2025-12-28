package main.java.com.example.parking.web.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor
public class CheckOutQuoteResponse {
    private int feeCents;
    private String currency;
}
