package org.example.metabox.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import org.example.metabox._core.util.ScopeDeserializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserResponse {

    //마이페이지 DetailBook
    @Data
    public static class DetailBookDTO {

        private List<MovieChartDTO> movieCharts = new ArrayList<>();

        @Builder
        public DetailBookDTO(List<MovieChartDTO> movieCharts) {
            this.movieCharts = movieCharts;
        }

        // today best 무비차트 뿌리는 DTO
        @Data
        public static class MovieChartDTO {
            private Integer id;     //movie Id
            private String imgFilename;
            private String title;
            private Date startDate;

            private Integer allCount;
            private Integer movieCount;

            private Double ticketSales;     // 예매율 - 계산해서 가져오기

            @Builder
            public MovieChartDTO(Integer id, String imgFilename, String title, Date startDate, Integer allCount, Integer movieCount, Double ticketSales) {
                this.id = id;
                this.imgFilename = imgFilename;
                this.title = title;
                this.startDate = startDate;
                this.allCount = allCount;
                this.movieCount = movieCount;
                this.ticketSales = ticketSales;
            }

        }

    }



    @Data
    public static class TokenDTO {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("expires_in")
        private Integer expiresIn;
        @JsonDeserialize(using = ScopeDeserializer.class)
        private List<String> scope;
        @JsonProperty("refresh_token_expires_in")
        private Integer refreshTokenExpiresIn;
    }

    @Data
    public static class KakaoUserDTO {
        private Long id;
        @JsonProperty("connected_at")
        private Timestamp connectedAt;
        private Properties properties;

        @Data
        class Properties {
            private String nickname;
            @JsonProperty("profile_image")
            private String profileImage;

        }
    }

    @Data
    public static class NaverUserDTO {
        private Response response;

        @Data
        class Response {
            private String id;
            private String nickname;
            @JsonProperty("profile_image")
            private String profileImage;
            private String age;     // 연령대라서 String으로 받아봄
            private String gender;
            private String email;
            private String mobile;
            @JsonProperty("mobile_e164")
            private String mobileE164;  // 국제 전화번호 형식. 필요없어보이지만 폰번호 받으면 같이 날아오는 듯함
            private String name;    //사람 이름
            private String birthday;    // 일단 문자열로 들어와서..
            private String birthyear;
        }
    }


}
