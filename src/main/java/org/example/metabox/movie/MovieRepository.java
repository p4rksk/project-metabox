package org.example.metabox.movie;

import org.example.metabox.trailer.Trailer;
import org.example.metabox.user.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("select m from Movie m where m.endDate >= current date")
    List<Movie> findCurrentAndUpcomingMovies();

    @Query("select count(*) from Movie m where m.endDate >= current date ")
    long countAllMovies();

    @Query("select count(*) from Movie m where m.endDate >= current date and m.startDate >= current date ")
    long countUpcomingMovies();
}
