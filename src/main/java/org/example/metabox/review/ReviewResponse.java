package org.example.metabox.review;

import lombok.Data;
import org.example.metabox.user.User;

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
}
