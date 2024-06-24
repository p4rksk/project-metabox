package org.example.metabox._core.config;

import org.example.metabox._core.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 로그인 인터셉터
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api-admin/**", "/api-user/**", "/api-theater/**", "/api-guest/**");
//                 예외 처리
//                .excludePathPatterns("");
    }


    //외부이미지 경로설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /video/** 경로로 들어오는 요청에 대해 비디오 스트리밍을 제공합니다.
        registry.addResourceHandler("/video/**")
                // 비디오 파일이 위치한 실제 경로 설정 (예: file:./videos/)
                .addResourceLocations("file:./videos/")
                // 캐시 기간 설정 (여기서는 0으로 설정하여 캐싱을 비활성화)
                .setCachePeriod(0)
                // 리소스 체인 설정 (true로 설정하여 추가적인 리소스 체인 사용)
                .resourceChain(true)
                // PathResourceResolver 추가
                .addResolver(new PathResourceResolver());

        // /image/** 경로로 들어오는 요청에 대해 현재 디렉터리의 ./image/ 디렉터리의 리소스를 사용합니다.
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:./image/")
                .setCachePeriod(3600) // 한 시간 (초 단위)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

}