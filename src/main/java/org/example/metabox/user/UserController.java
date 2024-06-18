package org.example.metabox.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final HttpSession session;

    @GetMapping("/")
    public String mainForm(HttpServletRequest request) {
        return "index";
    }

    @GetMapping("/login-form")
    public String loginForm(HttpServletRequest request) {
        return "user/login-form";
    }

    @GetMapping("/non-member")
    public String nonMember(HttpServletRequest request) {
        return "user/non-member";
    }

    @GetMapping("/mypage/home")
    public String mypageHome(HttpServletRequest request) {
        return "user/mypage-home";
    }

    @GetMapping("/mypage/detail-book")
    public String myBookDetail(HttpServletRequest request) {
        return "user/mypage-detail-book";
    }

    @GetMapping("/mypage/detail-saw")
    public String mySawDetail(HttpServletRequest request) {
        return "user/mypage-detail-saw";
    }

}
