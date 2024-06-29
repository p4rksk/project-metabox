package org.example.metabox.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class SessionUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String nickname;
    private String imgFilename;
    private String name;
    private String birthYear;
    private int point;
    private String provider;    //kakao, naver OAuth
    private String accessToken;

    public SessionUser(User user, String accessToken) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.imgFilename = user.getImgFilename();
        this.name = user.getName();
        this.birthYear = user.getBirthYear();
        this.point = user.getPoint();
        this.provider = user.getProvider();
        this.accessToken = accessToken;
    }
}