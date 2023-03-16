package Maswillaeng.MSLback.domain.repository;


import Maswillaeng.MSLback.domain.entity.ChatRoom;
import Maswillaeng.MSLback.dto.chat.response.ChatRoomResponseDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
        Expression<Long> unReadMsgCntSubQuery = JPAExpressions
                .select(chat.count())
                .from(chat)
                .where(chat.chatRoom.eq(chatRoom),
                        chat.recipientId.eq(userId),
                        chat.state.eq(false));
        JPQLQuery<String> lastMessageSubQuery = JPAExpressions.select(chat.content)
                .from(chat)
                .where(chat.chatRoom.eq(chatRoom)
                        .and(chat.createdAt.eq(
                                        JPAExpressions.select(
                                                        chat.createdAt.max())
                                                .from(chat)
                                                .where(chat.chatRoom.eq(chatRoom))
                                )
                        ))
                .orderBy(chat.createdAt.desc())
                .limit(1);

        JPQLQuery<LocalDateTime> lastMessageTimeSubQuery = JPAExpressions.select(chat.createdAt)
                .from(chat)
                .where(chat.chatRoom.eq(chatRoom)
                        .and(chat.createdAt.eq(
                                        JPAExpressions.select(
                                                        chat.createdAt.max())
                                                .from(chat)
                                                .where(chat.chatRoom.eq(chatRoom))
                                )
                        )).orderBy(chat.createdAt.desc())
                .limit(1);

        return queryFactory
                .select(Projections.bean(ChatRoomResponseDto.class,
                        chatRoom.id.as("chatRoomId"),
                        user.id.as("partnerId"),
                        user.nickName,
                        user.userImage,
                        ExpressionUtils.as(unReadMsgCntSubQuery, "unReadMsgCnt"),
                        ExpressionUtils.as(lastMessageSubQuery, "lastMessage"),
                        ExpressionUtils.as(lastMessageTimeSubQuery, "lastMessageTime")
                ))
                .from(chatRoom)
                .leftJoin(user).on(user.id.eq(chatRoom.owner.id)
                        .or(user.id.eq(chatRoom.invited.id))
                        .and(user.id.ne(userId)))
                .leftJoin(chat).on(chat.chatRoom.eq(chatRoom))
                .where(chatRoom.invited.id.eq(userId).or(chatRoom.owner.id.eq(userId)))
                .groupBy(chatRoom.id, user.id)
                .orderBy(chatRoom.id.asc())
                .fetch();
    }
}
