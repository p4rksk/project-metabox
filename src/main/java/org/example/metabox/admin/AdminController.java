package org.example.metabox.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.movie.MovieRequest;
import org.example.metabox.movie.MovieResponse;
import org.example.metabox.movie.MovieService;
import org.example.metabox.movie_pic.MoviePicRequest;
import org.example.metabox.movie_pic.MoviePicService;
import org.example.metabox.theater.TheaterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final AdminService adminService;
    private final MovieService movieService;
    private final HttpSession session;
    private final MoviePicService moviePicService;
    private final RedisTemplate<String, Object> rt;

    @GetMapping("/admin-login-form")
    public String Login() {
        return "admin/login-form";
    }

    @PostMapping("/admin-login")
    public String adminLogin(AdminRequest.LoginDTO reqDTO) {
        Admin admin = adminService.login(reqDTO);
        SessionAdmin sessionAdmin = new SessionAdmin(admin.getId(), admin.getLoginId());
        rt.opsForValue().set("sessionAdmin", sessionAdmin);
        session.setAttribute("sessionAdmin", sessionAdmin);
        return "redirect:movie-list";
    }

    //    TODO : admin 만 접속할 수 있도록 주소에 interceptor 설정
    //    TODO : description 칼럼 데이터 입력 방법 수정

    @GetMapping("/movie-list")
    public String movieList(@RequestParam(defaultValue = "all") String type,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "4") int size,
                            HttpServletRequest request) {
        Page<MovieResponse.AdminMovieChartDTO> movies;
        boolean isUpcoming = "upcoming".equals(type);
        Pageable pageable = PageRequest.of(page, size);

        if (isUpcoming) {
            movies = movieService.getAdminUpcomingMovieChart(pageable);
        } else {
            movies = movieService.getAdminMovieChart(pageable);
        }

        request.setAttribute("models", movies.getContent());
        request.setAttribute("isUpcoming", isUpcoming);
        request.setAttribute("totalPages", movies.getTotalPages());
        request.setAttribute("currentPage", page);

        return "admin/movie-list";
    }

    @GetMapping("/movie-list/data")
    @ResponseBody
    public Map<String, Object> listData(@RequestParam(defaultValue = "all") String type,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "4") int size) {
        Page<MovieResponse.AdminMovieChartDTO> movies;
        boolean isUpcoming = "upcoming".equals(type);
        Pageable pageable = PageRequest.of(page, size);

        if (isUpcoming) {
            movies = movieService.getAdminUpcomingMovieChart(pageable);
        } else {
            movies = movieService.getAdminMovieChart(pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("models", movies.getContent());
        response.put("isUpcoming", isUpcoming);
        response.put("totalPages", movies.getTotalPages());
        response.put("currentPage", page);

        return response;
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
    public String deleteStills(@PathVariable("id") int id) {
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
    public String addStills(@PathVariable("movieId") int movieId, MoviePicRequest.StillsAddDTO reqDTO) {
        try {
            moviePicService.addStills(movieId, reqDTO);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    // localhost:8080/admin-sales
    // 관리자 매출 페이지
    @GetMapping("/admin-sales-management")
    public String getSales(@RequestParam(value = "type", defaultValue = "theater") String type, HttpServletRequest request) {
        AdminResponse.RootAdminResponseDTO resDTO = adminService.getRootAdmin();
        request.setAttribute("models", resDTO);
        request.setAttribute("type", type);
        return "admin/admin-sales-management";
    }

    @GetMapping("/theater-save-form")
    public String theaterSaveForm() {
        return "admin/theater-save-form";
    }

    @PostMapping("/theater-save")
    public String saveTheater(TheaterRequest.theaterSaveDTO reqDTO) {
        adminService.saveTheater(reqDTO);
        return "redirect:theaters/info";
    }
}
