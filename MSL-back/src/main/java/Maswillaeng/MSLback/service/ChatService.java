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
        // 현재 user가 targetuser이 있는 채팅방이 있는지 예외처리
        if(getChatRoom(userId,targetId).equals(null)){
            throw new  IllegalStateException("이미 채팅방이 존재합니다");
        }
        ChatRoom createRoom = chatRoomRepository.save(chatRoom);
         return new CreateRoomResponseDto(createRoom);
    }


    public void findAllChatRoom(Long userId) {
       List<ChatRoom> chatRoomList = chatRoomRepository.findAllChatRoom(userId);
        // false 고 roomId ;
       // chatRoomList.stream().map(chatRoom ->  chatRepository.findByRoomIdAndState(chatRoom.getId())).toList();

        // 안읽은 메세지가 몇개인지 확인
        //Map 으로 줘야하나 ? 아니면 dto 로 ?
    }

    public ChatResponseDto saveMessage(ChatMessageDto chat) {

      //  ChatRoom chatRoom =chatRoomRepository.findByOwnerAndInvited(chat.getSenderUserId(),chat.getDestinationUserId());
       ChatRoom chatRoom =  getChatRoom(chat.getSenderUserId(),chat.getDestinationUserId());
        //fetch join 으로 한번에 가져올수있는데 sender/ recipient 가 owner에속하는지 Invited에 속하는지를 모름 ㅠ
        User senderUser =userRepository.findById(chat.getSenderUserId()).get();
        User recipientUser = userRepository.findById(chat.getDestinationUserId()).get();

        Chat chatMessage =  Chat.builder().chatRoom(chatRoom).sender(senderUser.getNickName()).recipient(recipientUser.getNickName()).content(chat.getContent()).state(false).build();
        Chat chatResponse = chatRepository.save(chatMessage);
        return new ChatResponseDto(chatResponse);
    }

    public void stateUpdate(Long roomId) {

       List<Chat> beforeState = chatRepository.findByChatRoom(roomId);
       // 어디까지 읽었는지 체크 chatId 가져오기
       beforeState.stream().forEach(state -> state.updateState());
//       return ChatResponseDto.;
    }

    public ChatRoom getChatRoom(Long senderId , Long recipientId){
        return chatRoomRepository.findByOwnerAndInvited(senderId,recipientId);
    }

    // 채팅 알림 몇개 왔는지?
    // 채팅방마다 알림 몇개 왓는지?
    // 쌓인 메세지도 보여주기


}
