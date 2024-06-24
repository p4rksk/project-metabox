package org.example.metabox.movie_pic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoviePicRepository extends JpaRepository<MoviePic, Integer> {

    @Query("select mp from MoviePic mp where mp.movie.id =:movieId")
    List<MoviePic> findStillsByMovieId(@Param("movieId") int movieId);
}
