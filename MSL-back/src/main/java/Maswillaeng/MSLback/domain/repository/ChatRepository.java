package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat,Long> {

//    @Query("select c from Chat c join fetch c.chatRoom cr where cr.id =:roomId and c.state =false")
//    List<Chat> findByChatRoom(@Param("roomId") Long roomId);

    @Query("select c from Chat c where c.recipientId =:userId and c.state =false")
    Chat findByChatMessage(@Param("userId") Long userId);


    @Modifying(clearAutomatically = true)
    @Query("update Chat c set c.state = true where c.chatRoom.id = :roomId and c.state = false")
    void updateState(@Param("roomId") Long roomId);

    @Modifying(clearAutomatically = true)
    @Query("update Chat c set c.state = true where c.chatRoom.id = :roomId and not c.senderId = :userId and c.state = false")
    void updateStateByRoomIdAndUserId(@Param("roomId") Long roomId, @Param("userId") Long userId);

    @Query ("select c from Chat c where c.chatRoom.id = :roomId order by c.createdAt desc ")
    List<Chat> findAllByChatRoomId(@Param("roomId") Long roomId);

    @Query("SELECT c FROM Chat c " +
            "WHERE c.id IN " +
            "(SELECT MAX(c2.id) FROM Chat c2 WHERE c.chatRoom.id = c2.chatRoom.id) " +
            "AND (c.chatRoom.owner.id = :userId OR c.chatRoom.invited.id = :userId) " +
            "GROUP BY c.chatRoom.id " +
            "ORDER BY c.chatRoom.id ASC")
    List<Chat> findLastChatByUserId(@Param("userId") Long userId);

//    @Query("select new Maswillaeng.MSLback.dto.common.ChatListDto()" +
//            "from Chat c join c.chatRoom cr where cr.id =:roomId and c.state = false group by c.state having count(c.state)")
//    Object findByRoomIdAndState(@Param("roomId")Long id);
//
//
//    @Query("select new Maswillaeng.MSLback.dto.common.ChatListDto(c.chatRoom.id, c.recipient, count(c.chatRoom.id)) from Chat c where c.chatRoom.id = :roomId and c.state = false and c.recipient = :userId group by c.chatRoom.id")
//    List<ChatListDto> findByRoomIdAndState(@Param("roomId")Long id , @Param("userId")Long userId);
}
