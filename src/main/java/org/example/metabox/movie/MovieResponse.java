package org.example.metabox.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.metabox._core.util.FormatUtil;
import org.example.metabox.movie_pic.MoviePic;
import org.example.metabox.review.Review;
import org.example.metabox.trailer.Trailer;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class MovieResponse {

    // Lombok 라이브러리를 사용하여 getter, setter, toString 메서드 자동 생성합니다.
    @Data
    @Builder
    @AllArgsConstructor
    public static class AdminMovieChartDTO {
        private int id;               // 영화의 고유 식별자
        private String title;         // 영화 제목
        private String imgFilename;   // 영화 이미지 파일명
        private String ageInfo;  // 영화 정보의 첫 번째 부분
        private Date startDate;       // 상영 시작일
        private String releaseStatus; // 상영 상태(상영중, D-?)
        private Double bookingRate;   // 예매율
        private int rank;             // 순위
        private String ageInfoColor;  // 연령 정보에 따른 색상 뱃지 값

        // Movie 객체를 받아서 UserMovieChartDTO 객체의 필드를 초기화합니다.
        public AdminMovieChartDTO(int id, String title, String imgFilename, String ageInfo, Date startDate, String releaseStatus, Double bookingRate, int rank) {
            this.id = id;
            this.title = title;
            this.imgFilename = imgFilename;
            this.ageInfo = ageInfo;
            this.startDate = startDate;
            this.releaseStatus = releaseStatus;
            this.bookingRate = bookingRate;
            this.rank = rank;
            this.ageInfoColor = classColor(ageInfo); // 연령 정보에 따른 색상 뱃지 값 설정
        }

        // 연령 정보에 따른 색상 뱃지 값을 리턴하는 메서드
        public String classColor(String ageInfo) {
            if ("12".equals(ageInfo)) {
                return "badge badge-warning";
            } else if ("15".equals(ageInfo)) {
                return "badge badge-primary";
            } else if ("전".equals(ageInfo)) {
                return "badge badge-success";
            } else if ("19".equals(ageInfo)) {
                return "badge badge-danger";
            } else {
                return "";
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class UserMovieChartDTO {
        private int id;               // 영화의 고유 식별자
        private String title;         // 영화 제목
        private String imgFilename;   // 영화 이미지 파일명
        private String ageInfo;       // 영화 정보의 첫 번째 부분 (연령 정보)
        private Date startDate;       // 상영 시작일
        private String releaseStatus; // 상영 상태(상영중, D-?)
        private Double bookingRate;   // 예매율
        private int rank;             // 순위
        private String ageInfoColor;  // 연령 정보에 따른 색상 뱃지 값

        // 필요한 모든 필드를 초기화하는 생성자 추가
        public UserMovieChartDTO(int id, String title, String imgFilename, String ageInfo, Date startDate, String releaseStatus, Double bookingRate, int rank) {
            this.id = id;
            this.title = title;
            this.imgFilename = imgFilename;
            this.ageInfo = ageInfo;
            this.startDate = startDate;
            this.releaseStatus = releaseStatus;
            this.bookingRate = bookingRate;
            this.rank = rank;
            this.ageInfoColor = classColor(ageInfo); // 연령 정보에 따른 색상 뱃지 값 설정
        }

        // 연령 정보에 따른 색상 뱃지 값을 리턴하는 메서드
        public String classColor(String ageInfo) {
            if ("12".equals(ageInfo)) {
                return "badge badge-warning";
            } else if ("15".equals(ageInfo)) {
                return "badge badge-primary";
            } else if ("전".equals(ageInfo)) {
                return "badge badge-success";
            } else if ("19".equals(ageInfo)) {
                return "badge badge-danger";
            } else {
                return "";
            }
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
            private int rating;
            private String reviewer;
            private String userProfile;
            // TODO: 더미 데이터에 리뷰 작성일 null이라서 머스테치에 적용하면 에러남 더미 데이터 수정 후 적용
            private String reviewDate;

            public static ReviewDTO fromEntity(Review review) {
                ReviewDTO dto = new ReviewDTO();
                dto.id = review.getId();
                dto.comment = review.getComment();
                dto.rating = review.getRating();
                dto.reviewer = review.getUser().getNickname();
                dto.userProfile = review.getUser().getImgFilename();
                dto.reviewDate = FormatUtil.timeFormat(review.getCreatedAt());
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
            private int rating;
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
