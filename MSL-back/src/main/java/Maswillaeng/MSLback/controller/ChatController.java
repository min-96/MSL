package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.ChatService;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @ValidToken
    @PostMapping("/api/create/chat-room/{targetId}")
    public ResponseEntity<?> createRoom(@PathVariable Long targetId) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_CREATE_CHATROOM,
                chatService.createRoom(UserContext.userData.get().getUserId(), targetId)
        ));
    }

    @ValidToken
    @GetMapping("/api/chat-room/list")
    public ResponseEntity<?> getChatRoomList() {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_CHATROOM_LIST,
                chatService.getChatRoomList(UserContext.userData.get().getUserId())
        ));
    }

    @ValidToken
    @GetMapping("/api/chat/list/{roomId}")
    public ResponseEntity<?> getChatList(@PathVariable Long roomId) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_CHAT_LIST,
                chatService.getChatList(roomId)
        ));
    }

    @ValidToken
    @GetMapping("/api/exist/chat/{targetId}")
    public ResponseEntity<?> existChatRoom(@PathVariable Long targetId) {

        Map<String, Boolean> response = new HashMap<>();
        if (chatService.getChatRoom(UserContext.userData.get().getUserId(), targetId) != null) {
            response.put("status", true);
            return ResponseEntity.ok().body(ResponseDto.of(
                    SUCCESS_CHECK_CHAT_EXIST,
                    response));
        }
        response.put("status", false);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_CHECK_CHAT_NOT_EXIST,
                response));
    }

    @ValidToken
    @GetMapping("/api/exist/chat-alarm")
    public ResponseEntity<?> messageAlarm() {

        Map<String, Boolean> response = new HashMap<>();
        if (chatService.existChatMessage(UserContext.userData.get().getUserId())) {
            response.put("alarm", true);

            return ResponseEntity.ok().body(ResponseDto.of(
                    SUCCESS_CHECK_UNREAD_CHAT_EXIST,
                    response));
        }
        response.put("alarm", false);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_CHECK_UNREAD_CHAT_NOT_EXIST,
                response));
    }

}
