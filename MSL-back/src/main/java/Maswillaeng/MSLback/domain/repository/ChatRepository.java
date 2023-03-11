package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    @Query("select c from Chat c join fetch c.chatRoom cr where cr.id =:roomId and c.state =:false")
    List<Chat> findByChatRoom(@Param("roomId") Long roomId);


    @Query("select new Maswillaeng.MSLback.dto.common.ChatListDto()" +
            "from Chat c join c.chatRoom cr where cr.id =:roomId and c.state =:false group by c.state having count(c.state)")
    Object findByRoomIdAndState(@Param("roomId")Long id);
}
