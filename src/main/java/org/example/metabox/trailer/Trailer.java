package org.example.metabox.trailer;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.movie.Movie;

@Entity
@Data
@NoArgsConstructor
@Table(name = "trailer_tb")
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    private String imgFilename; //포스터 파일 이름
    private String streamingFilename; // 스트리밍 파일 이름
    private String m3u8Filename; //m3u8 파일 이름 또는 경로

    @Builder
    public Trailer(int id, Movie movie, String imgFilename, String streamingFilename) {
        this.id = id;
        this.movie = movie;
        this.imgFilename = imgFilename;
        this.streamingFilename = streamingFilename;
    }
}
