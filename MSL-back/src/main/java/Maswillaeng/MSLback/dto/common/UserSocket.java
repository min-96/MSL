package Maswillaeng.MSLback.dto.common;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

@Getter
public class UserSocket {
    private Long userId;
    private WebSocketSession session;

    public UserSocket(Long userId, WebSocketSession session) {
        this.userId = userId;
        this.session = session;
    }
}
