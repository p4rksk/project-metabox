package org.example.metabox.screening;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "screening_tb")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    // 좌석수(count 안할려고 미리 하는듯?)
    private Integer seat_count;
    // TODO : Enum 처리
    private String screening_rank;
}
