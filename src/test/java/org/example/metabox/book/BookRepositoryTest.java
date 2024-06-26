package org.example.metabox.book;

import jakarta.persistence.EntityManager;
import org.example.metabox.movie.MovieQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void getTotalSales_Test(){
        // given

        // when
        Long result = bookRepository.getTotalSales();

        // then
        System.out.println("##########" + result);
    }

    @Test
    public void findSalesByTheater() {
        // given

        // when
        List<Object[]> lists = bookRepository.findSalesByTheater();

        // then
        System.out.println("########## Results ##########");
        for (Object[] result : lists) {
            System.out.println("Result: " + Arrays.toString(result));
            System.out.println("Type of result[0]: " + result[0].getClass().getName());
            System.out.println("Type of result[1]: " + result[1].getClass().getName());
            System.out.println("Type of result[2]: " + result[2].getClass().getName());

            Integer theaterId = (Integer) result[0];
            String theaterName = (String) result[1];
            Long theaterSales = (Long) result[2];

            System.out.println("Theater ID: " + theaterId + ", Theater Name: " + theaterName + ", Sales: " + theaterSales);
        }
    }
}
