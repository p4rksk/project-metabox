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

    private String streamingFilename; // 스트리밍 파일 이름
    private String masterM3U8Filename; // masterM3u8 파일 이름

    @Builder
    public Trailer(int id, Movie movie, String streamingFilename, String masterM3U8Filename) {
        this.id = id;
        this.movie = movie;
        this.streamingFilename = streamingFilename;
        this.masterM3U8Filename = masterM3U8Filename;
    }
}