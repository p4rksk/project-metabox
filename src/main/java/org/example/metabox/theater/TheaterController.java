package org.example.metabox.theater;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox._core.util.ApiUtil;
import org.example.metabox._core.util.FormatUtil;
import org.example.metabox.user.SessionUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class TheaterController {
    private final TheaterService theaterService;
    private final HttpSession session;

    @GetMapping("/theaters/info")
    public String theatersInfo(HttpServletRequest request, @RequestParam(value = "theaterId", defaultValue = "1") Integer theaterId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        TheaterResponse.TheaterInfoDTO respDTO = theaterService.theaterInfo(sessionUser, theaterId);
        request.setAttribute("model", respDTO);
        return "theater/info";
    }

    @GetMapping("/theaters/login-form")
    public String theatersLoginForm() {
        return "theater/login-form";
    }

    @PostMapping("/theaters-login")
    public String theaterLogin(TheaterRequest.LoginDTO reqDTO) {
        Theater theater = theaterService.login(reqDTO);
        SessionTheater sessionTheater = new SessionTheater(theater);
        session.setAttribute("sessionTheater", sessionTheater);
        // TODO: 임시 페이지로 리턴(극장 메인 페이지 현재 없음)
        return "theater/sales-management";
    }

    @GetMapping("/theaters/movie-schedule")
    public String theatersMovieSchedule(HttpServletRequest request, @RequestParam(value = "theaterId", defaultValue = "1") Integer theaterId, @RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null) {
            date = FormatUtil.currentDate();
        }
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        TheaterResponse.TheaterDTO respDTO = theaterService.movieSchedule(sessionUser, theaterId, date);
        request.setAttribute("model", respDTO);
        return "theater/movie-schedule";
    }

    @GetMapping("/theaters/movie-schedule-ajax")
    public ResponseEntity<?> theaterMovieScheduleDate(@RequestParam(value = "theaterId") Integer theaterId, @RequestParam(value = "date") String date) {
        TheaterResponse.TheaterAjaxDTO respDTO = theaterService.movieScheduleDate(theaterId, LocalDate.parse(date));
        System.out.println(ResponseEntity.ok(new ApiUtil<>(respDTO)));
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    @GetMapping("/theater/sales-management")
    public String getSalesManagement(HttpServletRequest request) {
        // 세션에서 로그인한 지점의 정보를 얻음
        // SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        // TheaterResponse.TheaterInfoDTO respDTO = theaterService.theaterInfo(sessionUser, theaterId);

        int theaterId = 1;
        TheaterResponse.TheaterSalesDTO theater = theaterService.getThearerSalesInfo(theaterId);
        request.setAttribute("model", theater);
        return "theater/sales-management";
    }


}