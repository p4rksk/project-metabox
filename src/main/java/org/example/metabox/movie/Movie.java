package org.example.metabox.movie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movie_tb")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    // 영화 소개
    private String description;

    public Movie(int id, String title, String engTitle, String director, String actor, String genre, String info, Date date, String imgFilename, String description) {
        this.id = id;
        this.title = title;
        this.engTitle = engTitle;
        this.director = director;
        this.actor = actor;
        this.genre = genre;
        this.info = info;
        this.date = date;
        this.imgFilename = imgFilename;
        this.description = description;
    }
