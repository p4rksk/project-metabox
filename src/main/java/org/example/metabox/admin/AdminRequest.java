package org.example.metabox.admin;

import lombok.Data;

public class AdminRequest {

    //로그인
    @Data
    public static class LoginDTO {
        private String loginId;
        private String password;
    }
}
