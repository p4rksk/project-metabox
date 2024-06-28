package org.example.metabox.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.movie.MovieRequest;
import org.example.metabox.movie.MovieResponse;
import org.example.metabox.movie.MovieService;
import org.example.metabox.movie_pic.MoviePicRequest;
import org.example.metabox.movie_pic.MoviePicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final AdminService adminService;
    private final MovieService movieService;
    private final HttpSession session;
    private final MoviePicService moviePicService;

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

    // 관리자 무비 상영 예정 차트(기본값 - 예매율순)
    @GetMapping("/movie-upcoming")
    public String movieUpcomingList(HttpServletRequest request) {
        List<MovieResponse.AdminMovieChartDTO> movies = movieService.getAdminUpcomingMovieChart();
        request.setAttribute("models", movies);
        return "admin/movie-upcoming";
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

    // 영화 수정 페이지 이동
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
        // 수정한 영화 페이지로 리다이렉트
        return "redirect:movie-detail/" + movieId;
    }

    // 영화 삭제
    @ResponseBody
    @PostMapping("/movie-delete/{movieId}")
    public String deleteMovie(@PathVariable("movieId") int movieId) {
        try {
            movieService.deleteMovie(movieId);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    // 스틸컷 삭제
    @ResponseBody
    @PostMapping("/stills-delete/{id}")
    public String deleteStills(@PathVariable("id") int id){
        try {
            moviePicService.deleteStills(id);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    // 스틸컷 추가
    @ResponseBody
    @PostMapping("/stills-add/{movieId}")
    public String addStills(@PathVariable("movieId") int movieId, MoviePicRequest.StillsAddDTO reqDTO){
        try {
            moviePicService.addStills(movieId, reqDTO);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    // localhost:8080/admin-sales
    // 관리자 매출 페이지
    @GetMapping("/admin-sales")
    public String getSales(HttpServletRequest request){
        AdminResponse.RootAdminResponseDTO resDTO = adminService.getRootAdmin();
        request.setAttribute("models", resDTO);
        return "admin/admin-sales";
    }


    @GetMapping("/theater-save-form")
    public String theaterSaveForm() {
        return "admin/theater-save-form";
    }
}
