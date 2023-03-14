package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.dto.common.ChatMessageDto;
import Maswillaeng.MSLback.dto.common.ChatRoomResponseDto;
import Maswillaeng.MSLback.dto.common.CreateRoomResponseDto;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.ChatService;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @ValidToken
    @PostMapping("/api/create/chat-room/{targetId}")
    public ResponseEntity<?> createRoom(@PathVariable Long targetId){

        CreateRoomResponseDto chatRoom =  chatService.createRoom(UserContext.userData.get().getUserId(),targetId);
        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅방이 성공적으로 생성되었습니다.",
                chatRoom
        ));
    }

    @ValidToken
    @GetMapping("/api/chat-room/list")
    public ResponseEntity<?> chatRoomList(){
        List<ChatRoomResponseDto> chatRoomList = chatService.findAllChatRoom(UserContext.userData.get().getUserId());
        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅방이 성공적으로 조회되었습니다.",
                    chatRoomList
        ));
    }

//    @MessageMapping("/chat/{roomId}")
//    @SendTo("/sub/chat/{roomId}")
//    public ResponseEntity<?> chat(@DestinationVariable Long roomId, ChatMessageDto message){
//        chatService.saveMessage(roomId,message);
//
//        return ResponseEntity.ok().build();
//    }

}
