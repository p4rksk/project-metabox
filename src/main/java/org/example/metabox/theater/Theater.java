package org.example.metabox.theater;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "theater_tb")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String loginId;
    private String password;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Theater(int id, String loginId, String password, Timestamp createdAt) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.createdAt = createdAt;
    }
}
