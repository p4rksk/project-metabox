package org.example.metabox.movie_scrap;

import lombok.Data;
import org.example.metabox.movie.Movie;
import org.example.metabox.user.User;


public class MovieScrapRequest {

    @Data
    public static class ScrapMovieDTO {
        private User user;
        private Movie movie;
        public ScrapMovieDTO(User user, Movie movie) {
            this.user = user;
            this.movie = movie;
        }
        public MovieScrap toEntity(){
            return MovieScrap.builder()
                    .user(user)
                    .movie(movie)
                    .build();
        }

    }
}
