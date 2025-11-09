package com.example.tradestore.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String cid = MDC.get("cid");
        log.info("Incoming request [{}] {} CID:{}", req.getMethod(), req.getRequestURI(), cid);
        chain.doFilter(request, response);
        log.info("Completed request [{}] {} CID:{}", req.getMethod(), req.getRequestURI(), cid);
    }
}
