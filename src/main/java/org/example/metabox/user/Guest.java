package org.example.metabox.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.book.Book;
import org.example.metabox.movie_scrap.MovieScrap;
import org.example.metabox.review.Review;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name = "guest_tb")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Book book;

    private String birthYear;

    private String password;

    @Builder
    public Guest(Integer id, Book book, String birthYear, String password) {
        this.id = id;
        this.book = book;
        this.birthYear = birthYear;
        this.password = password;
    }
}