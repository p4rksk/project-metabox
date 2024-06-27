package org.example.metabox.movie;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void findCurrentAndUpcomingMovies_Test(){
        // given

        // when
        List<Movie> movies = movieRepository.findCurrentAndUpcomingMovies();

        // then
        movies.forEach(movie -> {
            System.out.println("##########    Title: " + movie.getTitle());
            System.out.println("########## End Date: " + movie.getEndDate());
        });
    }
}
