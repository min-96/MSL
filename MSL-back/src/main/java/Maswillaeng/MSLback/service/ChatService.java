package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.domain.entity.ChatRoom;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.ChatRepository;
import Maswillaeng.MSLback.domain.repository.ChatRoomRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.common.ChatMessageDto;
import Maswillaeng.MSLback.dto.common.ChatResponseDto;
import Maswillaeng.MSLback.dto.common.CreateRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    public CreateRoomResponseDto createRoom(Long userId, Long targetId) {
        User user = userRepository.findById(userId).get();
        User targetUser = userRepository.findById(targetId).orElseThrow(
                () -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        ChatRoom chatRoom = new ChatRoom(user,targetUser);
        if(getChatRoom(userId,targetId).equals(null)){
            throw new  IllegalStateException("이미 채팅방이 존재합니다");
        }
        ChatRoom createRoom = chatRoomRepository.save(chatRoom);
         return new CreateRoomResponseDto(createRoom);
    }


    public void findAllChatRoom(Long userId) {

    }

    public ChatResponseDto saveMessage(ChatMessageDto chat) {
        ChatRoom chatRoom =  getChatRoom(chat.getSenderUserId(),chat.getDestinationUserId());
        User senderUser =userRepository.findById(chat.getSenderUserId()).get();
        User recipientUser = userRepository.findById(chat.getDestinationUserId()).get();

        Chat chatMessage =  Chat.builder().chatRoom(chatRoom).sender(senderUser.getNickName()).recipient(recipientUser.getNickName()).content(chat.getContent()).state(false).build();
        Chat chatResponse = chatRepository.save(chatMessage);
        return new ChatResponseDto(chatResponse);
    }

    public boolean stateUpdate(Long roomId) {
        chatRepository.findByChatRoom(roomId);
        return true;
    }

    public ChatRoom getChatRoom(Long senderId , Long recipientId){
        return chatRoomRepository.findByOwnerAndInvited( senderId,recipientId);
    }


    public boolean existChatMessage(Long userId) {
        if (chatRepository.findByChatMessage(userId) != null) return true;
        else return false;
    }
}
