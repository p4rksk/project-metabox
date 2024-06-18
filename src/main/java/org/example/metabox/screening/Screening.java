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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScreeningInfo> screeningInfo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    private String name;
    // 좌석수(count 안할려고 미리 하는듯?)
    private Integer seatCount;

    private ScreeningRank screeningRank; //상영관 등급


    public enum ScreeningRank {
        일반관, 특별관
    }
}
