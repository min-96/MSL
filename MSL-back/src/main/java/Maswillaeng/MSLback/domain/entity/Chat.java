package Maswillaeng.MSLback.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Chat {

    @Id
    @Column(name = "chat_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 채팅방이 사라지면 채팅 데이터도 사라지는가?

    private Long senderId;

    private Long recipientId; // ??  room 으로 브로드캐스트 하는데 굳이?

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @ColumnDefault("false")
    private boolean state;


    @Builder
    public Chat(ChatRoom chatRoom, Long senderId, Long recipientId, String content, LocalDateTime createdAt, boolean state) {
        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.createdAt = createdAt;
        this.state = state;
    }

    public void changeState() {
        this.state = true;
    }
}
