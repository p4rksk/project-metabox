package org.example.metabox.theater_movie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "theater_movie_tb")
public class TheaterMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // TODO : Enum 처리
    // 상영(현재 상영중) 유무, 현재 상영작, 상영 예정작, 종료작
    private String status;
}
