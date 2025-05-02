package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> 
{

	List<Movie> findByTitleContainingIgnoreCase(String title);
	List<Movie> findByGenreContainingIgnoreCase(String genre);
	List<Movie> findByTitleContainingIgnoreCaseAndGenreContainingIgnoreCase(String title, String genre);

}
