package com.triakobah.TaskParser.core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RequestIdAddingInterceptor implements HandlerInterceptor {
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestIdAddingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String id = UUID.randomUUID().toString();
        MDC.put("id", id);

        LOGGER.info("Path: '" + request.getRequestURI().substring(request.getContextPath().length()) + "'");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove("id");
    }

}
