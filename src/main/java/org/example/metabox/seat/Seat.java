package org.example.metabox.seat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.book.Book;
import org.example.metabox.screening.Screening;
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("false")
    private Boolean booked;

    // 장애인석, 라이트석은 -1000원 할인
    // 장애인석, 라이트석, 일반석, 모션베드
    @Enumerated(EnumType.STRING)
    private SeatType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Builder
    public Seat(int id, Screening screening, String code, Boolean booked, SeatType type, Book book) {
        this.id = id;
        this.screening = screening;
        this.code = code;
        this.booked = booked;
        this.type = type;
        this.book = book;
    }

    private enum SeatType {
        장애인석, 일반석, 라이트석, 모션베드
    }
}
