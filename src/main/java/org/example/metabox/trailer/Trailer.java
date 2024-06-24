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

    private String streamingFilePath; // 스트리밍 파일 경로
    private String m3u8FilePath; //m3u8 파일 경로

    @Builder
    public Trailer(int id, Movie movie, String streamingFilePath, String m3u8FilePath) {
        this.id = id;
        this.movie = movie;
        this.streamingFilePath = streamingFilePath;
        this.m3u8FilePath = m3u8FilePath;
    }
}
