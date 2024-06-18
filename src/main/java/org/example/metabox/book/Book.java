package org.example.metabox.book;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.payment.Payment;
import org.example.metabox.screening_info.ScreeningInfo;
import org.example.metabox.seat.Seat;
import org.example.metabox.user.Guest;
import org.example.metabox.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seatList;

    // 예매일
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Book(int id, User user, Guest guest, Payment payment, List<Seat> seatList, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.guest = guest;
        this.payment = payment;
        this.seatList = seatList;
        this.createdAt = createdAt;
    }
}
