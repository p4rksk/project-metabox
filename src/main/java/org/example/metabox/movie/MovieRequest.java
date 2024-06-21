package org.example.metabox.movie;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class MovieRequest {

    // 영화 등록
    @Data
    public static class movieSavaFormDTO {
        private String title;
        private String engTitle;
        private String director;
        private String actor;
        private String genre;
        private String info;
        private Date date;
        private MultipartFile imgFilename;
        private String description;
        private MultipartFile[] stills;
        private MultipartFile[] trailers;
    }
}
