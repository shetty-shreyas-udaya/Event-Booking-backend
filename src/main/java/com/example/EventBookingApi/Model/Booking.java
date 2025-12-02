package com.example.EventBookingApi.Model;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Booking {
    private String userName;
    private long eventId;
    private long id;
}
