package org.example.metabox.theater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class TheaterController {

    @GetMapping("/theaters/info")
    public String theatersInfo() {
        return "theater/info";
    }

    @GetMapping("/theaters/login-form")
    public String theatersLoginForm() {
        return "theater/login-form";
    }

    @GetMapping("/theaters/movie-schedule")
    public String theatersMovieSchedule() {
        return "theater/movie-schedule";
    }
}
