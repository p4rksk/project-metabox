package org.example.metabox.theater;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SessionTheater {

    private int id;

    private String name;
    private String imgFilename;
    private String address;

    // 서울, 경기, 인천, 강원, 대전/충청..... 01,02,03,04,05,06
    private String areaCode;
    private String areaName;

    // 051-153-159
    private String number;
    private String loginId;
    // 실시간 길안내 네이버 지도 url
    private String url;
    // 주차 안내
    private String parkingInfo;

    public SessionTheater(Theater theater) {
        this.id = theater.getId();
        this.name = theater.getName();
        this.imgFilename = theater.getImgFilename();
        this.address = theater.getAddress();
        this.areaCode = theater.getAreaCode();
        this.areaName = theater.getAreaName();
        this.number = theater.getNumber();
        this.loginId = theater.getLoginId();
        this.url = theater.getUrl();
        this.parkingInfo = theater.getParkingInfo();
    }
}
