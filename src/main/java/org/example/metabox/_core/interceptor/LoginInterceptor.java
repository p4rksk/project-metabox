package org.example.metabox._core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.metabox._core.errors.exception.Exception401;
import org.example.metabox.admin.SessionAdmin;
import org.example.metabox.theater.SessionTheater;
import org.example.metabox.user.SessionGuest;
import org.example.metabox.user.SessionUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginInterceptor implements HandlerInterceptor {
    RedisTemplate<String, Object> rt;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle............");
        HttpSession session = request.getSession();


        SessionGuest sessionGuest = (SessionGuest) rt.opsForValue().get("sessionGuest");
        SessionAdmin sessionAdmin = (SessionAdmin) rt.opsForValue().get("sessionAdmin");
        SessionUser sessionCustomer = (SessionUser) rt.opsForValue().get("sessionUser");
        SessionTheater sessionTheater = (SessionTheater) rt.opsForValue().get("sessionTheater");

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
        } else if (requestURI.startsWith("/api-guest")) {
            if (sessionGuest == null) {
                throw new Exception401("비회원 로그인이 필요합니다");
            }
        } else {
            // Shared paths accessible to both
            if (sessionAdmin == null && sessionCustomer == null && sessionTheater == null && sessionGuest == null) {
                throw new Exception401("로그인 하셔야 해요");
            }
        }

        return true;
    }
}
