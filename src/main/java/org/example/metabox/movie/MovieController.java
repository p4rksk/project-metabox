package org.example.metabox.movie;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MovieController {

    private final MovieService movieService;

    // 무비 차트
    @GetMapping("/movies/list")
    public String list(@RequestParam(defaultValue = "all") String type, HttpServletRequest request) {
        List<MovieResponse.UserMovieChartDTO> movies;
        // 상영예정작 버튼 클릭 시
        boolean isUpcoming = "upcoming".equals(type);

        if (isUpcoming) {
            movies = movieService.getUpcomingMovieChart();
        } else {
            movies = movieService.getMovieChart();
        }

        request.setAttribute("models", movies);
        request.setAttribute("isUpcoming", isUpcoming);
        return "movie/list";
    }

    // 영화 상세 페이지
    @GetMapping("/movies/detail/{movieId}")
    public String detail(@PathVariable("movieId") int movieId, HttpServletRequest request) {
        MovieResponse.MovieDetailDTO movieDetail = movieService.getMovieDetail(movieId);
        request.setAttribute("model", movieDetail);
        return "movie/detail";
    }

}
