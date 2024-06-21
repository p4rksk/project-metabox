package org.example.metabox.screening;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.screening_info.ScreeningInfo;
import org.example.metabox.seat.Seat;
import org.example.metabox.theater.Theater;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "screening_tb")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Theater theater;

    @OneToMany(mappedBy = "screening", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ScreeningInfo> screeningInfo;

    @OneToMany(mappedBy = "screening", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Seat> seats;

    private String name;
    // 좌석수(count 안할려고 미리 하는듯?)
    private Integer seatCount;

    // 일반관 : GENERAL = 15000원, 특별관 : SPECIAL 50000원
    private Integer seatPrice;

    @Enumerated(EnumType.STRING)
    private ScreeningRank screeningRank; //상영관 등급


    public Screening(Integer id, Theater theater, List<ScreeningInfo> screeningInfo, List<Seat> seats, String name, Integer seatCount, ScreeningRank screeningRank) {
        this.id = id;
        this.theater = theater;
        this.screeningInfo = screeningInfo;
        this.seats = seats;
        this.name = name;
        this.seatCount = seatCount;
        this.screeningRank = screeningRank;
    }

    public enum ScreeningRank {
        GENERAL, SPECIAL
    }


}
