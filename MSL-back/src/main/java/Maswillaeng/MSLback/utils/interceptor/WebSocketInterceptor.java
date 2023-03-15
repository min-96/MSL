//package Maswillaeng.MSLback.utils.interceptor;
//
//import Maswillaeng.MSLback.jwt.JwtTokenProvider;
//import Maswillaeng.MSLback.utils.auth.UserContext;
//import Maswillaeng.MSLback.utils.auth.ValidTokenProcess;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//
//@ComponentScan
//@NoArgsConstructor
//public class WebSocketInterceptor implements HandshakeInterceptor {
//
//    @Autowired
//   private  JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
//            HttpServletRequest req = (HttpServletRequest) request;
//            HttpServletResponse res = (HttpServletResponse) response;
//            System.out.println("Asd");
//            return ValidTokenProcess.execute(req,res,jwtTokenProvider);
//        }
//        return false;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//    }
//
//
//
//}
