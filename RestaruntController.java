package com.example.demo.controller;

import com.example.demo.entity.Restarunt;
import com.example.demo.service.RestaruntService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaruntController {

    private final RestaruntService service;

    public RestaruntController(RestaruntService service) {
        this.service = service;
    }

    @GetMapping
    public List<Restarunt> getAll() {
        return service.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restarunt> getById(@PathVariable Long id) {
        return service.getRestaurantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Restarunt create(@RequestBody Restarunt restarunt) {
        return service.saveRestaurant(restarunt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/nearby")
    public List<Restarunt> getNearbyRestaurants(@RequestParam double latitude,
                                                @RequestParam double longitude,
                                                @RequestParam(defaultValue = "10") double radiusKm) {
        return service.findNearbyRestaurants(latitude, longitude, radiusKm);
    }

}
