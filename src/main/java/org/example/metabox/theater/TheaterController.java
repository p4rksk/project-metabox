package org.example.metabox.theater;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.metabox._core.util.ApiUtil;
import org.example.metabox._core.util.FormatUtil;
import org.example.metabox.screening.ScreeningRepository;
import org.example.metabox.screening.ScreeningResponse;
import org.example.metabox.user.SessionUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TheaterController {
    private final TheaterService theaterService;
    private final HttpSession session;
    private final RedisTemplate<String, Object> rt;

    @GetMapping("/theaters/info")
    public String theatersInfo(HttpServletRequest request, @RequestParam(value = "theaterId", defaultValue = "1") Integer theaterId) {
        SessionUser sessionUser = (SessionUser) rt.opsForValue().get("sessionUser");
        TheaterResponse.TheaterInfoDTO respDTO = theaterService.theaterInfo(sessionUser, theaterId);
        request.setAttribute("model", respDTO);
        return "theater/info";
    }

    @GetMapping("/theaters/login-form")
    public String theatersLoginForm() {
        return "theater/login-form";
    }

    @PostMapping("/theaters-login")
    public String theaterLogin(@Valid TheaterRequest.LoginDTO reqDTO, Errors errors) {
        Theater theater = theaterService.login(reqDTO);
        SessionTheater sessionTheater = new SessionTheater(theater);
        rt.opsForValue().set("sessionTheater", sessionTheater);
        session.setAttribute("sessionTheater", sessionTheater);
        return "redirect:/theater/screening-info-form";
    }

    @GetMapping("/theaters/movie-schedule")
    public String theatersMovieSchedule(HttpServletRequest request, @RequestParam(value = "theaterId", defaultValue = "1") Integer theaterId, @RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null) {
            date = FormatUtil.currentDate();
        }
        SessionUser sessionUser = (SessionUser) rt.opsForValue().get("sessionUser");
        Integer userId = null;
        if (sessionUser != null) {
            userId = sessionUser.getId();
        }
        TheaterResponse.TheaterDTO respDTO = theaterService.movieSchedule(userId, theaterId, date);
        request.setAttribute("model", respDTO);
        return "theater/movie-schedule";
    }

    @GetMapping("/theaters/guest/movie-schedule")
    public String theatersGuestMovieSchedule(HttpServletRequest request, @RequestParam(value = "theaterId", defaultValue = "1") Integer theaterId, @RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null) {
            date = FormatUtil.currentDate();
        }
        Integer userId = null;
        TheaterResponse.TheaterDTO respDTO = theaterService.movieSchedule(userId, theaterId, date);
        request.setAttribute("model", respDTO);
        return "theater/movie-guest-schedule";
    }

    @GetMapping("/theaters/movie-schedule-ajax")
    public ResponseEntity<?> theaterMovieScheduleDate(@RequestParam(value = "theaterId") Integer theaterId, @RequestParam(value = "date") String date) {
        TheaterResponse.TheaterAjaxDTO respDTO = theaterService.movieScheduleDate(theaterId, LocalDate.parse(date));
        System.out.println(ResponseEntity.ok(new ApiUtil<>(respDTO)));
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    @GetMapping("/theater/sales-management")
    public String getSalesManagement(HttpServletRequest request) {
        SessionTheater sessionTheater = (SessionTheater) rt.opsForValue().get("sessionTheater");
        TheaterResponse.TheaterSalesDTO respDTO = theaterService.getThearerSalesInfo(sessionTheater.getId());
        request.setAttribute("model", respDTO);
        return "theater/sales-management";
    }

    @GetMapping("/theater/screening-info-form")
    public String theaterScreeningInfoForm(HttpServletRequest request) { return "theater/screening-info-form"; }

    @GetMapping("/theaters/find-screening")
    public ResponseEntity<List<TheaterResponse.ScreeningAjaxDTO>> getScreeningByTheater(HttpServletRequest request) {
        // 세션에서 상영관 정보 가져오기
        SessionTheater sessionTheater = (SessionTheater) rt.opsForValue().get("sessionTheater");

        // 상영관 ID로 상영 정보 가져오기
        List<TheaterResponse.ScreeningAjaxDTO> respDTO = theaterService.getThearerScreening(sessionTheater.getId());

        // 응답 데이터 반환
        return ResponseEntity.ok(respDTO);
    }

    @GetMapping("/theaters/find-movie")
    public ResponseEntity<List<TheaterResponse.MovieAjaxDTO>> getMovies(HttpServletRequest request) {
        List<TheaterResponse.MovieAjaxDTO> respDTO = theaterService.getMovie();
        return ResponseEntity.ok(respDTO);
    }

    @PostMapping("/theaters/screening-info-save")
    public String saveScreeningInfo(TheaterRequest.ScreeningInfoDTO reqDTO){
        SessionTheater sessionTheater = (SessionTheater) rt.opsForValue().get("sessionTheater");
        theaterService.saveScreeningInfo(reqDTO);
        return "redirect:/theaters/movie-schedule?theaterId=" + sessionTheater.getId();
    }

}