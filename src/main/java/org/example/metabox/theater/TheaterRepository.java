package org.example.metabox.theater;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {

    @Query("select t from Theater t where t.areaCode = :areaCode")
    List<Theater> findByArea(String areaCode);

    @Query("SELECT distinct(t.areaName, t.areaCode) FROM Theater t ORDER BY t.id ASC")
    List<String[]> findAllDistinctArea();

    @Query("select t from Theater t where t.loginId = :loginId and t.password = :password")
    Optional<Theater> findByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);
}
