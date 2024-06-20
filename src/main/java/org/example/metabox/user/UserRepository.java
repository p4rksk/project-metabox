package org.example.metabox.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    // OAuth 중복 닉네임 있는지 확인 쿼리
    @Query("select u from User u where u.nickname = :nickname")
    User findByNickname(String nickname);

    // OAuth 회원탈톼 (pk로 해야하면 말해주세여)
    @Modifying
    @Query("delete from User u where u.nickname = :nickname")
    void deleteByNickname(String nickname);
}
