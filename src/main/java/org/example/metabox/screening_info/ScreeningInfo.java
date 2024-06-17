package org.example.metabox.screening_info;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "screening_info_tb")
public class ScreeningInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 상영 시간
    private Timestamp showtime;
    private Timestamp starttime;
    private Timestamp endtime;
    private Date date;

}
