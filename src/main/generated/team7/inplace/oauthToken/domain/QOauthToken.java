package team7.inplace.oauthToken.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOauthToken is a Querydsl query type for OauthToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOauthToken extends EntityPathBase<OauthToken> {

    private static final long serialVersionUID = -59028672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOauthToken oauthToken1 = new QOauthToken("oauthToken1");

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath oauthToken = createString("oauthToken");

    public final team7.inplace.user.domain.QUser user;

    public QOauthToken(String variable) {
        this(OauthToken.class, forVariable(variable), INITS);
    }

    public QOauthToken(Path<? extends OauthToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOauthToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOauthToken(PathMetadata metadata, PathInits inits) {
        this(OauthToken.class, metadata, inits);
    }

    public QOauthToken(Class<? extends OauthToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new team7.inplace.user.domain.QUser(forProperty("user")) : null;
    }

}

