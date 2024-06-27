package org.example.metabox.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query("select s from Seat s where s.screening.id = :screeningId")
    List<Seat> findAllByScreeningId(@Param("screeningId") int screeningId);
}
