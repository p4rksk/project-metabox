package org.example.metabox.book;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BookController {
    private final HttpSession session;
    private final BookService bookService;

    //  TODO :  로그인 유저만 이용할 수 있도록 interceptor
    @GetMapping("/book-form")
    public String bookForm(HttpServletRequest request) {
        BookResponse.BookDTO respDTO = bookService.theaterAreaList();
        request.setAttribute("model", respDTO);
        System.out.println("--------------------------");
        System.out.println(respDTO);
        return "book/book-form";
    }

    @GetMapping("/seat-form")
    public String seatForm() {
        return "book/seat-basic-form";
    }
}
