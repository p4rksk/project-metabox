package org.example.metabox._core.interceptor;

import org.example.metabox._core.errors.exception.Exception401;
import org.example.metabox.admin.Admin;
import org.example.metabox.theater.Theater;
import org.example.metabox.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle............");
        HttpSession session = request.getSession();

        Admin sessionAdmin = (Admin) session.getAttribute("sessionAdmin");
        User sessionCustomer = (User) session.getAttribute("sessionUser");
        Theater sessionTheater = (Theater) session.getAttribute("sessionTheater");

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api-admin")) {
            if (sessionAdmin == null) {
                throw new Exception401("잘못된 접근입니다.");
            }
        } else if (requestURI.startsWith("/api-user")) {
            if (sessionCustomer == null) {
                throw new Exception401("고객 로그인이 필요합니다");
            }
        } else if (requestURI.startsWith("/api-theater")) {
            if (sessionTheater == null) {
                throw new Exception401("극장 관리자 로그인이 필요합니다");
            }
        }else {
            // Shared paths accessible to both
            if (sessionAdmin == null && sessionCustomer == null && sessionTheater == null) {
                throw new Exception401("로그인 하셔야 해요");
            }
        }

        return true;
    }
}
