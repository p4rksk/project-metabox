package org.example.metabox.theater_scrap;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.theater.Theater;
import org.example.metabox.user.User;

@Entity
@Data
@NoArgsConstructor
@Table(name = "theater_scrap_tb")
public class TheaterScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Theater theater;
}
