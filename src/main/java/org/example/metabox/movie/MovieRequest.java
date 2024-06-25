package org.example.metabox.movie;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public class MovieRequest {

    // 영화 등록
    @Data
    public static class MovieSavaFormDTO {
        private String title;
        private String engTitle;
        private String director;
        private String actor;
        private String genre;
        private String info;
        private Date startDate;
        private Date endDate;
        private MultipartFile imgFilename;
        private String description;
        private MultipartFile[] stills;
        private MultipartFile[] trailers;
    }

    // 영화 수정
    @Data
    public static class MovieInfoEditDTO {
        private int id;
        private String director;
        private String actor;
        private String genre;
        private String info;
        private Date startDate;
        private Date endDate;
        private String description;
        private MultipartFile imgFilename;
        private String posterName;
    }

}
