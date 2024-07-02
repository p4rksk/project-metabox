package org.example.metabox.movie_scrap;

import lombok.Data;

import java.sql.Date;
import java.util.List;

public class MovieScrapResponse {

    @Data
    public static class ScrapDTO {
        private int userId;
        private int movieId;

        public ScrapDTO(MovieScrap movieScrap) {
            this.userId = movieScrap.getUser().getId();
            this.movieId = movieScrap.getMovie().getId();
        }
    }

    @Data
    public static class MovieScrapDTO {
        private List<ScrapMovieListDTO> scrapMovies;
        private Integer scrapCount;

        public MovieScrapDTO(List<ScrapMovieListDTO> scrapMovies) {
            this.scrapMovies = scrapMovies;
            this.scrapCount = scrapMovies.size();
        }

        @Data
        public static class ScrapMovieListDTO {
            private Integer id;
            private Integer movieId;
            private Integer userId;
            private String title;
            private String imgFilename;
            private Date startDate;
            private Date endDate;

            public ScrapMovieListDTO(MovieScrap movieScrap) {
                this.id = movieScrap.getId();
                this.movieId = movieScrap.getMovie().getId();
                this.userId = movieScrap.getUser().getId();
                this.title = movieScrap.getMovie().getTitle();
                this.imgFilename = movieScrap.getMovie().getImgFilename();
                this.startDate = movieScrap.getMovie().getStartDate();
                this.endDate = movieScrap.getMovie().getEndDate();
            }
        }
    }
}
