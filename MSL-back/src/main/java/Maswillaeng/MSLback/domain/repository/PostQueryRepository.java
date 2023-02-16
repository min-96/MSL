package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.*;
import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static Maswillaeng.MSLback.domain.entity.QComment.*;
import static Maswillaeng.MSLback.domain.entity.QPost.*;
import static Maswillaeng.MSLback.domain.entity.QPostLike.*;
import static Maswillaeng.MSLback.domain.entity.QUser.*;

@Repository
public class PostQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public PostQueryRepository(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    public List<PostResponseDto> findAllPostByCategory(Category category) {
        JPAQuery<PostResponseDto> query = queryFactory
                .select(Projections.bean(PostResponseDto.class,
                        post.id.as("postId"), user.id.as("userId"),
                        user.nickName, user.userImage, post.thumbnail, post.title,
                        post.content, post.createdAt, post.modifiedAt,
                        postLike.count().as("likeCnt"), comment.count().as("commentCnt"), post.hits))
                .from(post)
                .join(post.user, user)
                .leftJoin(post.commentList, comment)
                .leftJoin(post.postLikeList, postLike)
                .groupBy(post.id)
                .orderBy(post.createdAt.desc())
                .limit(500);

        if (category != null) {
            query.where(post.category.eq(category));
        }

        return query.fetch();
    };

}

