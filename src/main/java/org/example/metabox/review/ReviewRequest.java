package org.example.metabox.review;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.metabox.movie.Movie;
import org.example.metabox.theater.Theater;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.example.metabox.user.User;

public class ReviewRequest {

    @Data
    public static class ReviewSaveDTO {
        private User user;
        private Movie movie;

        @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
        @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
        private int rating;
        @NotEmpty(message = "댓글은 필수 입력 항목입니다.")
        private String comment;

        public Review toEntity (User user, Movie movie) {
            return Review.builder()
                    .comment(comment)
                    .rating(rating)
                    .user(user)
                    .movie(movie)
                    .build();
        }
    }
}
