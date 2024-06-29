package org.example.metabox.book;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.seat.SeatBook;
import org.example.metabox.user.Guest;
import org.example.metabox.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book_tb") // 예매, 결제 테이블
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Guest guest;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SeatBook> seatBookList;

    // 전체 금액(포인트 사용 전)
    private Integer totalPrice;

    // 예매시간(결제시간)
    @CreationTimestamp
    private LocalDateTime createdAt;

    //지급 포인트, 결제로 받는 금액(결제금액의 5%)
    private Integer point;

    // 사용한 포인트
    private Integer used_point;

    // 최종 결제 금액
    private Integer book_price;

    // 예매번호
    private String bookNum;

    @Builder
    public Book(String bookNum, int id, User user, Guest guest, List<SeatBook> seatBookList, Integer totalPrice, LocalDateTime createdAt, Integer point, Integer used_point, Integer book_price) {
        this.id = id;
        this.user = user;
        this.guest = guest;
        this.seatBookList = seatBookList;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.point = point;
        this.used_point = used_point;
        this.book_price = book_price;
        this.bookNum = bookNum;
    }
}
