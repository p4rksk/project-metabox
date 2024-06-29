package org.example.metabox.help;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.metabox.review.ReviewRequest;
import org.example.metabox.review.ReviewService;
import org.example.metabox.user.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class HelpController {


    @GetMapping("/help-faq")
    public String helpFAQ() {

        return "help/FAQ";
    }

    @GetMapping("/help-home")
    public String helpHome() {

        return "help/home";
    }

    @GetMapping("/help-personal")
    public String helpPersonal() {

        return "help/personal";
    }

    @GetMapping("/help-rent")
    public String helpRent() {

        return "help/rent";
    }

}
