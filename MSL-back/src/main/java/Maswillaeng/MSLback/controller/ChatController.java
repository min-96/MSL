package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.CreateRoomResponseDto;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.ChatService;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getChatRoomList(){
        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅방이 성공적으로 조회되었습니다.",
                chatService.getChatRoomList(UserContext.userData.get().getUserId())
        ));
    }

    @ValidToken
    @GetMapping("/api/chat/list/{roomId}")
    public ResponseEntity<?> getChatRoomList(@PathVariable Long roomId){
        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅 목록이 성공적으로 조회되었습니다.",
                chatService.getChatRoomList(UserContext.userData.get().getUserId())
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
