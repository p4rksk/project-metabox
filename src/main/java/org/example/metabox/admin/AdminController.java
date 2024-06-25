package org.example.metabox.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.movie.MovieRequest;
import org.example.metabox.movie.MovieResponse;
import org.example.metabox.movie.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final AdminService adminService;
    private final MovieService movieService;
    private final HttpSession session;

    @GetMapping("/admin-login-form")
    public String Login() {
        return "admin/login-form";
    }

    @PostMapping("/admin-login")
    public String adminLogin(AdminRequest.LoginDTO reqDTO) {
        Admin admin = adminService.login(reqDTO);

        SessionAdmin sessionAdmin = new SessionAdmin(admin.getId(), admin.getLoginId());
        session.setAttribute("sessionAdmin", sessionAdmin);
        return "redirect:movie-list";
    }

    //    TODO : admin 만 접속할 수 있도록 주소에 interceptor 설정
    //    TODO : description 칼럼 데이터 입력 방법 수정

    // 관리자 무비 차트(기본값 - 예매율순)
    @GetMapping("/movie-list")
    public String movieList(HttpServletRequest request) {
        List<MovieResponse.AdminMovieChartDTO> movies = movieService.getAdminMovieChart();
        request.setAttribute("models", movies);
        return "admin/movie-list";
    }

    // 영화 상세 페이지
    @GetMapping("/movie-detail/{movieId}")
    public String movieDetail(@PathVariable("movieId") Integer movieId, HttpServletRequest request) {
        MovieResponse.MovieDetailDTO movieDetail = movieService.getMovieDetail(movieId);
        request.setAttribute("model", movieDetail);
        return "admin/movie-detail";
    }

    // 영화 등록 페이지 호출을 처리하는 GET 요청 메서드
    @GetMapping("/movie-save-form")
    public String movieSaveForm() {
        return "admin/movie-save-form";
    }

    // 영화 등록을 처리하는 POST 요청 메서드
    @PostMapping("/movie-save")
    public String movieAdd(MovieRequest.MovieSavaFormDTO reqDTO) {
        movieService.addMovie(reqDTO);
        return "redirect:movie-list";
    }

    @GetMapping("/movie-edit-form/{movieId}")
    public String movieEditForm(@PathVariable("movieId") Integer movieId, HttpServletRequest request) {
        MovieResponse.MovieDetailDTO movieDetail = movieService.getMovieDetail(movieId);
        request.setAttribute("model", movieDetail);
        return "admin/movie-edit-form";
    }

    // 영화 수정
    @PostMapping("/movie-edit")
    public String movieEdit(MovieRequest.MovieInfoEditDTO reqDTO) {
        int movieId = movieService.editMovieInfo(reqDTO);
        return "redirect:movie-detail/" + movieId;
    }

    @GetMapping("/theater-save-form")
    public String theaterSaveForm() {
        return "admin/theater-save-form";
    }
}
