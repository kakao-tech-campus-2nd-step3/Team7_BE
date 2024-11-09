package team7.inplace.likedPlace.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikedPlace is a Querydsl query type for likedPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikedPlace extends EntityPathBase<LikedPlace> {

    private static final long serialVersionUID = 1786130640L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikedPlace likedPlace = new QLikedPlace("likedPlace");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isLiked = createBoolean("isLiked");

    public final team7.inplace.place.domain.QPlace place;

    public final team7.inplace.user.domain.QUser user;

    public QLikedPlace(String variable) {
        this(LikedPlace.class, forVariable(variable), INITS);
    }

    public QLikedPlace(Path<? extends LikedPlace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikedPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikedPlace(PathMetadata metadata, PathInits inits) {
        this(LikedPlace.class, metadata, inits);
    }

    public QLikedPlace(Class<? extends LikedPlace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new team7.inplace.place.domain.QPlace(
            forProperty("place"), inits.get("place")) : null;
        this.user =
            inits.isInitialized("user") ? new team7.inplace.user.domain.QUser(forProperty("user"))
                : null;
    }

}

