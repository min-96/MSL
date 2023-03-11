package Maswillaeng.MSLback.configuration;

import Maswillaeng.MSLback.utils.auth.ValidToken;
import Maswillaeng.MSLback.utils.chat.ChatHandler;
import Maswillaeng.MSLback.utils.interceptor.WebSocketInterceptor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    private final ChatHandler chatHandler;

    @Bean
    public WebSocketInterceptor webSocketInterceptor(){
        return new WebSocketInterceptor();
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*")
                .addInterceptors(new WebSocketInterceptor());

    }

//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chat")
//                .setAllowedOriginPatterns("*");
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/sub");
//        registry.setApplicationDestinationPrefixes("/pub");
//    }
}
