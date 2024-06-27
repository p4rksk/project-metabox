package org.example.metabox.book;

import org.example.metabox.admin.AdminResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select sum(b.book_price) as totalSales from Book b")
    Long getTotalSales();

    @Query("select t.id, t.name as theatherName, SUM(b.book_price) as theaterSales from Book b join SeatBook sb on sb.book.id = b.id join Seat s on sb.seat.id = s.id join Screening sc on s.screening.id = sc.id join Theater t on sc.theater.id = t.id group by t.id, t.name order by theaterSales desc ")
    List<Object[]> findSalesByTheater();
}
