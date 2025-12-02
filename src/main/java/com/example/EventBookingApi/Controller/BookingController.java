package com.example.EventBookingApi.Controller;

import com.example.EventBookingApi.DTO.BookingRequest;
import com.example.EventBookingApi.Model.Booking;
import com.example.EventBookingApi.Service.DataStore;
import com.example.EventBookingApi.Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DataStore dataStore;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest, HttpServletRequest req)
    {
        String userName = extractUserName(req);
        if(userName == null)
        {
            return ResponseEntity.status(401).body("Username not found");
        }

        boolean alreadyBooked = dataStore.getBookingsData().stream()
                .anyMatch(b-> b.getUserName().equals(userName) && b.getEventId() == bookingRequest.getEventId());

        if(alreadyBooked)
        {
            return ResponseEntity.status(400).body("Already booked");
        }

        var eventOpt = dataStore.getEventData().stream()
                .filter(ev -> ev.getId() == bookingRequest.getEventId())
                .findFirst();

        if(eventOpt.isEmpty())
        {
            return ResponseEntity.status(404).body("Event Not found");
        }

        var event = eventOpt.get();

        if(event.getAvailableSeats() < 1)
        {
            return ResponseEntity.status(400).body("Seats not available");
        }

        event.setAvailableSeats(event.getAvailableSeats() -1);

        Booking booking = Booking.builder()
                .id(dataStore.getNextBookingId())
                .userName(userName)
                .eventId(event.getId())
                .build();
        dataStore.getBookingsData().add(booking);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<?> getBooking(HttpServletRequest req)
    {
        String userName = extractUserName(req);
        if(userName == null)
        {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<Booking> bookingList = dataStore.getBookingsData().stream()
                .filter(b -> b.getUserName().equals(userName))
                .toList();


        return ResponseEntity.ok(bookingList);
    }

    private String extractUserName(HttpServletRequest req)
    {
        String header = req.getHeader("Authorization");
        if(header !=null && header.startsWith("Bearer"))
        {
            String token = header.substring(7);
            if(jwtService.validateToken(token))
            {
                return jwtService.extractUserName(token);
            }
        }

        return null;
    }
}
