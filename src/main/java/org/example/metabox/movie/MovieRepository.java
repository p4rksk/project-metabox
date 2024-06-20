package org.example.metabox.movie;

import org.example.metabox.user.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
// 전체 예매율
//    select count(id) from seat_book_tb;

    // 특정 영화만 받는 예매율
//    select count(*)
//    from seat_book_tb sb
//    inner join screening_info_tb si on sb.screening_info_id = si.id
//    inner join movie_tb m on si.movie_id = m.id where m.id = 1;

    // JPA는 서브쿼리 안에 join fetch 사용 못한다고 함
//    @Query("select new org.example.metabox.user.UserResponse$DetailBookDTO$MovieChartDTO(m.id, m.imgFilename, m.title, m.startDate, " +
//            "(select count(sb) from SeatBook sb join sb.screeningInfo si on sb.screeningInfo.id = si.id " +
//            "where si.movie.id = 1 / select count(sb) from SeatBook sb) * 100 " +
//            "from Movie m")
//    List<UserResponse.DetailBookDTO.MovieChartDTO> findMovieChart();


    @Query("select new org.example.metabox.user.UserResponse$DetailBookDTO$MovieChartDTO(m.id, m.imgFilename, m.title, m.startDate) from Movie m")
    List<UserResponse.DetailBookDTO.MovieChartDTO> findMovieChart();

    @Query("select count(sb.id) from SeatBook sb")
    Integer findTotalSeatBookCount();

    // count 쿼리에 join fetch 쓰니까 자꾸 터져요
    @Query("select count(sb.id) from SeatBook sb join sb.screeningInfo si join si.movie m where m.id = :movieId")
    Integer findSeatBookCountByMovieId(@Param("movieId") Integer movieId);

}
