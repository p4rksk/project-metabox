package org.example.metabox.screening_info;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.movie.Movie;
import org.example.metabox.seat.SeatBook;
import org.example.metabox.screening.Screening;

import java.time.LocalDate;
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
    private Movie movie;

    @OneToMany(mappedBy = "screeningInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SeatBook> seatBookList;


    // 상영 시간
    private String showTime;
    private String startTime;
    private String endTime;
    private LocalDate date;

    @Builder

    public ScreeningInfo(int id, Screening screening, Movie movie, List<SeatBook> seatBookList, String showTime, String startTime, String endTime, LocalDate date) {
        this.id = id;
        this.screening = screening;
        this.movie = movie;
        this.seatBookList = seatBookList;
        this.showTime = showTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
}
