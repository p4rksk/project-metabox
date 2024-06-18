package org.example.metabox.movie_pic;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movie_pic_tb")
public class MoviePic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imgFilename;

    @Builder
    public MoviePic(int id, String imgFilename) {
        this.id = id;
        this.imgFilename = imgFilename;
    }
}
