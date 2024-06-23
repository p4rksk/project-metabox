package org.example.metabox.movie;

import jakarta.persistence.EntityManager;
import org.example.metabox.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(MovieQueryRepository.class)
public class MovieQueryRepositoryTest {
    @Autowired
    private MovieQueryRepository movieQueryRepository;
    @Autowired
    private EntityManager em;


    @Test
    public void getMainMovieChart_test(){
        // given

        // when
        List<UserResponse.MainChartDTO.MainMovieChartDTO> mainChartDTOs = movieQueryRepository.getMainMovieChart();

        // then
//        System.out.println("확인 " + mainChartDTOs);
        assertThat(mainChartDTOs.get(0).getId()).isEqualTo(1);
        assertThat(mainChartDTOs.get(0).getTitle()).isEqualTo("인사이드 아웃 2");
        assertThat(mainChartDTOs.size()).isEqualTo(10);

    }


    @Test
    public void getMovieChart_test(){
        // given

        // when
        List<UserResponse.DetailBookDTO.MovieChartDTO> movieChartDTO = movieQueryRepository.getMovieChart();

        // then
//        System.out.println("확인 " + movieChartDTO);
        assertThat(movieChartDTO.get(0).getId()).isEqualTo(1);
        assertThat(movieChartDTO.get(0).getTitle()).isEqualTo("인사이드 아웃 2");
        assertThat(movieChartDTO.size()).isEqualTo(6);


    }

}
