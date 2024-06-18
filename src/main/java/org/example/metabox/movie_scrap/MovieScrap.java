package org.example.metabox.movie_scrap;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.movie.Movie;
import org.example.metabox.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movie_scrap_tb")
public class MovieScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public MovieScrap(int id, User user, Movie movie, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.movie = movie;
        this.createdAt = createdAt;
    }
}
