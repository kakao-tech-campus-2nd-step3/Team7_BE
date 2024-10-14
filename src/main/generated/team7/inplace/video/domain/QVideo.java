package team7.inplace.video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideo is a Querydsl query type for Video
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideo extends EntityPathBase<Video> {

    private static final long serialVersionUID = -1693861724L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideo video = new QVideo("video");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final team7.inplace.influencer.domain.QInfluencer influencer;

    public final team7.inplace.place.domain.QPlace place;

    public final StringPath videoUrl = createString("videoUrl");

    public QVideo(String variable) {
        this(Video.class, forVariable(variable), INITS);
    }

    public QVideo(Path<? extends Video> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideo(PathMetadata metadata, PathInits inits) {
        this(Video.class, metadata, inits);
    }

    public QVideo(Class<? extends Video> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.influencer = inits.isInitialized("influencer") ? new team7.inplace.influencer.domain.QInfluencer(forProperty("influencer")) : null;
        this.place = inits.isInitialized("place") ? new team7.inplace.place.domain.QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

