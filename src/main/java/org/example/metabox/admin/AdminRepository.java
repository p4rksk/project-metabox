package org.example.metabox.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a WHERE a.loginId = :loginId AND a.password = :password")
    Optional<Admin> findByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);

}
