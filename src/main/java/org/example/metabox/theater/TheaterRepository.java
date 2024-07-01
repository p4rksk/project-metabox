package org.example.metabox.theater;

import org.example.metabox.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {

    @Query("select t from Theater t where t.areaCode = :areaCode")
    List<Theater> findByArea(String areaCode);

    @Query("SELECT distinct(t.areaName, t.areaCode) FROM Theater t ORDER BY t.id ASC")
    List<String[]> findAllDistinctArea();

    @Query("select t from Theater t where t.loginId = :loginId and t.password = :password")
    Optional<Theater> findByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);

    // 특점 지점 매출 조회 (DTO로 리턴하면 터짐 이유는 모름)
    @Query("""
                SELECT
                    t.areaName, 
                    t.name, 
                    t.address, 
                    t.number, 
                    SUM(CASE 
                        WHEN se.type = 'HANDICAPPED' THEN sc.seatPrice - 5000 
                        WHEN se.type = 'LIGHT' THEN sc.seatPrice - 1000 
                        ELSE sc.seatPrice 
                    END) AS totalSales
                FROM Theater t 
                JOIN t.screeningList sc 
                JOIN sc.screeningInfoList si 
                JOIN si.seatBookList sb 
                JOIN sb.seat se 
                WHERE t.id = :theaterId 
                GROUP BY t.id, t.areaName, t.name, t.address, t.number
                ORDER BY totalSales DESC
            """)
    List<Object[]> getTheaterSales(@Param("theaterId") int theaterId);

    // 지점별 매출 조회
    @Query("""
                SELECT t.id, t.name AS theaterName, 
                       SUM(CASE 
                           WHEN se.type = 'HANDICAPPED' THEN sc.seatPrice - 5000 
                           WHEN se.type = 'LIGHT' THEN sc.seatPrice - 1000 
                           ELSE sc.seatPrice 
                       END) AS theaterSales 
                FROM Theater t 
                JOIN t.screeningList sc 
                JOIN sc.screeningInfoList si 
                JOIN si.seatBookList sb 
                JOIN sb.seat se 
                GROUP BY t.id, t.name 
                ORDER BY theaterSales DESC
            """)
    List<Object[]> findSalesByTheater();

    // 지점의 영화별 매출
    @Query("""
            SELECT m.id, m.title AS movieTitle, m.startDate, m.endDate, 
                   SUM(CASE 
                       WHEN se.type = 'HANDICAPPED' THEN sc.seatPrice - 5000 
                       WHEN se.type = 'LIGHT' THEN sc.seatPrice - 1000 
                       ELSE sc.seatPrice 
                   END) AS movieSales, 
                   COUNT(sb.id) AS viewerCount
            FROM Theater t 
            JOIN t.screeningList sc 
            JOIN sc.screeningInfoList si 
            JOIN si.seatBookList sb 
            JOIN sb.seat se 
            JOIN si.movie m 
            WHERE t.id = :theaterId 
            GROUP BY m.id, m.title, m.startDate, m.endDate
            ORDER BY movieSales DESC
            """)
    List<Object[]> findTheaterSalesByMovie(@Param("theaterId") int theaterId);

    // 전체 매출 조회
    @Query("""
           SELECT SUM(CASE 
                          WHEN se.type = 'HANDICAPPED' THEN sc.seatPrice - 5000 
                          WHEN se.type = 'LIGHT' THEN sc.seatPrice - 1000 
                          ELSE sc.seatPrice 
                      END) AS totalSales 
           FROM Theater t 
           JOIN t.screeningList sc 
           JOIN sc.screeningInfoList si 
           JOIN si.seatBookList sb 
           JOIN sb.seat se
           """)
    Long findTotalSales();

    // 전 지점의 영화별 매출 및 관객수 조회
    @Query("""
           SELECT m.id, m.title AS movieTitle, m.startDate, m.endDate, 
                  SUM(CASE 
                      WHEN se.type = 'HANDICAPPED' THEN sc.seatPrice - 5000 
                      WHEN se.type = 'LIGHT' THEN sc.seatPrice - 1000 
                      ELSE sc.seatPrice 
                  END) AS movieSales,
                  COUNT(sb.id) AS viewerCount
           FROM Theater t 
           JOIN t.screeningList sc 
           JOIN sc.screeningInfoList si 
           JOIN si.seatBookList sb 
           JOIN sb.seat se 
           JOIN si.movie m 
           GROUP BY m.id, m.title, m.startDate, m.endDate
           ORDER BY movieSales DESC
           """)
    List<Object[]> findAllTheaterSalesByMovie();

    @Query("select s from Screening s where s.theater.id = :theaterId")
    List<Screening> findScreeningBytheaterId(int theaterId);
}

