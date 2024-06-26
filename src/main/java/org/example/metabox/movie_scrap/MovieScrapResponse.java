package org.example.metabox.movie_scrap;

import lombok.Data;

public class MovieScrapResponse {

    @Data
    public static class ScrapDTO{
        private int userId;
        private int movieId;

        public ScrapDTO(MovieScrap movieScrap) {
            this.userId = movieScrap.getUser().getId();
            this.movieId = movieScrap.getMovie().getId();
        }
    }
}
