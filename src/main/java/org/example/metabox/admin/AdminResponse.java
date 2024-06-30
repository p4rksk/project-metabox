package org.example.metabox.admin;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

public class AdminResponse {

    @Data
    @Builder
    public static class TheaterSalesDTO {
        private int theaterId;
        private String theaterName;
        private String theaterSales;
        private String theaterAddress;
        private String theaterTel;
    }

    @Data
    @Builder
    public static class MovieSalesDTO {
        private int movieId;
        private String movieTitle;
        private Date startDate;
        private Date endDate;
        private String movieSales;
        private String viewerCount;
    }

    @Data
    @Builder
    public static class RootAdminResponseDTO {
        private String totalSales;
        private List<TheaterSalesDTO> theaterSales;
        private List<MovieSalesDTO> movieSales;
    }
}
