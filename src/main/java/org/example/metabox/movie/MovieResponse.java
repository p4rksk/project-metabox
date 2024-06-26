package org.example.metabox.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.metabox.movie_pic.MoviePic;
import org.example.metabox.review.Review;
import org.example.metabox.trailer.Trailer;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class MovieResponse {

    // Lombok 라이브러리를 사용하여 getter, setter, toString 메서드 자동 생성합니다.
    @Data
    @AllArgsConstructor
    public static class AdminMovieChartDTO {
        private int id;               // 영화의 고유 식별자
        private String title;         // 영화 제목
        private String imgFilename;   // 영화 이미지 파일명
        private String infoAgeLimit;  // 영화 정보의 첫 번째 부분
        private Date startDate;       // 상영 시작일
        private String releaseStatus; // 상영 상태(상영중, D-?)
        private Double bookingRate;   // 예매율
        private int rank;             // 순위

        // Movie 객체를 받아서 UserMovieChartDTO 객체의 필드를 초기화합니다.
        public AdminMovieChartDTO(Movie movie, int rank, Double bookingRate, String releaseStatus) {
            this.id = movie.getId();
            this.imgFilename = movie.getImgFilename();
            this.title = movie.getTitle();
            this.infoAgeLimit = movie.getInfo().split(",")[0]; // Movie 객체에서 info 값을 쉼표로 분리하여 첫 번째 부분의 값을 할당
            this.startDate = movie.getStartDate();
            this.releaseStatus = releaseStatus; // 상영 상태(상영중, D-?)
            this.bookingRate = bookingRate; // 예매율
            this.rank = rank; // 순위
        }
    }

    @Data
    @AllArgsConstructor
    public static class UserMovieChartDTO {
        private int id;               // 영화의 고유 식별자
        private String title;         // 영화 제목
        private String imgFilename;   // 영화 이미지 파일명
        private String infoAgeLimit;  // 영화 정보의 첫 번째 부분
        private Date startDate;       // 상영 시작일
        private String releaseStatus; // 상영 상태(상영중, D-?)
        private Double bookingRate;   // 예매율
        private int rank;             // 순위

        // Movie 객체를 받아서 UserMovieChartDTO 객체의 필드를 초기화합니다.
        public UserMovieChartDTO(Movie movie, int rank, Double bookingRate, String releaseStatus) {
            this.id = movie.getId();
            this.imgFilename = movie.getImgFilename();
            this.title = movie.getTitle();
            this.infoAgeLimit = movie.getInfo().split(",")[0]; // Movie 객체에서 info 값을 쉼표로 분리하여 첫 번째 부분의 값을 할당
            this.startDate = movie.getStartDate();
            this.releaseStatus = releaseStatus; // 상영 상태(상영중, D-?)
            this.bookingRate = bookingRate; // 예매율
            this.rank = rank; // 순위
        }
    }

    @Data
    @Builder
    public static class MovieDetailDTO {
        private int id;
        private String imgFilename;
        private String releaseStatus;
        private String title;
        private String engTitle;
        private Double bookingRate;
        private String director;
        private String actor;
        private String genre;
        private String info;
        private Date startDate;
        private Date endDate;
        private String description;
        private List<MoviePicDTO> stills;
        private List<TrailerDTO> trailers;
        private List<ReviewDTO> reviews;
        private Integer reviewCount;
        private Integer stillsCount;
        private Integer trailersCount;

        @Data
        public static class MoviePicDTO {
            private int id;
            private String fileName;

            // TODO: 생성자 코드 변경하기
            public static MoviePicDTO fromEntity(MoviePic moviePic) {
                MoviePicDTO dto = new MoviePicDTO();
                dto.id = moviePic.getId();
                dto.fileName = moviePic.getImgFilename();
                return dto;
            }
        }

        @Data
        public static class TrailerDTO {
            private int id;
            private String fileName;
            private String masterM3u8name;

            public static TrailerDTO fromEntity(Trailer trailer) {
                TrailerDTO dto = new TrailerDTO();
                dto.id = trailer.getId();
                dto.fileName = trailer.getStreamingFilename();
                dto.masterM3u8name= trailer.getMasterM3U8Filename();
                return dto;
            }
        }

        @Data
        public static class ReviewDTO {
            private int id;
            private String comment;
            private double rating;
            private String reviewer;
            private String userProfile;
            // TODO: 더미 데이터에 리뷰 작성일 null이라서 머스테치에 적용하면 에러남 더미 데이터 수정 후 적용
            private LocalDateTime reviewDate;

            public static ReviewDTO fromEntity(Review review) {
                ReviewDTO dto = new ReviewDTO();
                dto.id = review.getId();
                dto.comment = review.getComment();
                dto.rating = review.getRating();
                dto.reviewer = review.getUser().getNickname();
                dto.userProfile = review.getUser().getImgFilename();
                dto.reviewDate = review.getCreatedAt();
                return dto;
            }
        }
    }

    @Data
    @Builder
    public static class AdminMovieDetailDTO {
        private int id;
        private String imgFilename;
        private String releaseStatus;
        private String title;
        private String engTitle;
        private Double bookingRate;
        private String director;
        private String actor;
        private String genre;
        private String info;
        private Date startDate;
        private String description;
        private List<MoviePicDTO> stills;
        private List<TrailerDTO> trailers;
        private List<ReviewDTO> reviews;
        private Integer reviewCount;
        private Integer stillsCount;

        @Data
        public static class MoviePicDTO {
            private int id;
            private String fileName;

            public static MoviePicDTO fromEntity(MoviePic moviePic) {
                MoviePicDTO dto = new MoviePicDTO();
                dto.id = moviePic.getId();
                dto.fileName = moviePic.getImgFilename();
                return dto;
            }
        }

        @Data
        public static class TrailerDTO {
            private int id;
            private String fileName;

            public static TrailerDTO fromEntity(Trailer trailer) {
                TrailerDTO dto = new TrailerDTO();
                dto.id = trailer.getId();
                dto.fileName = trailer.getStreamingFilename();
                return dto;
            }
        }

        @Data
        public static class ReviewDTO {
            private int id;
            private String comment;
            private double rating;
            private String reviewer;
            private String userProfile;

            public static ReviewDTO fromEntity(Review review) {
                ReviewDTO dto = new ReviewDTO();
                dto.id = review.getId();
                dto.comment = review.getComment();
                dto.rating = review.getRating();
                dto.reviewer = review.getUser().getNickname();
                dto.userProfile = review.getUser().getImgFilename();
                return dto;
            }
        }
    }

}
