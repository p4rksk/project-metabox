package org.example.metabox.payment;

import lombok.Data;
import org.example.metabox.book.Book;
import org.example.metabox.seat.SeatBook;
import org.example.metabox.user.Guest;
import org.example.metabox.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentResponse {

    @Data
    public static class PaymentDTO {
        private User user;
        private Guest guest;
        private List<SeatBook> seatBookList;
        private Integer totalPrice;
        private Integer point;
        private Integer used_point;
        private Integer book_price;

        public PaymentDTO(Book book){
            this.user = book.getUser();
            this.guest = book.getGuest();
            this.seatBookList = book.getSeatBookList();
            this.totalPrice = book.getTotalPrice();
            this.point = book.getPoint();
            this.used_point=book.getUsed_point();
            this.book_price=book.getBook_price();
        }
    }


}
