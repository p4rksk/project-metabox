package org.example.metabox.payment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.book.Book;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "payment_tb")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private int price;
    private String theaterName;
    private String movieName;
    
    // 결제일
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Payment(int id, Book book, int price, String theaterName, String movieName, LocalDateTime createdAt) {
        this.id = id;
        this.book = book;
        this.price = price;
        this.theaterName = theaterName;
        this.movieName = movieName;
        this.createdAt = createdAt;
    }
}
