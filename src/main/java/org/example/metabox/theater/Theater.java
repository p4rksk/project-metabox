package org.example.metabox.theater;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.screening.Screening;
import org.example.metabox.theater_movie.TheaterMovie;
import org.example.metabox.theater_scrap.TheaterScrap;
import org.hibernate.engine.internal.Cascade;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "theater_tb")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Screening> screening;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TheaterMovie> theaterMovies;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TheaterScrap> theaterScrapList;

    private String name;
    private String imgFilename;
    private String address;

    // 051-153-159
    private String number;
    private String loginId;
    private String password;
    // 실시간 길안내 네이버 지도 url
    private String url;
    // 주차 안내
    private String parkingInfo;
}
