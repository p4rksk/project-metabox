package org.example.metabox.movie_pic;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.movie.Movie;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movie_pic_tb")
public class MoviePic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String imgFilename;

    @Builder
    public MoviePic(int id, Movie movie, String imgFilename) {
        this.id = id;
        this.movie = movie;
        this.imgFilename = imgFilename;
    }
}
