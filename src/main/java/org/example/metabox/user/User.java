package org.example.metabox.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@NoArgsConstructor
@Entity
@Data
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String loginId; // 아이디
    private String password; //패스워드

    @CreationTimestamp
    private Timestamp createdAt;

//    // 사진이 null로 들어올때 디폴트 값 설정하기
//    @PrePersist // 엔티티가 저장되기 전에 실행되는 메서드, 필드에 기본값 설정
//    public void setDefaultImgFilename() {
//        if(imgFilename == null) {
//            imgFilename = "default.png";
//        }
//    }


    @Builder
    public User(Integer id, String loginId, String password, Timestamp createdAt) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.createdAt = createdAt;
    }
}