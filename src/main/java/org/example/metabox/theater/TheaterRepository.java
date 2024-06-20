package org.example.metabox.theater;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {


//    @Query("select new org.example.metabox.theater.TheaterResponse$ form Theater t where  ")
//    void findTheaterInfoByUserId(Integer id);
}
