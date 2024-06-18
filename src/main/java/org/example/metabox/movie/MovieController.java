package org.example.metabox.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MovieController {

    @GetMapping("movies/detail")
    public String detail() {
        return "movie/detail";
    }

    @GetMapping("movies/list")
    public String list() {
        return "movie/list";
    }
}
