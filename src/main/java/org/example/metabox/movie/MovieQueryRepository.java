package org.example.metabox.movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.example.metabox.user.UserResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MovieQueryRepository {
    private final EntityManager em;

    // Main의 영화 받는 용
    public List<UserResponse.MainChartDTO.MainMovieChartDTO> getMainMovieChart() {

        String q = """
                select id, img_filename, title, movieCount * 1.0 / allCount as ticketSales
                from (select id, img_filename, title, start_date, 
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
            Date startDate = (Date) row[3];
            Double ticketSales = ((Number) row[6]).doubleValue() * 100;
            // 소수점 이하 두 자리까지 반올림
            ticketSales = Math.round(ticketSales * 100.0) / 100.0;

            UserResponse.MainChartDTO.MainMovieChartDTO movieChartDTO = UserResponse.MainChartDTO.MainMovieChartDTO.builder()
                    .id(id)
                    .imgFilename(imgFilename)
                    .title(title)

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

}
