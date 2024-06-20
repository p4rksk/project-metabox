package org.example.metabox.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.nickname = :nickname")
    User findByNickname(String nickname);

    @Modifying
    @Query("delete from User u where u.nickname = :nickname")
    void deleteByNickname(String nickname);
}
