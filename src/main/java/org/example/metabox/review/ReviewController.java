package org.example.metabox.review;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.user.SessionUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final HttpSession session;
    private final RedisTemplate<String, Object> rt;

    @PostMapping("/review-save")
    public String reviewAdd(ReviewRequest.ReviewSaveDTO req, @RequestParam("movieId") Integer movieId) {
        SessionUser sessionUser = (SessionUser) rt.opsForValue().get("sessionUser");
        reviewService.addComment(req, sessionUser.getId(), movieId);
        return "redirect:movies/detail/" + movieId;
    }

    @GetMapping("/review-list")
    public String reviewList(HttpServletRequest request) {
        SessionUser sessionUser = (SessionUser) rt.opsForValue().get("sessionUser");
        List<ReviewResponse.ReviewListDTO> reviewList = reviewService.getReviewList(sessionUser);
        request.setAttribute("reviewList", reviewList);
        return "user/mypage-detail-review";
    }

    @PostMapping("/review/delete/{id}")
    public String reviewDelete(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return "redirect:/review-list";
    }
}
