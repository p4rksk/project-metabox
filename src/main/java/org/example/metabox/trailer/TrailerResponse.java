package org.example.metabox.trailer;

import lombok.Data;

public class TrailerResponse {

    @Data
    public static class TrailerDTO {
        private int id;
        private String masterM3U8Filename;


        public TrailerDTO(String masterM3U8Filename, int id) {
            this.masterM3U8Filename = masterM3U8Filename;
            this.id = id;
        }
    }

    @Data
    public static class TopMovieDTO {
        private int movieId;

        public TopMovieDTO(int movieId) {
            this.movieId = movieId;
        }
    }

}
