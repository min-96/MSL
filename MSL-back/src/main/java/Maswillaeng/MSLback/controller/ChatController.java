package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.dto.common.ChatMessageDto;
import Maswillaeng.MSLback.dto.common.CreateRoomResponseDto;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.ChatService;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @ValidToken
    @GetMapping("/api/exist/chat/{targetId}")
    public ResponseEntity<?> existChatRoom(@PathVariable Long targetId){
        Map<String,Boolean> response = new HashMap<>();
        if(chatService.getChatRoom(UserContext.userData.get().getUserId(),targetId) !=null){
            response.put("status",true);
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,response));
        }
        response.put("status",false);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,response));

    }

    @ValidToken
    @GetMapping("/api/exist/chat-alarm")
    public ResponseEntity<?> messageAlarm(){
        Map<String,Boolean> response = new HashMap<>();
        if(chatService.existChatMessage(UserContext.userData.get().getUserId())){
            response.put("alarm",true);
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,response));
        }
            response.put("alarm",false);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,response));
        }
;    }
