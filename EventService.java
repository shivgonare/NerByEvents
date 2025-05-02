package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.WishlistRepository;
import com.example.demo.specification.EventSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import com.example.demo.entity.Wishlist;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
  
	  @Autowired
	    private EventRepository eventRepository;

	    @Autowired
	    private WishlistRepository wishlistRepository;

	    @Autowired
	    private NotificationService notificationService;
	    
    // ✅ Existing methods
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public String deleteEvent(Long id) {
        eventRepository.deleteById(id);
        return "Event deleted successfully.";
    }

    // ✅ New method for Search + Filter + Pagination + Sorting
    public Page<Event> searchEvents(String name, String location, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Event> spec = Specification
                .where(EventSpecification.hasName(name))
                .and(EventSpecification.hasLocation(location));

        return eventRepository.findAll(spec, pageable);
    }
    
    public List<Event> findNearbyEvents(double userLat, double userLng, double radiusKm) {
        List<Event> allEvents = eventRepository.findAll();
        return allEvents.stream()
                .filter(e -> {
                    if (e.getLatitude() == null || e.getLongitude() == null) return false;
                    double distance = haversine(userLat, userLng, e.getLatitude(), e.getLongitude());
                    return distance <= radiusKm;
                })
                .sorted((e1, e2) -> {
                    double d1 = haversine(userLat, userLng, e1.getLatitude(), e1.getLongitude());
                    double d2 = haversine(userLat, userLng, e2.getLatitude(), e2.getLongitude());
                    return Double.compare(d1, d2);
                })
                .collect(Collectors.toList());
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByDateAfter(LocalDateTime.now());
    }

    
    public boolean checkAndNotifyIfSeatsAvailable(Long eventId) {
        Event event = getEventById(eventId);
        if (event == null) return false;

        int bookedSeats = event.getTickets().size();
        int totalSeats = event.getTotalSeats(); // Make sure Event.java has getTotalSeats()

        if (bookedSeats < totalSeats) {
            List<Wishlist> wishlisted = wishlistRepository.findByEventAndNotifiedFalse(event);
            for (Wishlist entry : wishlisted) {
                notificationService.sendNotification(entry.getUser(), event);
                entry.setNotified(true);
            }
            wishlistRepository.saveAll(wishlisted);
            return true;
        }
        return false;
    }


}
