package org.example.metabox.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class PaymentController {
    @GetMapping("/payment-form")
    public String bookForm() {
        return "payment/payment-form";
    }
}
