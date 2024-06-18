package org.example.metabox.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BookController {
    //  TODO :  로그인 유저만 이용할 수 있도록 interceptor
    @GetMapping("book-form")
    public String bookForm() {
        return "book/book-form";
    }

    @GetMapping("seat-form")
    public String seatForm() {
        return "book/seat-form";
    }
}
