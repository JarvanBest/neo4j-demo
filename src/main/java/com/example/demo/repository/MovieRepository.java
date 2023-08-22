package com.example.demo.repository;

import com.example.demo.domain.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends Neo4jRepository<Movie, Long> {
    //@Query("MATCH (n:Movie) WHERE id = $0 RETURN n")
    Movie findMovieById(Long id);
    Movie findMovieByTitle(String title);
}