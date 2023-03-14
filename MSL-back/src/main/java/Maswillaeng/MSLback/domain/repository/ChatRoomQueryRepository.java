package Maswillaeng.MSLback.domain.repository;


import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.domain.entity.ChatRoom;
import Maswillaeng.MSLback.domain.entity.QChat;
import Maswillaeng.MSLback.dto.common.ChatRoomResponseDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static Maswillaeng.MSLback.domain.entity.QChat.chat;
import static Maswillaeng.MSLback.domain.entity.QChatRoom.chatRoom;
import static Maswillaeng.MSLback.domain.entity.QUser.user;

@Repository
public class ChatRoomQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ChatRoomQueryRepository(JPAQueryFactory queryFactory) {
        super(ChatRoom.class);
        this.queryFactory = queryFactory;
    }

    public List<ChatRoomResponseDto> findAllByUserId(Long userId) {
        return queryFactory
                .select(Projections.bean(ChatRoomResponseDto.class,
                        chatRoom.id.as("chatRoomId"),
                        user.id.as("partnerId"),
                        user.nickName,
                        user.userImage,
                        chat.count().as("unReadMsgCnt")
                ))
                .from(chatRoom)
                .leftJoin(chat).on(chat.chatRoom.eq(chatRoom)
                        .and(chat.recipientId.eq(userId))
                        .and(chat.state.eq(false)))
                .leftJoin(user).on(user.id.eq(chatRoom.owner.id)
                        .or(user.id.eq(chatRoom.invited.id))
                        .and(user.id.ne(userId)))
                .where(chatRoom.invited.id.eq(userId).or(chatRoom.owner.id.eq(userId)))
                .groupBy(chatRoom.id)
                .orderBy(chatRoom.id.asc())
                .fetch();
    }
}
