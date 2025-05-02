package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private Double latitude;
    private Double longitude;

    @Transient
    private Double distanceFromUser;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getDistanceFromUser() { return distanceFromUser; }
    public void setDistanceFromUser(Double distanceFromUser) { this.distanceFromUser = distanceFromUser; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}
