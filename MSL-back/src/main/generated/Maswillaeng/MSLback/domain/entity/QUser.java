package Maswillaeng.MSLback.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1654011693L;

    public static final QUser user = new QUser("user");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final SetPath<Follow, QFollow> followerList = this.<Follow, QFollow>createSet("followerList", Follow.class, QFollow.class, PathInits.DIRECT2);

    public final SetPath<Follow, QFollow> followingList = this.<Follow, QFollow>createSet("followingList", Follow.class, QFollow.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final SetPath<Post, QPost> postList = this.<Post, QPost>createSet("postList", Post.class, QPost.class, PathInits.DIRECT2);

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<Maswillaeng.MSLback.domain.enums.RoleType> role = createEnum("role", Maswillaeng.MSLback.domain.enums.RoleType.class);

    public final StringPath userImage = createString("userImage");

    public final DateTimePath<java.time.LocalDateTime> withdrawAt = createDateTime("withdrawAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> withdrawYn = createNumber("withdrawYn", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

