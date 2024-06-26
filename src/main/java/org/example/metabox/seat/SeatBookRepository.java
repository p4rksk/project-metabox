package org.example.metabox.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface SeatBookRepository extends JpaRepository<SeatBook, Integer> {
}
