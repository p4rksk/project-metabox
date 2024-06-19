package org.example.metabox.seat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.book.Book;
import org.example.metabox.screening_info.ScreeningInfo;

@Entity
@Data
@NoArgsConstructor
@Table(name = "seat_book_tb")
public class SeatBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ScreeningInfo screeningInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Builder
    public SeatBook(int id, ScreeningInfo screeningInfo, Seat seat, Book book) {
        this.id = id;
        this.screeningInfo = screeningInfo;
        this.seat = seat;
        this.book = book;
    }
}

