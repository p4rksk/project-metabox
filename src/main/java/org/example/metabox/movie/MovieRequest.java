package org.example.metabox.movie;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public class MovieRequest {

    // 영화 등록
    @Data
    public static class MovieSavaFormDTO {
        @NotEmpty(message = "제목은 필수 입력 항목입니다.")
        private String title;

        @NotEmpty(message = "영문 제목은 필수 입력 항목입니다.")
        private String engTitle;

        @NotEmpty(message = "감독은 필수 입력 항목입니다.")
        private String director;

        @NotEmpty(message = "배우는 필수 입력 항목입니다.")
        private String actor;

        @NotEmpty(message = "장르는 필수 입력 항목입니다.")
        private String genre;

        @NotEmpty(message = "정보는 필수 입력 항목입니다.")
        private String info;

//        @FutureOrPresent(message = "시작일은 현재 또는 미래여야 합니다.")
        private Date startDate;

//        @Future(message = "종료일은 미래여야 합니다.")
        private Date endDate;

//        @NotNull(message = "이미지 파일은 필수 입력 항목입니다.")
        private MultipartFile imgFilename;

        @NotEmpty(message = "설명은 필수 입력 항목입니다.")
        private String description;

//        @Size(min = 1, message = "최소 1개 이상의 스틸 이미지가 필요합니다.")
        private MultipartFile[] stills;

//        @Size(min = 1, message = "최소 1개 이상의 트레일러가 필요합니다.")
        private MultipartFile[] trailers;
    }

    // 영화 수정
    @Data
    public static class MovieInfoEditDTO {
        private int id;
        @NotEmpty(message = "감독은 필수 입력 항목입니다.")
        private String director;
        @NotEmpty(message = "배우는 필수 입력 항목입니다.")
        private String actor;
        @NotEmpty(message = "장르는 필수 입력 항목입니다.")
        private String genre;
        @NotEmpty(message = "정보는 필수 입력 항목입니다.")
        private String info;
        private Date startDate;
        private Date endDate;
        private String description;
        private MultipartFile imgFilename;
        private String posterName;
    }

}
