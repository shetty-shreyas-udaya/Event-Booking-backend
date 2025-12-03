package com.example.EventBookingApi.Service;

import com.example.EventBookingApi.Model.Booking;
import com.example.EventBookingApi.Model.Event;
import com.example.EventBookingApi.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DataStore {

    private final List<Event> events = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicLong nextEventId = new AtomicLong(1);
    private final AtomicLong nextBookingId = new AtomicLong(1);

    public DataStore()
    {
        users.add(User.builder()
                .userName("Admin")
                .role("ADMIN")
                .password("admin1")
                .build());
        users.add(User.builder()
                .userName("user1")
                .role("USER")
                .password("user1")
                .build());
        users.add(User.builder()
                .userName("user2")
                .role("USER")
                .password("user2")
                .build());

        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Spring Music Festival")
                .date("2024-07-15")
                .location("Central Park")
                .description("An outdoor celebration of music and culture.")
                .availableSeats(100)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Tech Conference X")
                .date("2024-08-21")
                .location("Metro Convention Center")
                .description("Innovative tech workshops and keynote talks.")
                .availableSeats(250)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Stand-Up Comedy Night")
                .date("2024-09-10")
                .location("City Theater")
                .description("A hilarious night with top comedians.")
                .availableSeats(60)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Art & Food Expo")
                .date("2024-10-05")
                .location("Downtown Exhibition Hall")
                .description("Explore local art while sampling gourmet foods.")
                .availableSeats(200)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Startup Pitch Day")
                .date("2024-10-28")
                .location("Innovation Labs")
                .description("Watch startups pitch to investors for big prizes.")
                .availableSeats(150)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Annual Charity Marathon")
                .date("2024-11-12")
                .location("Riverside Park")
                .description("Run for a cause and support local charities.")
                .availableSeats(300)
                .build());

    }

    public List<User> getUserData()
    {
        return Collections.unmodifiableList(users);
    }

    public List<Event> getEventData()
    {
        return events;
    }

    public List<Booking> getBookingsData()
    {
        return bookings;
    }

    public long getNextEventId()
    {
        return nextEventId.getAndIncrement();
    }

    public long getNextBookingId()
    {
        return nextBookingId.getAndIncrement();
    }

    public void resetEvents() {
        events.clear();
        nextEventId.set(1);

        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Spring Music Festival")
                .date("2024-07-15")
                .location("Central Park")
                .description("An outdoor celebration of music and culture.")
                .availableSeats(100)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Tech Conference X")
                .date("2024-08-21")
                .location("Metro Convention Center")
                .description("Innovative tech workshops and keynote talks.")
                .availableSeats(250)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Stand-Up Comedy Night")
                .date("2024-09-10")
                .location("City Theater")
                .description("A hilarious night with top comedians.")
                .availableSeats(60)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Art & Food Expo")
                .date("2024-10-05")
                .location("Downtown Exhibition Hall")
                .description("Explore local art while sampling gourmet foods.")
                .availableSeats(200)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Startup Pitch Day")
                .date("2024-10-28")
                .location("Innovation Labs")
                .description("Watch startups pitch to investors for big prizes.")
                .availableSeats(150)
                .build());
        events.add(Event.builder()
                .id(nextEventId.getAndIncrement())
                .name("Annual Charity Marathon")
                .date("2024-11-12")
                .location("Riverside Park")
                .description("Run for a cause and support local charities.")
                .availableSeats(300)
                .build());
    }
}
