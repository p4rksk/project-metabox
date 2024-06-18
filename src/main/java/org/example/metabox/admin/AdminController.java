package org.example.metabox.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminController {

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
