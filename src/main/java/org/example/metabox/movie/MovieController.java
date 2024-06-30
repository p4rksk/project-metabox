package org.example.metabox.movie;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MovieController {

    private final MovieService movieService;

    // 무비 차트
    @GetMapping("/movies/list")
    public String list(@RequestParam(defaultValue = "all") String type,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "4") int size,
                       HttpServletRequest request) {
        Page<MovieResponse.UserMovieChartDTO> movies;
        boolean isUpcoming = "upcoming".equals(type);
        Pageable pageable = PageRequest.of(page, size);

        if (isUpcoming) {
            movies = movieService.getUpcomingMovieChart(pageable);
        } else {
            movies = movieService.getMovieChart(pageable);
        }

        request.setAttribute("models", movies.getContent());
        request.setAttribute("isUpcoming", isUpcoming);
        request.setAttribute("totalPages", movies.getTotalPages());
        request.setAttribute("currentPage", page);
        return "movie/list";
    }

    // 무비 차트 JSON 데이터
    @GetMapping("/movies/list/data")
    @ResponseBody
    public Map<String, Object> listData(@RequestParam(defaultValue = "all") String type,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "4") int size) {
        Page<MovieResponse.UserMovieChartDTO> movies;
        boolean isUpcoming = "upcoming".equals(type);
        Pageable pageable = PageRequest.of(page, size);

        if (isUpcoming) {
            movies = movieService.getUpcomingMovieChart(pageable);
        } else {
            movies = movieService.getMovieChart(pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("models", movies.getContent());
        response.put("isUpcoming", isUpcoming);
        response.put("totalPages", movies.getTotalPages());
        response.put("currentPage", page);

        return response;
    }

    // 영화 상세 페이지
    @GetMapping("/movies/detail/{movieId}")
    public String detail(@PathVariable("movieId") int movieId, HttpServletRequest request) {
        MovieResponse.MovieDetailDTO movieDetail = movieService.getMovieDetail(movieId);
        request.setAttribute("model", movieDetail);
        return "movie/detail";
    }

    // 검색기능(영화만 검색 가능, cgv도 그러함)
    @GetMapping("/search")
    public String search(@RequestParam("query") String query, HttpServletRequest request) {
        int movieId = movieService.searchMoviesByTitle(query);
        return "redirect:/movie-detail/" + movieId;
    }
}
