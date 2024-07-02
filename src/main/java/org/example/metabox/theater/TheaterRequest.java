package org.example.metabox.theater;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
        @NotNull(message = "상영관은 필수 입력 항목입니다.")
        private Integer screeningId;

        @NotNull(message = "상영 영화는 필수 입력 항목입니다.")
        private Integer movieId;

        @NotEmpty(message = "시작 시간은 필수 입력 항목입니다.")
        private String startTime;       //상영시작시간
        @NotEmpty(message = "종료 시간은 필수 입력 항목입니다.")
        private String endTime;         //상영종료시간
        @NotEmpty(message = "상영 시간은 필수 입력 항목입니다.")
        private String showtime;        //상영시간
        @NotNull(message = "상영 일은 필수 입력 항목입니다.")
        private LocalDate screeningDate;
    }
}
