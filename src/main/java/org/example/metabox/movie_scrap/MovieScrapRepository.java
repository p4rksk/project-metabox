package org.example.metabox.movie_scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieScrapRepository extends JpaRepository<MovieScrap, Integer> {

    @Query("""
            select ms
            from MovieScrap ms
            join fetch ms.movie m
            join fetch ms.user u
            where u.id = :user_id
            """)
    List<MovieScrapResponse.MovieScrapDTO.ScrapMovieListDTO> findByUserAndMovie(@Param("user_id") Integer userId);

    @Query("""
            select ms
            from MovieScrap ms
            join fetch ms.movie m
            join fetch ms.user u
            where u.id = :user_id and m.id = :movie_id
            """)
    Optional<MovieScrap> findByScrapAndUser(@Param("user_id") Integer userId, @Param("movie_id") Integer movieId);
}
