package org.example.metabox.theater;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
        @NotEmpty(message = "ID가 공백일 수 없습니다.")
        @Size(min = 2, max = 20, message = "ID는 최소 2자 이상 최대 20자 이하여야 합니다.")
        private String loginId;

        @NotEmpty(message = "비밀번호가 공백일 수 없습니다.")
        @Size(min = 4, max = 20, message = "비밀번호는 최소 4자 이상 최대 20자 이하여야 합니다.")
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
