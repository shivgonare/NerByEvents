package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Restarunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String cuisine;
    private Double latitude;
    private Double longitude;

    @Transient
    private Double distanceFromUser;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getDistanceFromUser() { return distanceFromUser; }
    public void setDistanceFromUser(Double distanceFromUser) { this.distanceFromUser = distanceFromUser; }
}
