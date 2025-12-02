package com.example.EventBookingApi.DTO;

import lombok.Data;

@Data
public class EventRequest {
    private String name;
    private String location;
    private String description;
    private String date;
    private int availableSeats;
}
