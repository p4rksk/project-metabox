package org.example.metabox.screening_info;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.seat.SeatBook;
import org.example.metabox.screening.Screening;
import org.example.metabox.theater_movie.TheaterMovie;

import java.time.LocalDate;
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

    @OneToMany(mappedBy = "screeningInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SeatBook> seatBookList;


    // 상영 시간
    private String showTime;
    private String startTime;
    private String endTime;
    private LocalDate date;

    @Builder

    public ScreeningInfo(int id, Screening screening, TheaterMovie theaterMovie, List<SeatBook> seatBookList, String showTime, String startTime, String endTime, LocalDate date) {
        this.id = id;
        this.screening = screening;
        this.theaterMovie = theaterMovie;
        this.seatBookList = seatBookList;
        this.showTime = showTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
}
