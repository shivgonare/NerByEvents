package com.example.demo.controller;

import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired private MovieService movieService;

    @GetMapping
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {
        return movieService.deleteMovie(id);
    }
    
    
    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam(required = false) String title,
                                    @RequestParam(required = false) String genre) {
        return movieService.searchMovies(title, genre);
    }
    
    @GetMapping("/nearby")
    public List<Movie> getNearbyMovies(@RequestParam double latitude,
                                       @RequestParam double longitude,
                                       @RequestParam(defaultValue = "10") double radiusKm) {
        return movieService.findNearbyMovies(latitude, longitude, radiusKm);
    }


}
