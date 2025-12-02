package com.example.EventBookingApi.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {
    private long id;
    private String name;
    private String date;
    private String description;
    private String location;
    private int availableSeats;
}
