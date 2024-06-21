package org.example.metabox.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

    //비회원 회원 가입 때  동일한 휴대폰 번호 있는지 확인
    @Query("select g from Guest g where g.phone = :phone")
    Optional<Guest> findOneByPhone(String phone);

    //비회원 로그인
    @Query("select g from Guest g where g.birth = :birth and g.password = :password")
    Optional<Guest> findByBirthAndPassword(@Param("birth") String birth, @Param("password") String password);

}
