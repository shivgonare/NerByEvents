package com.example.demo.service;

import com.example.demo.entity.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public String deleteMovie(Long id) {
        movieRepository.deleteById(id);
        return "Movie deleted successfully.";
    }
    
    public List<Movie> searchMovies(String title, String genre) {
        if (title != null && genre != null) {
            return movieRepository.findByTitleContainingIgnoreCaseAndGenreContainingIgnoreCase(title, genre);
        } else if (title != null) {
            return movieRepository.findByTitleContainingIgnoreCase(title);
        } else if (genre != null) {
            return movieRepository.findByGenreContainingIgnoreCase(genre);
        } else {
            return movieRepository.findAll();
        }
    }

    public List<Movie> findNearbyMovies(double userLat, double userLng, double radiusKm) {
        List<Movie> allMovies = movieRepository.findAll();
        return allMovies.stream()
                .filter(m -> m.getLatitude() != null && m.getLongitude() != null)
                .peek(m -> {
                    double dist = haversine(userLat, userLng, m.getLatitude(), m.getLongitude());
                    m.setDistanceFromUser(dist);
                })
                .filter(m -> m.getDistanceFromUser() <= radiusKm)
                .sorted((m1, m2) -> Double.compare(m1.getDistanceFromUser(), m2.getDistanceFromUser()))
                .collect(Collectors.toList());
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
