package org.example.expert.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@AllArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final ThreadLocal<LogInfo> logInfoThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LogInfo logInfo = LogInfo.builder()
                .startTime(startTime)
                .method(request.getMethod())
                .uri((String) request.getAttribute("originalURI"))
                .userAgent(request.getHeader("User-Agent"))
                .ip(request.getRemoteAddr())
                .duration(System.currentTimeMillis())
                .build();
        logInfoThreadLocal.set(logInfo);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LogInfo logInfo = logInfoThreadLocal.get();
        logInfoThreadLocal.remove();
        long duration = System.currentTimeMillis() - logInfo.getDuration();
        log.info("ADMIN METHOD : {}, URI : {} , USER-AGENT : {} , IP : {} , STATUS : {} , DURATION : {}ms",
                logInfo.getMethod(),
                logInfo.getUri(),
                logInfo.getUserAgent(),
                logInfo.getIp(),
                response.getStatus(),
                duration);
    }
}
