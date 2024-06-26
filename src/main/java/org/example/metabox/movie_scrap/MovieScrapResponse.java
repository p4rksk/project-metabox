package org.example.metabox.movie_scrap;

import lombok.Data;

import java.util.List;

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

    @Data
    public static class ScrapMovieListDTO {
        private Integer id;
        private Integer movieId;
        private Integer userId;
        private String title;
        private String imgFilename;

        public ScrapMovieListDTO(MovieScrap movieScrap) {
            this.id = movieScrap.getId();
            this.movieId = movieScrap.getMovie().getId();
            this.userId = movieScrap.getUser().getId();
            this.title = movieScrap.getMovie().getTitle();
            this.imgFilename = movieScrap.getMovie().getImgFilename();
        }
    }
}
