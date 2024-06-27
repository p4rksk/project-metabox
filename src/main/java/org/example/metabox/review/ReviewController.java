package org.example.metabox.review;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.movie.Movie;
import org.example.metabox.movie.MovieRequest;
import org.example.metabox.user.SessionUser;
import org.example.metabox.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final HttpSession session;

    @PostMapping("/review-save")
    public String reviewAdd(ReviewRequest.ReviewSaveDTO req, @RequestParam("movieId") Integer movieId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        reviewService.addComment(req, sessionUser.getId(), movieId);
        return "redirect:movies/detail/"+movieId;
    }
}
