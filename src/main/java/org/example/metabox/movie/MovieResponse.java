package org.example.metabox.movie;

import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MovieResponse {

    // Lombok 라이브러리를 사용하여 getter, setter, toString 메서드 자동 생성합니다.
    @Data
    public static class MovieChartDTO {
        private int id;               // 영화의 고유 식별자
        private String imgFilename;   // 영화 이미지 파일명
        private String title;         // 영화 제목
        private String infoFirstPart; // 영화 정보의 첫 번째 부분
        private Date date;            // 영화 개봉일

        // Movie 객체를 받아서 MovieChartDTO 객체의 필드를 초기화합니다.
        public MovieChartDTO(Movie movie) {
            this.id = movie.getId();
            this.imgFilename = movie.getImgFilename();
            this.title = movie.getTitle();
            this.infoFirstPart = movie.getInfo().split(",")[0]; // Movie 객체에서 정보 값을 쉼표로 분리하여 첫 번째 부분만 초기화
            this.date = movie.getDate();
        }
    }
}
