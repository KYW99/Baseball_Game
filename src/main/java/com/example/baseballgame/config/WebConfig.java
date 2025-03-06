package com.example.baseballgame.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /game/** 경로는 정적 리소스가 아닌 동적 요청으로 처리되도록 수정
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);

        registry.addResourceHandler("/game/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }

}
