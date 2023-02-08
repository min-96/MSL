package Maswillaeng.MSLback.configuration;

import Maswillaeng.MSLback.utils.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    // private final TokenArgumentResolver tokenArgumentResolver;


    @Bean
    public AuthInterceptor jwtTokenInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET","POST","PUT","DELETE");
    }

    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludes = Arrays.asList("/favicon.ico");

        registry.addInterceptor(jwtTokenInterceptor()).excludePathPatterns(excludes);
    }



}
