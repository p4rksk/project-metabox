package org.example.metabox.movie_scrap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.user.SessionUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MovieScrapController {
    private final MovieScrapService movieScrapService;
    private final HttpSession session;
    private final RedisTemplate<String, Object> rt;

    @PostMapping("/scrap/{id}/movie")
    public String scrapMovie(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) rt.opsForValue().get("sessionUser");
        movieScrapService.movieScrap(id, sessionUser.getId());
        return "redirect:/movies/detail/" + id;
    }

    @PostMapping("/scrap/{id}")
    public String delete(@PathVariable Integer id) {
        movieScrapService.deleteMovieScrap(id);
        return "redirect:/scrap/movie-list";
    }

    @GetMapping("/scrap/movie-list")
    public String scrapDetailMovie(HttpServletRequest request) {
        SessionUser sessionUser = (SessionUser) rt.opsForValue().get("sessionUser");
       MovieScrapResponse.MovieScrapDTO movieScrapList = movieScrapService.movieScrapList(sessionUser.getId());
        request.setAttribute("movieScrapList", movieScrapList);
        return "user/mypage-detail-saw-scrap";
    }

}
