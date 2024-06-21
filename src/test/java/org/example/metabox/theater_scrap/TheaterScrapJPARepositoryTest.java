package org.example.metabox.theater_scrap;

import org.example.metabox.admin.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class TheaterScrapJPARepositoryTest {
    @Autowired
    private TheaterScrapRepository theaterScrapRepository;

    @Test
    public void findByUserId_test(){
        // given
//        int userId = 1;
//
//        // when
//        List<TheaterScrapResponse.TheaterScrapDTO> respDTO = theaterScrapRepository.findByUserId(userId);
//        // then
//        System.out.println("respDTO : " + respDTO);

    }



}
