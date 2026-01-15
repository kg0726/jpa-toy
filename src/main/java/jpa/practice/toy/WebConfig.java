package jpa.practice.toy;

import jpa.practice.toy.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인터셉터 등록
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)  // 인터셉터 실행 순서
                .addPathPatterns("/**")  // 모든 경로에 인터셉터 적용
                .excludePathPatterns(
                        "/api/members/join",
                        "/api/login",
                        "/api/logout",
                        "css/**", "/*.ico", "/error" // 정적 리소스나 에러 페이지 허용함
                );

    }
}
