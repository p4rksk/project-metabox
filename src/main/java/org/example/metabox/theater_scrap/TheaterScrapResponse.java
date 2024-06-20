package org.example.metabox.theater_scrap;

import lombok.AllArgsConstructor;
import lombok.Data;

public class TheaterScrapResponse {

    @Data
    @AllArgsConstructor
    public static class TheaterScrapDTO {
        private int id;
        private String theaterName;

        public TheaterScrapDTO(TheaterScrap theaterScrap) {
            this.id = theaterScrap.getId();
            this.theaterName = theaterScrap.getTheater().getName();
        }
    }
}
