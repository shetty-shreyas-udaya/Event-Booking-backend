package com.example.EventBookingApi.Controller;

import com.example.EventBookingApi.DTO.EventRequest;
import com.example.EventBookingApi.Model.Event;
import com.example.EventBookingApi.Service.DataStore;
import com.example.EventBookingApi.Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DataStore dataStore;

    @GetMapping
    public List<Event> getAll()
    {
        return dataStore.getEventData();
    }

    @PostMapping
    public ResponseEntity<?> createEvents(@RequestBody EventRequest eventRequest, HttpServletRequest req)
    {
        if(!isAdmin(req))
        {
            return ResponseEntity.status(403).body("Forbidden:Admin only");
        }

        Event event = Event.builder()
                .id(dataStore.getNextEventId())
                .name(eventRequest.getName())
                .description(eventRequest.getDescription())
                .date(eventRequest.getDate())
                .location(eventRequest.getLocation())
                .availableSeats(eventRequest.getAvailableSeats())
                .build();
        dataStore.getEventData().add(event);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable long id, HttpServletRequest req){
        if(!isAdmin(req))
        {
            return ResponseEntity.status(403).body("Forbidden: Admin only");
        }
        List<Event> events = dataStore.getEventData();
        Optional<Event> found = events.stream().filter(ev -> ev.getId() == id).findFirst();

        if(found.isEmpty())
        {
            return ResponseEntity.status(404).body("Event not found");
        }
        events.remove(found.get());
        return ResponseEntity.ok("Event deleted");
    }

    private boolean isAdmin(HttpServletRequest req)
    {
        String header = req.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
        {
            String token = header.substring(7);
            if(jwtService.validateToken(token))
            {
                return "ADMIN".equalsIgnoreCase(jwtService.extractRole(token));
            }
        }
        return false;
    }

}
