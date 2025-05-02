package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import com.example.demo.util.GeoUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }


    
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        if (event.getUserLatitude() != null && event.getUserLongitude() != null &&
            event.getLatitude() != null && event.getLongitude() != null) {
            
            double distance = GeoUtils.calculateDistance(
                event.getUserLatitude(), event.getUserLongitude(),
                event.getLatitude(), event.getLongitude()
            );
            event.setDistanceFromUser(distance);
        }

        return eventService.saveEvent(event);
    }


    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }

    // ✅ New endpoint for search/filter/pagination/sorting

    @GetMapping("/search")
    public Page<Event> searchEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) Double userLatitude,
            @RequestParam(required = false) Double userLongitude
    ) {
        Page<Event> eventsPage = eventService.searchEvents(name, location, page, size, sortBy, direction);

        if (userLatitude != null && userLongitude != null) {
            eventsPage.forEach(event -> {
                if (event.getLatitude() != null && event.getLongitude() != null) {
                    event.setUserLatitude(userLatitude);
                    event.setUserLongitude(userLongitude);
                    event.setDistanceFromUser(
                        GeoUtils.calculateDistance(userLatitude, userLongitude, event.getLatitude(), event.getLongitude())
                    );
                }
            });
        }

        return eventsPage;
    }

    
    
    // ✅ New: Get Nearby Events
    @GetMapping("/nearby")
    public List<Event> getNearbyEvents(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "10") double radiusKm
    ) {
        return eventService.findNearbyEvents(latitude, longitude, radiusKm);
    }
    
    // ✅ Get Upcoming Events (e.g., future events only)
    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    // ✅ Notify Users When Seats Become Available (trigger endpoint manually or internally after booking/cancel)
    @PostMapping("/{eventId}/check-seats")
    public String notifyIfSeatsAvailable(@PathVariable Long eventId) {
        boolean notified = eventService.checkAndNotifyIfSeatsAvailable(eventId);
        return notified ? "Users notified for available seats." : "No notification sent (still full or no interested users).";
    }
}
