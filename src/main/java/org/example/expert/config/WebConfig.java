package org.example.expert.config;

import lombok.RequiredArgsConstructor;
import org.example.expert.interceptor.AdminInterceptor;
import org.example.expert.interceptor.LogInfo;
import org.example.expert.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MyInterceptor myInterceptor;
    private final AdminInterceptor adminInterceptor;
    // ArgumentResolver 등록
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**");
        registry.addInterceptor(adminInterceptor)
                .order(1)
                .addPathPatterns("/admin/**");
    }

}
