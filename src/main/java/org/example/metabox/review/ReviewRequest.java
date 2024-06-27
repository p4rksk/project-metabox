package org.example.metabox.review;

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
        private double rating;
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
