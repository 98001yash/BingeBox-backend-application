package com.company.BingeBox_backend_application.catalog_service.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("X-User-Id");
        String roles = request.getHeader("X-User-Roles"); // comma-separated roles

        if (userId != null) {
            UserContextHolder.setCurrentUserId(Long.valueOf(userId));
        }

        if (roles != null) {
            UserContextHolder.setCurrentUserRoles(Collections.singletonList(roles)); // store roles for RBAC
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clear();
    }
}
