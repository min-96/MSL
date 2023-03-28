package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.AlreadyExistException;
import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.domain.entity.ChatRoom;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.ChatRepository;
import Maswillaeng.MSLback.domain.repository.ChatRoomQueryRepository;
import Maswillaeng.MSLback.domain.repository.ChatRoomRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.chat.request.ChatMessageDto;
import Maswillaeng.MSLback.dto.chat.response.ChatMessageListResponseDto;
import Maswillaeng.MSLback.dto.chat.response.ChatResponseDto;
import Maswillaeng.MSLback.dto.chat.response.ChatRoomResponseDto;
import Maswillaeng.MSLback.dto.chat.response.CreateRoomResponseDto;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;
    private final ChatRoomQueryRepository chatRoomQueryRepository;

    @Transactional
    public CreateRoomResponseDto createRoom(Long userId, Long targetId) {
        User user = userRepository.findById(userId).get();
        User targetUser = userRepository.findById(targetId).orElseThrow(
                () -> new EntityNotFoundException(User.class.getSimpleName()));
        ChatRoom chatRoom = new ChatRoom(user, targetUser);
        if (getChatRoom(userId, targetId) != null) {
            throw new AlreadyExistException(ChatRoom.class.getSimpleName());
        }
        ChatRoom createRoom = chatRoomRepository.save(chatRoom);
        return new CreateRoomResponseDto(createRoom);
    }


    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> getChatRoomList(Long userId) {
        return chatRoomQueryRepository.findAllByUserId(userId);
    }

    @Transactional
    public ChatResponseDto saveMessage(ChatMessageDto chat) {
        ChatRoom chatRoom = getChatRoom(chat.getSenderId(), chat.getRecipientId());
        Chat chatMessage = Chat.builder().id(chat.getChatId()).chatRoom(chatRoom).senderId(chat.getSenderId()).recipientId(chat.getRecipientId()).content(chat.getContent()).state(false).build();
        Chat chatResponse = chatRepository.save(chatMessage);
        return new ChatResponseDto(chatResponse);
    }

    @Transactional
    public ChatMessageListResponseDto getChatList(Long roomId) {
        Long userId = UserContext.userData.get().getUserId();
        chatRepository.updateStateByRoomIdAndUserId(roomId, userId);
        List<ChatMessageDto> chatMessageDtoList = chatRepository.findAllByChatRoomId(roomId)
                .stream().map(ChatMessageDto::new).toList();
        User partner = userRepository.findPartnerByRoomIdAndUserId(roomId, userId);
        return new ChatMessageListResponseDto(partner, chatMessageDtoList);
    }

    @Transactional
    public boolean stateUpdate(Long chatId) {
        chatRepository.updateState(chatId);
        return true;
    }

    public ChatRoom getChatRoom(Long senderId, Long recipientId) {
        return chatRoomRepository.findByOwnerAndInvited(senderId, recipientId);
    }

    @Transactional(readOnly = true)
    public boolean existChatMessage(Long userId) {
        return chatRepository.findByChatMessage(userId) != null;
    }

}
