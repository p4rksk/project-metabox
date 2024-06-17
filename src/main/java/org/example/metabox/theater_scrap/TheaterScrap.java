package org.example.metabox.theater_scrap;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "theater_scrap_tb")
public class TheaterScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
