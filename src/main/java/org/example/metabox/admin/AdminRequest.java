package org.example.metabox.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class AdminRequest {

    //로그인
    @Data
    public static class LoginDTO {
        @NotEmpty(message = "ID가 공백일 수 없습니다.")
        @Size(min = 2, max = 20, message = "ID는 최소 2자 이상 최대 20자 이하여야 합니다.")
        private String loginId;

        @NotEmpty(message = "비밀번호가 공백일 수 없습니다.")
        @Size(min = 4, max = 20, message = "비밀번호는 최소 4자 이상 최대 20자 이하여야 합니다.")
        private String password;
    }
}
