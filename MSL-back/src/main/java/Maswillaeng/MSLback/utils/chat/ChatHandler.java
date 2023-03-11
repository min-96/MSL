package Maswillaeng.MSLback.utils.chat;


import Maswillaeng.MSLback.dto.common.*;
import Maswillaeng.MSLback.service.ChatService;
import Maswillaeng.MSLback.utils.auth.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
@AllArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    private Map<Long,WebSocketSession> userSocketList;

    private final ChatService chatService;
    private final ObjectMapper objectMapper;


    // WebSocket으로 받은 메시지 처리 로직
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload =  message.getPayload();
        log.info("payload : {} " ,payload);

        MessageEnum type = objectMapper.readValue(payload, MessageType.class).getType();
       // ChatMessageDto chat = objectMapper.readValue(payload,ChatMessageDto.class);
        switch (type) {
            case DM:
                // TODO: static 으로 함수만들기.
                ChatMessageDto chat = objectMapper.readValue(payload, ChatMessageDto.class);
                ChatResponseDto result = chatService.saveMessage(chat);
                if (userSocketList.get(chat.getDestinationUserId()) != null) {
                    // 클라이언트에서 메세지 받고 location 확인후 알림 쌓기
                    userSocketList.get(chat.getDestinationUserId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(result)));
                }
                break;
            case ACK:
                //상대가 그 채팅방에 있는지 없는지 확인  이걸 클라이언트에서 알려줘야되는데 ..
                //destinationUSerID에 메세지가 오면 상태값 알려주는게 가능한가?
                ChatAckDto ack = objectMapper.readValue(payload, ChatAckDto.class);
                chatService.stateUpdate(ack.getRoomId());
                // 서버가 굳이 응답을 안해줘도 클라이언트에서 읽음표시로 바꾸면 안되는가?
                //상대도 읽음표시 떠야되고 나도 읽음표시가 떠야됨

                break;
        }
    }

    //클라이언트가 연결 된 후
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userSocketList.put(UserContext.userData.get().getUserId(), session);
        log.info(UserContext.userData.get().getUserId() + " 클라이언트 접속");
        UserContext.remove();

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for(Long key : userSocketList.keySet()){
            if(userSocketList.get(key).equals(session)){
                userSocketList.remove(key);
            }
        }
    }
}
