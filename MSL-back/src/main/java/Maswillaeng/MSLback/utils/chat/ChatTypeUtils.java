package Maswillaeng.MSLback.utils.chat;

import Maswillaeng.MSLback.dto.chat.request.ChatAckDto;
import Maswillaeng.MSLback.dto.chat.request.ChatMessageDto;
import Maswillaeng.MSLback.dto.chat.request.EnterTypeDto;
import Maswillaeng.MSLback.dto.chat.response.ChatResponseDto;
import Maswillaeng.MSLback.dto.chat.response.SocketStatus;
import Maswillaeng.MSLback.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatTypeUtils {

    private final ObjectMapper objectMapper;

    private final ChatService chatService;

    public void EnterTypeProcess(String payload, WebSocketSession session, Map<Long,WebSocketSession> userSocketList) throws JsonProcessingException {
        EnterTypeDto data = objectMapper.readValue(payload, EnterTypeDto.class);
        userSocketList.put(data.getUserId(), session);
    }

    public void MessageTypeProcess(String payload, Map<Long, WebSocketSession> userSocketList) throws IOException {
        ChatMessageDto chat = objectMapper.readValue(payload, ChatMessageDto.class);
        ChatResponseDto responseDto = null;
        SocketStatus socketStatus = new SocketStatus(400, "duplicateError");
        try {
            responseDto = chatService.saveMessage(chat);
        } catch (DuplicateKeyException e) {
            userSocketList.get(chat.getSenderId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(socketStatus)));
        }
        if (userSocketList.get(chat.getRecipientId()) != null) {
             userSocketList.get(chat.getRecipientId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(responseDto)));
        }
    }

    public void ACKTypeProcess(String payload,Map<Long, WebSocketSession> userSocketList) throws IOException {

        SocketStatus socketStatus = new SocketStatus(400, "true");
        ChatAckDto ack = objectMapper.readValue(payload, ChatAckDto.class);
        log.info("보낼사람 : {}" ,  ack.getSenderId());
        if(chatService.stateUpdate(ack.getChatId())) {
            userSocketList.get(ack.getSenderId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(socketStatus)));
        }

    }
}
