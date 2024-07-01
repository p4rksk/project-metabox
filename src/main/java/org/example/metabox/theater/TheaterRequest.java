package org.example.metabox.theater;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

public class TheaterRequest {

    @AllArgsConstructor
    @Data
    public class ScheduleDTO {
        private Integer theaterId;
    }

    @Data
    public class LoginDTO {
        private String loginId;
        private String password;
    }

    @Data
    public class ScreeningInfoDTO {
        private int screeningId;
        private int movieId;
        private String startTime;
        private String endTime;
        private String showtime;
        private LocalDate screeningDate;
    }
}
