package org.example.metabox.seat;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "seat_tb")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 좌석번호 ex : A9
    private String code;
    private Boolean booked;
    private String price;
    // 장애인석, 라이트석, 일반석
    // TODO : ENUM 처리
    private String type;
}
