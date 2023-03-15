package Maswillaeng.MSLback.utils.chat;


import Maswillaeng.MSLback.dto.common.*;
import Maswillaeng.MSLback.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    private final Map<Long,WebSocketSession> userSocketList;

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
            case ENTER :
                String[] str = payload.split(":");
                Long userId = Long.parseLong(str[1]);
                userSocketList.put(userId, session);
                break;

            case MESSAGE:
                // TODO: static 으로 함수만들기.
                ChatMessageDto chat = objectMapper.readValue(payload, ChatMessageDto.class);
                ChatResponseDto result = chatService.saveMessage(chat); //
                if (userSocketList.get(chat.getRecipientId()) != null) {
                    // 클라이언트에서 메세지 받고 location 확인후 알림 쌓기
                    userSocketList.get(chat.getRecipientId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(result)));
                }
                break;
            case ACK:
                ChatAckDto ack = objectMapper.readValue(payload, ChatAckDto.class);
                if(chatService.stateUpdate(ack.getRoomId())) {
                    userSocketList.get(ack.getSenderId()).sendMessage(new TextMessage("ok"));
                }
                break;
        }

    }

    //클라이언트가 연결 된 후
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    //    userSocketList.put(UserContext.userData.get().getUserId(), session);
        log.info(" 클라이언트 접속");
     //   UserContext.remove();

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
