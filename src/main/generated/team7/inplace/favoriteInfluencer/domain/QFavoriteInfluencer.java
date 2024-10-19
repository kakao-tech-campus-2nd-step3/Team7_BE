package team7.inplace.favoriteInfluencer.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteInfluencer is a Querydsl query type for FavoriteInfluencer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteInfluencer extends EntityPathBase<FavoriteInfluencer> {

    private static final long serialVersionUID = -1437439762L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteInfluencer favoriteInfluencer = new QFavoriteInfluencer("favoriteInfluencer");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final team7.inplace.influencer.domain.QInfluencer influencer;

    public final BooleanPath like = createBoolean("like");

    public final team7.inplace.user.domain.QUser user;

    public QFavoriteInfluencer(String variable) {
        this(FavoriteInfluencer.class, forVariable(variable), INITS);
    }

    public QFavoriteInfluencer(Path<? extends FavoriteInfluencer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteInfluencer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteInfluencer(PathMetadata metadata, PathInits inits) {
        this(FavoriteInfluencer.class, metadata, inits);
    }

    public QFavoriteInfluencer(Class<? extends FavoriteInfluencer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.influencer = inits.isInitialized("influencer") ? new team7.inplace.influencer.domain.QInfluencer(forProperty("influencer")) : null;
        this.user = inits.isInitialized("user") ? new team7.inplace.user.domain.QUser(forProperty("user")) : null;
    }

}

