package org.example.metabox.review;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.metabox._core.errors.exception.Exception401;
import org.example.metabox.movie.Movie;
import org.example.metabox.movie.MovieRepository;
import org.example.metabox.movie.MovieRequest;
import org.example.metabox.movie_pic.MoviePic;
import org.example.metabox.trailer.Trailer;
import org.example.metabox.user.User;
import org.example.metabox.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponse.ReviewDTO addComment(ReviewRequest.ReviewSaveDTO req, Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception401("User not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new Exception401("존재하지 않는 영화입니다!."));
        Review review = reviewRepository.save(req.toEntity(user, movie));
        return new ReviewResponse.ReviewDTO(review,user);
    }

    public Movie getMovie(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new Exception401("존재하지 않는 영화입니다!."));
        return movie;
    }
}
