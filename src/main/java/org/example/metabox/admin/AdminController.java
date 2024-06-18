package org.example.metabox.admin;

import lombok.RequiredArgsConstructor;
import org.example.metabox.movie.Movie;
import org.example.metabox.movie.MovieResponse;
import org.example.metabox.movie.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final MovieService movieService;

    //    TODO : admin 만 접속할 수 있도록 주소에 interceptor 설정

    // 전체 영화 리스트를 조회하는 GET 요청 처리 메서드
    @GetMapping("/movie-list")
    public String movieList(Model model) {
        // 모든 영화 정보를 가져와 movies 리스트에 저장합니다.
        List<MovieResponse.MovieChartDTO> movies = movieService.getAllMovies();

        // 모델에 movies 을 추가합니다.
        model.addAttribute("movies", movies);

        // "admin/movie-list" 뷰를 반환하여 영화 리스트 페이지를 표시합니다.
        return "admin/movie-list";
    }

    // 'movieId'에 해당하는 영화 정보를 조회하는 GET 요청 처리 메서드
    @GetMapping("/movie-detail/{movieId}")
    public String movieDetail(@PathVariable("movieId") Integer movieId, Model model) {
        // movieId에 해당하는 영화 정보를 데이터베이스에서 조회하고 MovieDetailDTO에 저장합니다.
        MovieResponse.MovieDetailDTO movie = movieService.findById(movieId);

        // 조회한 영화 정보를 모델에 추가합니다.
        model.addAttribute("movie", movie);

        // "admin/movie-detail" 뷰를 반환하여 영화 상세 페이지를 표시합니다.
        return "admin/movie-detail";
    }



    @GetMapping("/movie-edit-form")
    public String movieEditForm() {
        return "admin/movie-edit-form";
    }

    @GetMapping("/movie-save-form")
    public String movieSaveForm() {
        return "admin/movie-save-form";
    }

    @GetMapping("/theater-save-form")
    public String theaterSaveForm() {
        return "admin/theater-save-form";
    }
}
