package org.example.metabox.review;

import lombok.Data;
import org.example.metabox.movie.Movie;
import org.example.metabox.user.User;

import java.sql.Date;

public class ReviewResponse {

    @Data
    public static class ReviewDTO {
        private String comment;
        private String nickname;
        private String imgFilename;

        public ReviewDTO(Review review, User user){
            this.comment = review.getComment();
            this.nickname = user.getNickname();
            this.imgFilename = user.getImgFilename();
        }
    }

    @Data
    public static class ReviewListDTO {
        private Integer id;
        private String comment;
        private String title;
        private String imgFilename;
        private Date startDate;
        private Date endDate;
        private Integer rating;

        public ReviewListDTO(Review review){
            this.id = review.getId();
            this.comment = review.getComment();
            this.title = review.getMovie().getTitle();
            this.imgFilename = review.getMovie().getImgFilename();
            this.startDate = review.getMovie().getStartDate();
            this.endDate = review.getMovie().getEndDate();
            this.rating = review.getRating();
        }
    }
}
