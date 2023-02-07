package Maswillaeng.MSLback.configuration;

import Maswillaeng.MSLback.utils.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    // private final TokenArgumentResolver tokenArgumentResolver;

    @Bean
    public AuthInterceptor jwtTokenInterceptor() {
        return new AuthInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        // List<String> excludes = Arrays.asList("/join", "/duplicate");

        registry.addInterceptor(jwtTokenInterceptor());
    }

}
