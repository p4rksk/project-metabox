package org.example.metabox.admin;

import lombok.Builder;
import lombok.Data;

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
    public static class RootAdminResponseDTO {
        private String totalSales;
        private List<TheaterSalesDTO> theaterSales;
    }
}
