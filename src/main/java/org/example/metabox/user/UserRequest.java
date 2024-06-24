package org.example.metabox.user;

import lombok.Data;
import org.example.metabox.theater.Theater;
import org.example.metabox.theater_scrap.TheaterScrap;

public class UserRequest {

    // 유저 스크랩
    @Data
    public static class TheaterScrapDTO {
        private Integer theaterNameId;
        private Integer userId;

        public TheaterScrap toEntity (Theater theaterNameId, User userId) {
            return TheaterScrap.builder()
                    .theater(theaterNameId)
                    .user(userId)
                    .build();
        }
    }


    //비회원 회원가입
    @Data
    public static class JoinDTO {
        private String birth;
        private String password;
        private String phone;

        public JoinDTO(String birth, String password, String phone) {
            this.birth = birth;
            this.password = password;
            this.phone = phone;
        }
    }
}
