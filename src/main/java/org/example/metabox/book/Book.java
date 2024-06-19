package org.example.metabox.book;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.seat.SeatBook;
import org.example.metabox.user.Guest;
import org.example.metabox.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book_tb")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Guest guest;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeatBook> seatBookList;

    private Integer totalPrice;

    // 예매일
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Book(int id, User user, Guest guest, List<SeatBook> seatBookList, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.guest = guest;
        this.seatBookList = seatBookList;
        this.createdAt = createdAt;
    }
}
