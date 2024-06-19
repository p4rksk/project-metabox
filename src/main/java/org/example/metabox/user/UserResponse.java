package org.example.metabox.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.example.metabox._core.util.ScopeDeserializer;

import java.sql.Timestamp;
import java.util.List;

public class UserResponse {

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


}
