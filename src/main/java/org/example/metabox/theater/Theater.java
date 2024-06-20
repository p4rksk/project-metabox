package org.example.metabox.theater;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.screening.Screening;
import org.example.metabox.theater_scrap.TheaterScrap;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "theater_tb")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Screening> screeningList;

    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TheaterScrap> theaterScrapList;

    private String name;
    private String imgFilename;
    private String address;

    // 지역 코드 01,02,03,04,05,06,07,08,09(서울, 경기, 인천, 강원, 대전/충청.....)
    private String areaCode;

    // 051-153-159
    private String number;
    private String loginId;
    private String password;
    // 실시간 길안내 네이버 지도 url
    private String url;
    // 주차 안내
    private String parkingInfo;

    @Builder
    public Theater(int id, List<Screening> screeningList, List<TheaterScrap> theaterScrapList, String name, String imgFilename, String address, String areaCode, String number, String loginId, String password, String url, String parkingInfo) {
        this.id = id;
        this.screeningList = screeningList;
        this.theaterScrapList = theaterScrapList;
        this.name = name;
        this.imgFilename = imgFilename;
        this.address = address;
        this.areaCode = areaCode;
        this.number = number;
        this.loginId = loginId;
        this.password = password;
        this.url = url;
        this.parkingInfo = parkingInfo;
    }
}
