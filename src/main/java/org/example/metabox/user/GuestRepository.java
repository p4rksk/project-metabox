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
    Guest findOneByPhone(String phone);

    //비회원 로그인
    @Query("select g from Guest g where g.birth = :birth and g.password = :password")
    Guest findByBirthAndPassword(@Param("birth") String birth, @Param("password") String password);


    // 비회원 예매조회
    @Query("select new org.example.metabox.user.UserResponse$GuestCheckDTO$UserDTO(g.id, g.name, g.password, g.phone, b.bookNum) " +
            "from Guest g join fetch Book b on g.id = b.guest.id where g.name = :name and g.password = :password and g.phone = :phone and b.bookNum = :bookNum")
    Optional<UserResponse.GuestCheckDTO.UserDTO> findByGuest(String name, String password, String phone, String bookNum);
}
