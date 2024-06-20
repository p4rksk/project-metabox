package org.example.metabox.movie;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.metabox.movie_pic.MoviePic;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movie_tb")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 한글 영화 제목
    private String title;
    // 영어 제목
    private String engTitle;
    // 감독
    private String director;
    // 배우
    private String actor;
    // 장르
    private String genre;
    // 정보(ex : '전체관람가, 94분, 미국')
    private String info;
    // 개봉일
    private Date date;
    // 포스터 사진
    private String imgFilename;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<MoviePic> moviePicList;

    // 영화 소개
    // VARCHAR 보다 긴 TEXT 사용
    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder
    public Movie(int id, String title, String engTitle, String director, String actor, String genre, String info, Date date, String imgFilename, List<MoviePic> moviePicList, String description) {
        this.id = id;
        this.title = title;
        this.engTitle = engTitle;
        this.director = director;
        this.actor = actor;
        this.genre = genre;
        this.info = info;
        this.date = date;
        this.imgFilename = imgFilename;
        this.moviePicList = moviePicList;
        this.description = description;
    }
}