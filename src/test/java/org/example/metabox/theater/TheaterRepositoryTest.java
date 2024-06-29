package org.example.metabox.theater;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class TheaterRepositoryTest {

    @Autowired
    private TheaterRepository theaterRepository;

    @Test
    public void getTheaterSales_Test() {
        // given
        int theaterId = 1;

        // when
        // List<Object[]> result = theaterRepository.getTheaterSales(theaterId);
        List<Object[]> result = theaterRepository.getTheaterSales(theaterId);

        // then
        Object[] row = result.get(0);
        System.out.println("######### 지역: " + row[0]);
        System.out.println("##### 극장 이름: " + row[1]);
        System.out.println("##### 극장 주소: " + row[2]);
        System.out.println("##### 극장 번호: " + row[3]);
        System.out.println("##### 극장 매출: " + row[4]);
    }

    @Test
    public void findSalesByTheater_Test() {
        // when
        List<Object[]> results = theaterRepository.findSalesByTheater();

        // then
        for (Object[] result : results) {
            Integer theaterId = (Integer) result[0];
            String theaterName = (String) result[1];
            Long theaterSales = (Long) result[2];

            System.out.println("#####  극장 PK: " + theaterId);
            System.out.println("##### 극장 이름: " + theaterName);
            System.out.println("##### 극장 매출: " + theaterSales);
        }
    }

    @Test
    public void findTheaterSalesByMovie_Test() {
        // given
        int theaterId = 3;

        // when
        List<Object[]> results = theaterRepository.findTheaterSalesByMovie(theaterId);

        // then
        for (Object[] result : results) {
            int movieId = (Integer) result[0];
            String movieTitle = (String) result[1];
            Date startDate = (Date) result[2];
            Date endDate = (Date) result[3];
            Long movieSales = (Long) result[4];

            System.out.println("######   영화 PK: " + movieId);
            System.out.println("#####   영화 매출: " + movieTitle);
            System.out.println("##### 영화 시작일: " + startDate);
            System.out.println("##### 영화 종료일: " + endDate);
            System.out.println("#####   영화 매출: " + movieSales);
            System.out.println("==============================");
        }
    }

    @Test
    public void findTotalSales_Test(){
        // given

        // when
        Long result = theaterRepository.findTotalSales();

        // then
        System.out.println("#####" + result);
    }

    @Test
    public void findAllTheaterSalesByMovie_Test() {
        // given

        // when
        List<Object[]> results = theaterRepository.findAllTheaterSalesByMovie();

        // then
        for (Object[] result : results) {
            int movieId = (Integer) result[0];
            String movieTitle = (String) result[1];
            Date startDate = (Date) result[2];
            Date endDate = (Date) result[3];
            Long movieSales = (Long) result[4];
            Long viewerCount = (Long) result[5];

            System.out.println("######   영화 PK: " + movieId);
            System.out.println("#####   영화 제목: " + movieTitle);
            System.out.println("##### 영화 시작일: " + startDate);
            System.out.println("##### 영화 종료일: " + endDate);
            System.out.println("#####   영화 매출: " + movieSales);
            System.out.println("#####   관객 수: " + viewerCount);
            System.out.println("==============================");
        }
    }

}
