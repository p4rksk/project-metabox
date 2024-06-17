package org.example.metabox.theater;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "theater_tb")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    // 이미지 ㅏ일 내용 추가
}
