package org.example.metabox.book;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox._core.util.FormatUtil;
import org.example.metabox.user.SessionGuest;
import org.example.metabox.user.SessionUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {
    private final HttpSession session;
    private final BookService bookService;

    @PostMapping("/books/complete")
    public ResponseEntity<?> completeBook(@RequestBody BookRequest.PaymentRequestDTO paymentRequestDTO) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        SessionGuest sessionGuest = (SessionGuest) session.getAttribute("sessionGuest");
        Integer userId;
        if (sessionUser != null) {
            userId = sessionUser.getId();
            bookService.completeBook(paymentRequestDTO, userId);
        } else {
            userId = sessionGuest.getId();
            bookService.completeBookGuest(paymentRequestDTO, userId);
        }

        return ResponseEntity.ok("/mypage/detail-book");
    }

    //  TODO :  로그인 유저만 이용할 수 있도록 interceptor
    @GetMapping("/book-form")
    public String bookForm(HttpServletRequest request, @RequestParam(value = "theaterId", required = false) Integer theaterId, @RequestParam(value = "movieId", required = false) Integer movieId, @RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null) {
            date = FormatUtil.currentDate();
        }
        BookResponse.BookDTO respDTO = bookService.theaterAreaList();
        request.setAttribute("model", respDTO);
        System.out.println(respDTO);
        return "book/book-form";
    }

//    @GetMapping("/book-form-sub-list-ajax")
//    public ResponseEntity<?> bookFormSubList(@RequestParam(value = "areaCode") String areaCode) {
//        List<String> respDTO = bookService.bookFormSubList(areaCode);
//        return ResponseEntity.ok(new ApiUtil<>(respDTO));
//    }

    @GetMapping("/seat-form/{screeningInfoId}")
    public String seatForm(HttpServletRequest request, @PathVariable int screeningInfoId) {
        BookResponse.BookSeatDTO respDTO = bookService.seatDetail(screeningInfoId);
        System.out.println(respDTO);
        request.setAttribute("model", respDTO);
        return "book/seat-basic-form";
    }

    // 결제 페이지
    @GetMapping("/payment-form")
    public String bookForm(@RequestParam("id") int screeningInfoId, @RequestParam("ids") String ids, HttpServletRequest request) {
        // 콤마로 구분된 ID 문자열을 리스트로 변환
        List<String> idList = Arrays.asList(ids.split(","));
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        BookResponse.PaymentDTO respDTO = bookService.payment(idList, screeningInfoId, sessionUser.getId());
        request.setAttribute("model", respDTO);
        return "payment/payment-form";
    }
}
