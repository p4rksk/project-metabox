package org.example.metabox.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.book.Book;
import org.example.metabox.movie_scrap.MovieScrap;
import org.example.metabox.review.Review;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class SessionUser {

    private Integer id;

    private String nickname;
    private String imgFilename;
    private String name;
    private String birthYear;
    private int point;
    private String provider;    //kakao, naver OAuth
    private String accessToken;

    public SessionUser(User user, String accessToken) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.imgFilename = user.getImgFilename();
        this.name = user.getName();
        this.birthYear = user.getBirthYear();
        this.point = user.getPoint();
        this.provider = user.getProvider();
        this.accessToken = accessToken;
    }
}