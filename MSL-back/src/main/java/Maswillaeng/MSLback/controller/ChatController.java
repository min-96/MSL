package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ChatMessageDto;
import Maswillaeng.MSLback.dto.common.CreateRoomResponseDto;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.ChatService;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    @ValidToken
    @PostMapping("/api/create/chat-room/{targetId}")
    public ResponseEntity<?> createRoom(@PathVariable Long targetId) {

        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅방이 성공적으로 생성되었습니다.",
                chatService.createRoom(UserContext.userData.get().getUserId(), targetId)

        ));
    }

    @ValidToken
    @GetMapping("/api/chat-room/list")
    public ResponseEntity<?> getChatRoomList() {
        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅방이 성공적으로 조회되었습니다.",
                chatService.getChatRoomList(UserContext.userData.get().getUserId())
        ));
    }

    @ValidToken
    @GetMapping("/api/chat/list/{roomId}")
    public ResponseEntity<?> getChatList(@PathVariable Long roomId) {

        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅 목록이 성공적으로 조회되었습니다.",
                chatService.getChatList(roomId)
        ));
    }

    @ValidToken
    @GetMapping("/api/exist/chat/{targetId}")
    public ResponseEntity<?> existChatRoom(@PathVariable Long targetId) {
        Map<String, Boolean> response = new HashMap<>();
        if (chatService.getChatRoom(UserContext.userData.get().getUserId(), targetId) != null) {
            response.put("status", true);
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, response));
        }
        response.put("status", false);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, response));

    }

    @ValidToken
    @GetMapping("/api/exist/chat-alarm")
    public ResponseEntity<?> messageAlarm() {
        Map<String, Boolean> response = new HashMap<>();
        if (chatService.existChatMessage(UserContext.userData.get().getUserId())) {
            response.put("alarm", true);
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, response));
        }
        response.put("alarm", false);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, response));
    }
}
