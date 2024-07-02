package org.example.metabox.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review r where r.movie.id =:movieId order by r.createdAt desc")
    List<Review> findByMovieId(@Param("movieId") int movieId);

    @Query("""
            select r
            from Review r
            join fetch r.movie m
            join fetch r.user u
            where u.id =:userId
            """)
    List<ReviewResponse.MyReviewDTO.ReviewListDTO> findByUserIdAndMovie(@Param("userId") int userId);
}
