package org.example.metabox.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatBookRepository extends JpaRepository<SeatBook, Integer> {

    @Query("select sb from SeatBook sb where sb.book.guest.id = :guestId")
    List<SeatBook> findByGuestId(@Param("guestId") Integer guestId);
}
