package org.example.metabox.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.metabox.theater.Theater;
import org.example.metabox.theater_scrap.TheaterScrap;

public class UserRequest {

    // 유저 스크랩
    @Data
    public static class TheaterScrapDTO {
        private Integer theaterNameId;
        private Integer userId;

        public TheaterScrap toEntity(Theater theaterNameId, User userId) {
            return TheaterScrap.builder()
                    .theater(theaterNameId)
                    .user(userId)
                    .build();
        }
    }

    // 비회원 예매 조회
    @Data
    public static class GuestBookCheckDTO {
        @NotEmpty(message = "이름을 입력해주세요.")
        private String name;

        @NotEmpty(message = "전화번호를 입력해주세요.")
        @Size(min = 13, max = 13, message = "전화번호는 13글자여야 합니다.")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010으로 시작해야 합니다.")
        private String phone;

        @NotEmpty(message = "비밀번호를 입력해주세요.")
        @Size(min = 4, max = 4, message = "비밀번호는 4글자여야 합니다.")
        private String password;

        @NotEmpty(message = "예매번호를 입력해주세요.")
        private String bookNumb;
    }


    //비회원 회원가입
    @Data
    public static class JoinDTO {
        @NotEmpty(message = "생년월일을 입력해주세요.")
        @Size(min = 8, max = 8, message = "생년월일은 8자리여야 합니다.")
        @Pattern(regexp = "^(19[2-9][0-9]|20[0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$", message = "생년월일을 확인해주세요.")
        private String birth;

        @NotEmpty(message = "비밀번호를 입력해주세요.")
        @Size(min = 4, max = 4, message = "비밀번호는 4글자여야 합니다.")
        private String password;

        @NotEmpty(message = "전화번호를 입력해주세요.")
        @Size(min = 13, max = 13, message = "전화번호는 13글자여야 합니다.")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010으로 시작해야 합니다.")
        private String phone;

        @NotEmpty(message = "이름을 입력해주세요.")
        private String name;

        public JoinDTO(String birth, String password, String phone, String name) {
            this.birth = birth;
            this.password = password;
            this.phone = phone;
            this.name = name;
        }
    }
}
