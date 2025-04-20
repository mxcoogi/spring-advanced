package org.example.expert.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.example.expert.config.error.MyJwtException;

import java.io.IOException;

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
            sendError((HttpServletResponse) servletResponse, e);
        }
    }

    private void sendError(HttpServletResponse response, MyJwtException e) {


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
        } catch (IOException ioException) {
            // 로깅만 처리해주자
            ioException.printStackTrace();
        }
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
