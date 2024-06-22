package org.example.metabox.movie;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.movie_pic.MoviePic;
import org.example.metabox.screening_info.ScreeningInfo;

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
    private Date startDate;
    // 개봉 종료 일
    private Date endDate;
    // 포스터 사진
    private String imgFilename;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MoviePic> moviePicList;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ScreeningInfo> screeningInfoList;

    // 영화 소개
    // VARCHAR 보다 긴 TEXT 사용
    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder
    public Movie(int id, String title, String engTitle, String director, String actor, String genre, String info, Date startDate, Date endDate, String imgFilename, List<MoviePic> moviePicList, List<ScreeningInfo> screeningInfoList, String description) {
        this.id = id;
        this.title = title;
        this.engTitle = engTitle;
        this.director = director;
        this.actor = actor;
        this.genre = genre;
        this.info = info;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imgFilename = imgFilename;
        this.moviePicList = moviePicList;
        this.screeningInfoList = screeningInfoList;
        this.description = description;
    }
}