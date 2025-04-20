package org.example.expert.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.config.error.MyJwtException;

import java.io.IOException;

@Slf4j
public class ErrorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try{
            chain.doFilter(servletRequest, servletResponse);
        }catch (MyJwtException e){
            sendError((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, e);
        }
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response, MyJwtException e) {


        response.setStatus(e.getErrorType().getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        try {
            String json = String.format(
                    "{ \"code\": %d, \"message\": \"%s\", \"status\": \"%s\" }",
                    e.getErrorType().getHttpStatus().value(),
                    e.getErrorType().getMessage(),
                    e.getErrorType().getHttpStatus().name()
            );

            response.getWriter().write(json);
            log.info("METHOD : {}, URI : {} , USER-AGENT : {} , IP : {} , STATUS : {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getHeader("User-Agent"),
                    request.getRemoteAddr(),
                    response.getStatus()
            );
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
