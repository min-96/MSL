package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.dto.common.ChatMessageDto;
import Maswillaeng.MSLback.dto.common.CreateRoomResponseDto;
import Maswillaeng.MSLback.service.ChatService;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/api/create/chat-room/{targetId}")
    public ResponseEntity<?> createRoom(@PathVariable Long targetId){

        CreateRoomResponseDto chatRoom =  chatService.createRoom(UserContext.userData.get().getUserId(),targetId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/chat-room/list")
    public ResponseEntity<?> chatRoomList(){
        chatService.findAllChatRoom(UserContext.userData.get().getUserId());
        return ResponseEntity.ok().build();
    }

//    @MessageMapping("/chat/{roomId}")
//    @SendTo("/sub/chat/{roomId}")
//    public ResponseEntity<?> chat(@DestinationVariable Long roomId, ChatMessageDto message){
//        chatService.saveMessage(roomId,message);
//
//        return ResponseEntity.ok().build();
//    }

}
