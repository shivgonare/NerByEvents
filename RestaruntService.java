package com.example.demo.service;

import com.example.demo.entity.Restarunt;
import com.example.demo.repository.RestaruntRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaruntService {

    private final RestaruntRepository repository;

    public RestaruntService(RestaruntRepository repository) {
        this.repository = repository;
    }

    public List<Restarunt> getAllRestaurants() {
        return repository.findAll();
    }

    public Optional<Restarunt> getRestaurantById(Long id) {
        return repository.findById(id);
    }

    public Restarunt saveRestaurant(Restarunt restarunt) {
        return repository.save(restarunt);
    }

    public void deleteRestaurant(Long id) {
        repository.deleteById(id);
    }
    
    public List<Restarunt> findNearbyRestaurants(double userLat, double userLng, double radiusKm) {
        List<Restarunt> all = repository.findAll();
        return all.stream()
                .filter(r -> r.getLatitude() != null && r.getLongitude() != null)
                .peek(r -> r.setDistanceFromUser(haversine(userLat, userLng, r.getLatitude(), r.getLongitude())))
                .filter(r -> r.getDistanceFromUser() <= radiusKm)
                .sorted(Comparator.comparingDouble(Restarunt::getDistanceFromUser))
                .collect(Collectors.toList());
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in KM
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
