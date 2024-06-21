package org.example.metabox.theater;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.theater_scrap.TheaterScrapResponse;
import org.example.metabox.user.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class TheaterController {
    private final TheaterService theaterService;
    private final HttpSession session;

    @GetMapping("/theaters/info")
    public String theatersInfo() {
        return "theater/info";
    }

    @GetMapping("/theaters/login-form")
    public String theatersLoginForm() {
        return "theater/login-form";
    }

    @GetMapping("/theaters/movie-schedule")
    public String theatersMovieSchedule(HttpServletRequest request, TheaterRequest.ScheduleDTO reqDTO) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        TheaterResponse.TheaterDTO respDTO = theaterService.movieSchedule(sessionUser, reqDTO.getAreaCode());
        request.setAttribute("model",respDTO);
        return "theater/movie-schedule";
    }
}
