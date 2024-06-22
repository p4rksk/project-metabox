package org.example.metabox.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("select m from ScreeningInfo m where m.screening.theater.id = :theaterId")
    List<Movie> findByTheaterId(@Param("theaterId") int theaterId);
}
