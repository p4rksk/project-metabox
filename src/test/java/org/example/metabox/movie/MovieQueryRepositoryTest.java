package org.example.metabox.movie;

import jakarta.persistence.EntityManager;
import org.example.metabox.review.Review;
import org.example.metabox.review.ReviewRepository;
import org.example.metabox.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(MovieQueryRepository.class)
public class MovieQueryRepositoryTest {
    @Autowired
    private MovieQueryRepository movieQueryRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private ReviewRepository reviewRepository;


    @Test
    public void findUnwatchTicketV1_test(){
        // given
        Integer sessionUserId = 1;

        // when
        List<UserResponse.DetailBookDTO.SeatDTO> seatDTOS = movieQueryRepository.findUnwatchTicketV1(sessionUserId);

        // then
        System.out.println("seatDTOS = " + seatDTOS);
        assertThat(seatDTOS.get(0).getBookId()).isEqualTo(1);
        assertThat(seatDTOS.get(2).getBookId()).isEqualTo(2);
        assertThat(seatDTOS.get(1).getSeatCode()).isEqualTo("E11");
        assertThat(seatDTOS.get(2).getSeatCode()).isEqualTo("E4");

    }

    @Test
    public void findUnwatchTicketV2_test(){
        // given
        Integer sessionUserId = 1;
        List<UserResponse.DetailBookDTO.TotalPriceDTO> totalPriceDTOs = new ArrayList<>();
        List<UserResponse.DetailBookDTO.SeatDTO> seatDTOS = new ArrayList<>();
        totalPriceDTOs.add(new UserResponse.DetailBookDTO.TotalPriceDTO(1, 180000));
        seatDTOS.add(new UserResponse.DetailBookDTO.SeatDTO(1,"1234"));

        // when
        List<UserResponse.DetailBookDTO.TicketingDTO> ticketingDTOS = movieQueryRepository.findUnwatchTicketV2(sessionUserId, totalPriceDTOs, seatDTOS);

        // then
        System.out.println("ticketingDTOS = " + ticketingDTOS);
        assertThat(ticketingDTOS.get(0).getId()).isEqualTo(1);
        assertThat(ticketingDTOS.get(0).getTitle()).isEqualTo("인사이드 아웃 2");
        assertThat(ticketingDTOS.get(0).getEngTitle()).isEqualTo("Inside Out 2");
        assertThat(ticketingDTOS.get(0).getAgeInfo()).isEqualTo("전");

    }

    @Test
    public void findUnwatchTicketV3_test(){
        // given
        Integer sessionUserId = 1;

        // when
        List<UserResponse.DetailBookDTO.TotalPriceDTO> totalPriceDTOS = movieQueryRepository.findUnwatchTicketV3(sessionUserId);

        // then
        System.out.println("totalPriceDTOS = " + totalPriceDTOS);
        assertThat(totalPriceDTOS.get(0).getBookId()).isEqualTo(1);
        assertThat(totalPriceDTOS.get(1).getTotalPrice()).isEqualTo(180000);
    }
    
    @Test
    public void findMyTicketing_test(){
        // given
        Integer sessionUserId = 1;

        // when
        List<UserResponse.MyPageHomeDTO.TicketingDTO> ticketingDTOS = movieQueryRepository.findMyTicketing(sessionUserId);

        // then
        System.out.println("ticketingDTOS = " + ticketingDTOS);
        assertThat(ticketingDTOS.get(0).getId()).isEqualTo(1);
        assertThat(ticketingDTOS.get(0).getTitle()).isEqualTo("인사이드 아웃 2");
        assertThat(ticketingDTOS).size().isEqualTo(2);

            
    }

    @Test
    public void getToBeChart_test(){
        // given

        // when
        List<UserResponse.MainChartDTO.ToBeChartDTO> toBeChartDTOS = movieQueryRepository.getToBeChart();

        // then
        System.out.println("toBeChartDTOS = " + toBeChartDTOS);
        assertThat(toBeChartDTOS.size()).isEqualTo(9);
        assertThat(toBeChartDTOS.get(0).getMovieId()).isEqualTo(11);
        assertThat(toBeChartDTOS.get(0).getTitle()).isEqualTo("콰이어트 플레이스-첫째 날");

    }

    @Test
    public void getMainMovieChart_test(){
        // given

        // when
        List<UserResponse.MainChartDTO.MainMovieChartDTO> mainChartDTOs = movieQueryRepository.getMainMovieChart();

        // then
//        System.out.println("확인 " + mainChartDTOs);
        assertThat(mainChartDTOs.get(0).getMovieId()).isEqualTo(1);
//        assertThat(mainChartDTOs.get(0).getTitle()).isEqualTo("인사이드 아웃 2");
        assertThat(mainChartDTOs.size()).isEqualTo(10);

    }


    @Test
    public void getMovieChart_test(){
        // given

        // when
        List<UserResponse.DetailBookDTO.MovieChartDTO> movieChartDTO = movieQueryRepository.getMovieChart();

        // then
//        System.out.println("확인 " + movieChartDTO);
        assertThat(movieChartDTO.get(0).getMovieId()).isEqualTo(1);
//        assertThat(movieChartDTO.get(0).getTitle()).isEqualTo("인사이드 아웃 2");
        assertThat(movieChartDTO.size()).isEqualTo(6);


    }

    @Test
    public void getUserMovieChart_Test(){
        // given

        // when
        List<Object[]> results = movieQueryRepository.getUserMovieChart();

        // then
        for (Object[] result : results) {
            System.out.println("Movie ID: " + result[0]);
            System.out.println("Title: " + result[1]);
            System.out.println("Image Filename: " + result[2]);
            System.out.println("Info: " + result[3]);
            System.out.println("Start Date: " + result[4]);
            System.out.println("Booking Rate: " + result[5]);
        }
    }

    @Test
    public void findByMovieId_Test(){
        // given
        int movieId = 1;

        // when
        List<Review> results = reviewRepository.findByMovieId(movieId);

        // then
        results.forEach(review -> System.out.println(review.getId() + ": " + review.getComment()));
    }


    @Test
    public void getBookingRate_Test(){
        int movieId = 1;

        double result = movieQueryRepository.getBookingRate(movieId);

        System.out.println("##########"+ result);

    }
}
