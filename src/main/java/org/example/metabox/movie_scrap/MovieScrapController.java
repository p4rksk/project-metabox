package org.example.metabox.movie_scrap;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.user.SessionUser;
import org.example.metabox.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MovieScrapController {
    private final MovieScrapService movieScrapService;
    private final HttpSession session;

    @PostMapping("/scrap/{id}/movie")
    public String scrapMovie(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        movieScrapService.movieDetailScrap(id,sessionUser.getId());
        return "redirect:/movies/list";
    }
}
