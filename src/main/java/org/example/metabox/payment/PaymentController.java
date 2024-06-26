package org.example.metabox.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.metabox.user.SessionUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j

public class PaymentController {
//
//    private IamportClient iamportClient;
//    private final RefundService refundService;
//    private PaymentService paymentService;
    private HttpSession session;

//    @Value("${imp.api.key}")
//    private String apiKey;
//
//    @Value("${imp.api.secretkey}")
//    private String secretKey;
//
//    @PostConstruct
//    public void init() {
//        this.iamportClient = new IamportClient(apiKey, secretKey);
//    }
//
//
    @PostMapping("/api/v1/payment")
    public ResponseEntity<String> paymentComplete() throws IOException {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        try {
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/payment-form")
    public String paymentForm() {
        return "payment/payment-form";
    }
}
