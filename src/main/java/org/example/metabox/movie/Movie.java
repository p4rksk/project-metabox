package org.example.metabox.movie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.movie_scrap.MovieScrap;
import org.example.metabox.review.Review;
import org.example.metabox.theater_movie.TheaterMovie;
import org.example.metabox.trailer.Trailer;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MovieScrap> movieScrap;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TheaterMovie> theaterMovies;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Trailer> trailerList;

    private String title;
    // 개봉일
    private Date date;
    // 포스터 사진
    private String imgFilename;
    // 영화 소개
    private String description;
}
