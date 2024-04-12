package com.dawnbit.security.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author DB-CPU009
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CorsConfig implements Filter {
    /**
     * A method to perform filtering on the servlet request and response.
     *
     * @param req   the servlet request
     * @param res   the servlet response
     * @param chain the filter chain
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        if (log.isInfoEnabled()) {
            log.info("Entering to override destroy filters");
        }
    }

    @Override
    public void init(final FilterConfig arg0) throws ServletException {
        if (log.isInfoEnabled()) {
            log.info("Entering to override init filters");
        }
    }

}


