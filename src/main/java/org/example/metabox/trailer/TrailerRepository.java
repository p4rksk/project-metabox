package org.example.metabox.trailer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrailerRepository extends JpaRepository<Trailer, Integer> {

    @Query("select t from Trailer t where t.movie.id =:movieId")
    List<Trailer> findTrailersByMovieId(@Param("movieId") int movieId);
}
