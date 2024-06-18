package org.example.metabox.seat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.screening.Screening;

@Entity
@Data
@NoArgsConstructor
@Table(name = "seat_tb")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Screening screening;

    // 좌석번호 ex : A9
    private String code;
    private Boolean booked;
    private int price;
    // 장애인석, 라이트석, 일반석

    private SeatType type;

    @Builder
    public Seat(int id, Screening screening, String code, Boolean booked, int price, SeatType type){
        this.id = id;
        this.screening = screening;
        this.code = code;
        this.booked = booked;
        this.price = price;
        this.type = type;
    }






    private enum SeatType {
        장애인석, 일반석
    }
}
