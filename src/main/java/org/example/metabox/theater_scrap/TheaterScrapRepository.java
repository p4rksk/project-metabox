package org.example.metabox.theater_scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TheaterScrapRepository extends JpaRepository<TheaterScrap, Integer> {

    @Query("SELECT ts FROM TheaterScrap ts WHERE ts.user.id = :userId")
    List<TheaterScrap> findByUserId(@Param("userId") int userId);
}
