package org.example.metabox.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.book.Book;
import org.example.metabox.movie_scrap.MovieScrap;
import org.example.metabox.review.Review;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Book> bookList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MovieScrap> movieScrap;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TheaterScrap> theaterScrapList;

    private String nickname;
    private String imgFilename;
    private String name;
    private String birthYear;
    @ColumnDefault("0")
    private Integer point;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String provider;    // kakao, naver
    private String password;

    @Builder

    public User(Integer id, List<Book> bookList, List<MovieScrap> movieScrap, List<Review> reviews, List<TheaterScrap> theaterScrapList, String nickname, String imgFilename, String name, String birthYear, int point, LocalDateTime createdAt, String provider, String password) {
        this.id = id;
        this.bookList = bookList;
        this.movieScrap = movieScrap;
        this.reviews = reviews;
        this.theaterScrapList = theaterScrapList;
        this.nickname = nickname;
        this.imgFilename = imgFilename;
        this.name = name;
        this.birthYear = birthYear;
        this.point = point;
        this.createdAt = createdAt;
        this.provider = provider;
        this.password = password;
    }
}