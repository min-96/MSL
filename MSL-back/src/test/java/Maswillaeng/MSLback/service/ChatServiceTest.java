package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.domain.entity.ChatRoom;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.ChatRepository;
import Maswillaeng.MSLback.domain.repository.ChatRoomRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.common.ChatRoomResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRepository chatRepository;


    @Test
    @Transactional
    void 채팅방_전체조회_테스트() {
        //given
        User user1 = User.builder()
                .email("test1")
                .password("test1")
                .nickName("tes1")
                .build();
        User user2 = User.builder()
                .email("test2")
                .password("test2")
                .nickName("tet2")
                .build();

        ChatRoom chatRoom = ChatRoom.builder()
                .owner(user1)
                .invited(user2)
                .build();

        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .senderId(user1.getId())
                .recipientId(user2.getId())
                .content("hello")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        chatRoomRepository.save(chatRoom);
        chatRepository.save(chat);

        //when
        List<ChatRoomResponseDto> result = chatService.findAllChatRoom(user1.getId());

        //then
        assertThat(result.size()).isEqualTo(1);
        ChatRoomResponseDto chatRoomDto = result.get(0);
        assertThat(chatRoomDto.getChatRoomId()).isEqualTo(chatRoom.getId());
        assertThat(chatRoomDto.getNickName()).isEqualTo(user2.getNickName());
        assertThat(chatRoomDto.getUserImage()).isEqualTo(user2.getUserImage());
        assertThat(chatRoomDto.getUnReadMsgCnt()).isEqualTo(1);
    }

}