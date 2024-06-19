package org.example.metabox.theater_movie;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.movie.Movie;
import org.example.metabox.screening_info.ScreeningInfo;
import org.example.metabox.theater.Theater;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "theater_movie_tb")
public class TheaterMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "theaterMovie", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ScreeningInfo> screeningInfoList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Theater theater;

    // 상영(현재 상영중) 유무, 현재 상영작, 상영 예정작, 종료작
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'상영예정작'")
    private TheaterMovieStatus status;

    private enum TheaterMovieStatus {
        현재상영중, 상영예정작, 종료작;
    }

    @Builder
    public TheaterMovie(int id, List<ScreeningInfo> screeningInfoList, Movie movie, Theater theater, TheaterMovieStatus status) {
        this.id = id;
        this.screeningInfoList = screeningInfoList;
        this.movie = movie;
        this.theater = theater;
        this.status = status;
    }
}
