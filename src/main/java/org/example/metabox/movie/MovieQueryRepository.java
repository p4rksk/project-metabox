package org.example.metabox.movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.example.metabox.trailer.Trailer;
import org.example.metabox.user.UserResponse;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MovieQueryRepository {
    private final EntityManager em;

    // 마이페이지의 내 예매내역
    public List<UserResponse.MyPageHomeDTO.TicketingDTO> findMyTicketing(Integer sessionUserId) {
        String q = """
                SELECT m.title, m.img_filename, si.date as "관람일시", si.start_time as "시작시간", 
                si.end_time as "종료시간", b.id, s.name, t.name, b.user_id as "유저"
                FROM book_tb b
                INNER JOIN seat_book_tb sb ON sb.book_id = b.id
                INNER JOIN screening_info_tb si ON sb.screening_info_id = si.id
                INNER JOIN screening_tb s ON s.id = si.screening_id
                INNER JOIN theater_tb t ON s.theater_id = t.id
                INNER JOIN movie_tb m ON si.movie_id = m.id
                WHERE user_id = ?
                AND b.created_at >= CURRENT_DATE - INTERVAL '1' MONTH
                GROUP BY si.id          
                """;

        Query query = em.createNativeQuery(q);
        query.setParameter(1, sessionUserId);

        List<Object[]> rows = query.getResultList();
        List<UserResponse.MyPageHomeDTO.TicketingDTO> ticketingDTOList = new ArrayList<>();

        for (Object[] row : rows) {
            String title = (String) row[0];
            String imgFilename = (String) row[1];
            Date date = (Date) row[2];
            String startTime = (String) row[3];
            String endTime = (String) row[4];
            Integer bookId = (Integer) row[5];
            String name = (String) row[6];  // 몇관인지
            String theaterName = (String) row[7];  // 몇관인지
            Integer userId = ((Number) row[8]).intValue();

            UserResponse.MyPageHomeDTO.TicketingDTO ticketingDTO = UserResponse.MyPageHomeDTO.TicketingDTO.builder()
                    .title(title)
                    .imgFilename(imgFilename)
                    .date(date)
                    .startTime(startTime)
                    .endTime(endTime)
                    .id(bookId)
                    .name(name)
                    .theaterName(theaterName)
                    .userId(userId)
                    .build();

            ticketingDTOList.add(ticketingDTO);

        }

        return ticketingDTOList;


    }



    // 상영예정 영화 목록
    public List<UserResponse.MainChartDTO.ToBeChartDTO> getToBeChart() {

        String q = """
                select id, img_filename, title, start_date, DATEDIFF('DAY', CURRENT_DATE(), start_date) as d_day
                from movie_tb where start_date > CURRENT_DATE()
                """;

        Query query = em.createNativeQuery(q);
//        query.setParameter(1, 2);

        List<Object[]> rows = query.getResultList();
        List<UserResponse.MainChartDTO.ToBeChartDTO> movieChartDTOS = new ArrayList<>();

        for (Object[] row : rows) {
            Integer id = ((Number) row[0]).intValue();
            String imgFilename = (String) row[1];
            String title = (String) row[2];
            Date startDate = (Date) row[3];
            Integer dDay = ((Number) row[4]).intValue();    // integer로 변환시켜서 가져오기

            UserResponse.MainChartDTO.ToBeChartDTO toBeChartDTO = UserResponse.MainChartDTO.ToBeChartDTO.builder()
                    .id(id)
                    .imgFilename(imgFilename)
                    .title(title)
                    .startDate(startDate)
                    .dDay(dDay)
                    .build();

            movieChartDTOS.add(toBeChartDTO);

        }

        return movieChartDTOS;

    }



    // Main의 영화 받는 용
    public List<UserResponse.MainChartDTO.MainMovieChartDTO> getMainMovieChart() {

        String q = """
                select id, img_filename, title, SUBSTRING(info, 1, 2) as info, movieCount * 1.0 / allCount as ticketSales
                                from (select id, img_filename, title, SUBSTRING(info, 1, 2) as info, start_date,
                                (select count(id) from seat_book_tb) as allCount,
                                (select count(*) from seat_book_tb sb
                                inner join screening_info_tb si on sb.screening_info_id = si.id
                                where si.movie_id = m.id) as movieCount from movie_tb m)
                                as subquery order by ticketSales
                                desc limit 10
                """;

        Query query = em.createNativeQuery(q);
//        query.setParameter(1, 2);

        List<Object[]> rows = query.getResultList();
        List<UserResponse.MainChartDTO.MainMovieChartDTO> movieChartDTOS = new ArrayList<>();

        for (Object[] row : rows) {
            Integer id = ((Number) row[0]).intValue();
            String imgFilename = (String) row[1];
            String title = (String) row[2];
            String info = (String) row[3];
            Double ticketSales = ((Number) row[4]).doubleValue() * 100;
            // 소수점 이하 두 자리까지 반올림
            ticketSales = Math.round(ticketSales * 100.0) / 100.0;

            String ageInfo;
            if ("전체".equals(info)) {
                ageInfo = info.substring(0, 1);  // "전체"의 첫 글자만 사용
            } else {
                ageInfo = info.substring(0, Math.min(2, info.length()));  // 첫 두 글자 사용
            }

            UserResponse.MainChartDTO.MainMovieChartDTO movieChartDTO = UserResponse.MainChartDTO.MainMovieChartDTO.builder()
                    .id(id)
                    .imgFilename(imgFilename)
                    .title(title)
                    .ageInfo(ageInfo)
                    .ticketSales(ticketSales)
                    .build();

            movieChartDTOS.add(movieChartDTO);

        }

        return movieChartDTOS;

    }


    // 영화 받는 용
    public List<UserResponse.DetailBookDTO.MovieChartDTO> getMovieChart() {
//        String q = """
//                select id, img_filename, title, start_date, (select count(id) from seat_book_tb) as allCount,
//                (select count(*) from seat_book_tb sb
//                inner join screening_info_tb si on sb.screening_info_id = si.id
//                where si.movie_id = m.id) as movieCount,
//                (select count(*) from seat_book_tb sb
//                inner join screening_info_tb si on sb.screening_info_id = si.id
//                where si.movie_id = m.id) * 1.0 / (select count(id) from seat_book_tb sb) as ticketSales
//                from movie_tb m
//                """;

        String q = """
                select id, img_filename, title, start_date, movieCount * 1.0 / allCount as ticketSales
                from (select id, img_filename, title, start_date, 
                (select count(id) from seat_book_tb) as allCount,
                (select count(*) from seat_book_tb sb
                inner join screening_info_tb si on sb.screening_info_id = si.id
                where si.movie_id = m.id) as movieCount from movie_tb m) 
                as subquery order by ticketSales 
                desc limit 6
                   """;

        Query query = em.createNativeQuery(q);
//        query.setParameter(1, 2);

        List<Object[]> rows = query.getResultList();
        List<UserResponse.DetailBookDTO.MovieChartDTO> movieChartDTOS = new ArrayList<>();

        for (Object[] row : rows) {
            Integer id = ((Number) row[0]).intValue();
            String imgFilename = (String) row[1];
            String title = (String) row[2];
            Date startDate = (Date) row[3];
            Double ticketSales = ((Number) row[4]).doubleValue() * 100;
            // 소수점 이하 두 자리까지 반올림
            ticketSales = Math.round(ticketSales * 100.0) / 100.0;

            UserResponse.DetailBookDTO.MovieChartDTO movieChartDTO = UserResponse.DetailBookDTO.MovieChartDTO.builder()
                    .id(id)
                    .imgFilename(imgFilename)
                    .title(title)
                    .startDate(startDate)
                    .ticketSales(ticketSales)
                    .build();

            movieChartDTOS.add(movieChartDTO);

        }

        return movieChartDTOS;

    }

    public List<Object[]> getUserMovieChart() {
        // PK, 순위, 연령, 포스터, 제목, 예매율, 개봉일, 상영 상태
        // TODO: DB에 값이 없어서 '2024-06-21'로 설정함 -> CURRENT_DATE로 바꿔야함
        String sql = """
            SELECT
                m.id,
                m.title,
                m.img_filename,
                m.info,
                m.start_date,
                COUNT(sb.book_id) * 1.0 / (
                                            SELECT COUNT(sb2.book_id)
                                            FROM seat_book_tb sb2
                                            JOIN screening_info_tb si2
                                            ON sb2.screening_info_id = si2.id
                                            WHERE si2.date >= '2024-06-21'
                                            ) AS bookingRate
            FROM movie_tb m
            LEFT JOIN screening_info_tb si ON m.id = si.movie_id
            LEFT JOIN seat_book_tb sb ON si.id = sb.screening_info_id
            WHERE m.end_date >= CURRENT_DATE
            GROUP BY m.id, m.title, m.img_filename, m.info, m.start_date
            ORDER BY bookingRate DESC;
            """;
        Query query = em.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();
        return results;
    }

    public List<Object[]> getAdminMovieChart() {
        // PK, 순위, 연령, 포스터, 제목, 예매율, 개봉일, 상영 상태
        // TODO: WHERE si2.date >= '2024-06-21' -> CURRENT_DATE
        String sql = """
            SELECT
                m.id,
                m.title,
                m.img_filename,
                m.info,
                m.start_date,
                COUNT(sb.book_id) * 1.0 / (
                                            SELECT COUNT(sb2.book_id)
                                            FROM seat_book_tb sb2
                                            JOIN screening_info_tb si2
                                            ON sb2.screening_info_id = si2.id
                                            WHERE si2.date >= '2024-06-21'
                                            ) AS bookingRate
            FROM movie_tb m
            LEFT JOIN screening_info_tb si ON m.id = si.movie_id
            LEFT JOIN seat_book_tb sb ON si.id = sb.screening_info_id
            WHERE m.end_date >= CURRENT_DATE
            GROUP BY m.id, m.title, m.img_filename, m.info, m.start_date
            ORDER BY bookingRate DESC;
            """;
        Query query = em.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();
        return results;
    }

    // TODO: 쿼리로만 할 수 있음.. 서비스로 구현은 나중에
    // 예매율을 계산하는 메서드
    public double getBookingRate(Integer movieId) {
        String sql = """
            
                SELECT
                COUNT(sb.book_id) * 1.0 / (
                                            SELECT COUNT(sb2.book_id)
                                            FROM seat_book_tb sb2
                                            JOIN screening_info_tb si2
                                            ON sb2.screening_info_id = si2.id
                                            WHERE si2.date >= '2024-06-21'
                                            ) AS bookingRate
            FROM movie_tb m
            LEFT JOIN screening_info_tb si ON m.id = si.movie_id
            LEFT JOIN seat_book_tb sb ON si.id = sb.screening_info_id
            WHERE si.date >= '2024-06-21' AND si.movie_id = ?
            GROUP BY si.movie_id;
            """;
        // 네이티브 쿼리 생성
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, movieId);

        try {
            // 쿼리 실행 및 결과 가져오기
            Object result = query.getSingleResult();
            if (result == null) return 0.0;

            // 결과를 double로 변환
            double bookingRate = ((Number) result).doubleValue();

            // 퍼센트로 변환
            bookingRate = bookingRate * 100;

            // 소수점 둘째 자리까지 포맷팅
            DecimalFormat format = new DecimalFormat("#.##");
            bookingRate = Double.parseDouble(format.format(bookingRate));

            return bookingRate;
        } catch (NoResultException e) {
            // 예매 내역이 없는 경우
            return 0.0;
        }
    }
}
