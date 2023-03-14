package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.dto.common.ChatListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {

//    @Query("select c from Chat c join fetch c.chatRoom cr where cr.id =:roomId and c.state =false")
//    List<Chat> findByChatRoom(@Param("roomId") Long roomId);

    @Query("select c from Chat c where c.recipient =:userId and c.state =false")
    Chat findByChatMessage(@Param("userId") Long userId);


    @Modifying(clearAutomatically = true)
    @Query("update Chat c set c.state = true where c.chatRoom.id = :roomId and c.state = false")
    void findByChatRoom(@Param("roomId") Long roomId);

//    @Query("select new Maswillaeng.MSLback.dto.common.ChatListDto()" +
//            "from Chat c join c.chatRoom cr where cr.id =:roomId and c.state = false group by c.state having count(c.state)")
//    Object findByRoomIdAndState(@Param("roomId")Long id);
//
//
//    @Query("select new Maswillaeng.MSLback.dto.common.ChatListDto(c.chatRoom.id, c.recipient, count(c.chatRoom.id)) from Chat c where c.chatRoom.id = :roomId and c.state = false and c.recipient = :userId group by c.chatRoom.id")
//    List<ChatListDto> findByRoomIdAndState(@Param("roomId")Long id , @Param("userId")Long userId);
}
