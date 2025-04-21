package org.example.expert.config;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.filter.ErrorFilter;
import org.example.expert.config.filter.JwtFilter;
import org.example.expert.config.utils.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(jwtUtil));
        registrationBean.addUrlPatterns("/*"); // 필터를 적용할 URL 패턴을 지정합니다.
        registrationBean.setOrder(2);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<ErrorFilter> errorFilter() {
        FilterRegistrationBean<ErrorFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ErrorFilter());
        registrationBean.addUrlPatterns("/*"); // 필터를 적용할 URL 패턴을 지정합니다.
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
