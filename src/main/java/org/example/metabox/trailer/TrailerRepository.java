package org.example.metabox.trailer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrailerRepository extends JpaRepository<Trailer, Integer> {

    @Query("select t from Trailer t where t.movie.id =:movieId")
    List<Trailer> findTrailersByMovieId(@Param("movieId") int movieId);


    //예매율 1위 영화 구하는 쿼리
    @Query(value = """
        SELECT m.id
        FROM movie_tb m
        LEFT JOIN screening_info_tb si ON m.id = si.movie_id
        LEFT JOIN seat_book_tb sb ON si.id = sb.screening_info_id
        WHERE si.date >= '2024-06-21'
        GROUP BY m.id
        ORDER BY (COUNT(sb.book_id) * 1.0 / (
            SELECT COUNT(sb2.book_id)
            FROM seat_book_tb sb2
            JOIN screening_info_tb si2 ON sb2.screening_info_id = si2.id
            WHERE si2.date >= '2024-06-21'
        )) DESC
        LIMIT 1;
    """, nativeQuery = true)
    Integer findTopBookingRateMovieId();

}
