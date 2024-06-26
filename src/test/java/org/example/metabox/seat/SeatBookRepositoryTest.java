package org.example.metabox.seat;

import jakarta.persistence.EntityManager;
import org.example.metabox.movie.MovieQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(SeatBookRepository.class)
public class SeatBookRepositoryTest {

    @Autowired
    private SeatBookRepository seatBookRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void countTotalBooking_Test(){

    }
}
