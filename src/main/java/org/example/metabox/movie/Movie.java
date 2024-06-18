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
    private String engTitle;
    // 개봉일
    private Date date;
    // 포스터 사진
    private String imgFilename;

    // 영화 소개
    private String description;
}
