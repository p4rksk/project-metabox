package org.example.metabox._core.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.example.metabox._core.errors.exception.ApiException400;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class MyApiExceptionHandler {

    @ExceptionHandler(ApiException400.class)
    public @ResponseBody String exApi400(ApiException400 e, HttpServletRequest request) {
        return "fail";

    }

}
