package com.example.tradestore.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String cid = ((HttpServletRequest) request).getHeader("X-Correlation-Id");
            if (cid == null || cid.isBlank()) {
                cid = UUID.randomUUID().toString();
            }
            MDC.put("cid", cid);
            chain.doFilter(request, response);
        } finally {
            MDC.remove("cid");
        }
    }

    @Override
    public void destroy() { }
}
