package org.example.metabox.movie;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MovieController {

    private final MovieService movieService;

    // 무비 차트(기본값 - 예매율순)
    @GetMapping("/movies/list")
    public String list(HttpServletRequest request) {
        List<MovieResponse.UserMovieChartDTO> movies = movieService.getMovieChart();
        request.setAttribute("models", movies);
        return "movie/list";
    }

    // 상영 예정작(기본값 - 예매율순)
    @GetMapping("/movies/upcoming-list")
    public String getUpcomingChart(HttpServletRequest request){
        List<MovieResponse.UserMovieChartDTO> movies = movieService.getUpcomingMovieChart();
        request.setAttribute("models", movies);
        return "movie/upcoming-list";
    }

    // 영화 상세 페이지
    @GetMapping("/movies/detail/{movieId}")
    public String detail(@PathVariable("movieId") int movieId, HttpServletRequest request) {
        MovieResponse.MovieDetailDTO movieDetail = movieService.getMovieDetail(movieId);
        request.setAttribute("model", movieDetail);
        return "movie/detail";
    }

}
