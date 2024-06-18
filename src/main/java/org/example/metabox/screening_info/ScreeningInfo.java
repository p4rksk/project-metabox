package org.example.metabox.screening_info;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.book.Book;
import org.example.metabox.screening.Screening;
import org.example.metabox.theater_movie.TheaterMovie;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "screening_info_tb")
public class ScreeningInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Screening screening;

    @ManyToOne(fetch = FetchType.LAZY)
    private TheaterMovie theaterMovie;


    // 상영 시간
    private String showtime;
    private String startTime;
    private String endTime;
    private LocalDateTime date;

    @Builder
    public ScreeningInfo(int id, Screening screening, TheaterMovie theaterMovie, String showtime, String startTime, String endTime, LocalDateTime date) {
        this.id = id;
        this.screening = screening;
        this.theaterMovie = theaterMovie;
        this.showtime = showtime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
}
