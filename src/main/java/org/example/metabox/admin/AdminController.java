package org.example.metabox.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final AdminService adminService;
    private final HttpSession session;

    @PostMapping("admin-login")
    public String adminLogin(AdminRequest.LoginDTO reqDTO) {
        Admin admin = adminService.login(reqDTO);
        session.setAttribute("admin", admin);
        return "admin/movie-list";
    }

    @GetMapping("admin-login-form")
    public String Login(){return "admin/login-form";}

    //    TODO : admin 만 접속할 수 있도록 주소에 interceptor 설정
    @GetMapping("/movie-detail")
    public String movieDetail() {
        return "admin/movie-detail";
    }

    @GetMapping("/movie-list")
    public String movieList() {
        return "admin/movie-list";
    }

    @GetMapping("/movie-edit-form")
    public String movieEditForm() {
        return "admin/movie-edit-form";
    }

    @GetMapping("/movie-save-form")
    public String movieSaveForm() {
        return "admin/movie-save-form";
    }

    @GetMapping("/theater-save-form")
    public String theaterSaveForm() {
        return "admin/theater-save-form";
    }
}
