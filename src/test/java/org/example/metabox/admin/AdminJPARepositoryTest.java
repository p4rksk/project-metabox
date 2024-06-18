package org.example.metabox.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class AdminJPARepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void findByLoginIdAndPassword_test(){
        // given
        String loginId = "metabox";
        String password = "metabox1234";
        // when
        Optional<Admin> admin = adminRepository.findByLoginIdAndPassword(loginId,password);

        System.out.println("admin"+admin);
        // then


    }

}
