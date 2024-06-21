package org.example.metabox.movie;

import lombok.Data;
import org.example.metabox.movie_pic.MoviePic;
import org.example.metabox.trailer.Trailer;

import java.sql.Date;
import java.util.List;

public class MovieResponse {

    // Lombok 라이브러리를 사용하여 getter, setter, toString 메서드 자동 생성합니다.
    @Data
    public static class MovieChartDTO {
        private int id;               // 영화의 고유 식별자
        private String imgFilename;   // 영화 이미지 파일명
        private String title;         // 영화 제목
        private String infoAgeLimit;  // 영화 정보의 첫 번째 부분
        private Date date;            // 영화 개봉일

        // Movie 객체를 받아서 MovieChartDTO 객체의 필드를 초기화합니다.
        public MovieChartDTO(Movie movie) {
            this.id = movie.getId();
            this.imgFilename = movie.getImgFilename();
            this.title = movie.getTitle();
            this.infoAgeLimit = movie.getInfo().split(",")[0]; // Movie 객체에서 info 값을 쉼표로 분리하여 첫 번째 부분의 값을 할당
            this.date = movie.getStartDate();
        }
    }

    @Data
    public static class MovieDetailDTO {
        private int id;                     // 영화의 고유 식별자
        private String title;               // 한글 영화 제목
        private String engTitle;            // 영어 영화 제목
        private String director;            // 감독
        private String actor;               // 배우
        private String genre;               // 장르
        private String infoAgeLimit;        // 연령 제한
        private String infoRunningTime;     // 상영 시간
        private String infoRegion;          // 지역
        private Date startDate;             // 개봉일
        private String imgFilename;         // 포스터 사진
        private String description;         // 영화 소개
        private String releaseStatus;       // 개봉 상태
        private List<StillDTO> stills;      // 스틸컷
        private List<TrailerDTO> trailers;  // 트레일러

        // Movie 객체를 MovieDetailDTO 객체로 변환하는 메서드
        public static MovieDetailDTO formEntity(Movie movie, String releaseStatus){
            MovieDetailDTO movieDetailDto = new MovieDetailDTO();
            movieDetailDto.id = movie.getId();
            movieDetailDto.title = movie.getTitle();
            movieDetailDto.engTitle = movie.getEngTitle();
            movieDetailDto.director = movie.getDirector();
            movieDetailDto.actor = movie.getActor();
            movieDetailDto.genre = movie.getGenre();
            movieDetailDto.infoAgeLimit = movie.getInfo().split(",")[0];    // Movie 객체에서 info 값을 쉼표로 분리하여 첫 번째 부분의 값을 할당
            movieDetailDto.infoRunningTime = movie.getInfo().split(",")[1]; // Movie 객체에서 info 값을 쉼표로 분리하여 두 번째 부분의 값을 할당
            movieDetailDto.infoRegion = movie.getInfo().split(",")[2];      // Movie 객체에서 info 값을 쉼표로 분리하여 세 번째 부분의 값을 할당
            movieDetailDto.startDate = movie.getStartDate();
            movieDetailDto.imgFilename = movie.getImgFilename();
            movieDetailDto.description = movie.getDescription();
            movieDetailDto.releaseStatus = releaseStatus;                         // 개봉 상태
            movieDetailDto.stills = movie.getMoviePicList().stream().map(StillDTO::new).toList();
            movieDetailDto.trailers = movie.getTrailerList().stream().map(TrailerDTO::new).toList();
            return movieDetailDto;
        }

        @Data
        public static class StillDTO {
            private String fileName; // 스틸컷 파일 이름

            public StillDTO(MoviePic moviePic) {
                this.fileName = moviePic.getImgFilename();
            }
        }

        @Data
        public static class TrailerDTO {
            private String fileName; // 트레일러 파일 이름

            public TrailerDTO(Trailer trailer) {
                this.fileName = trailer.getStreamingFilename();
            }

        }

    }

}
