package team7.inplace.influencer.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInfluencer is a Querydsl query type for Influencer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInfluencer extends EntityPathBase<Influencer> {

    private static final long serialVersionUID = 799784822L;

    public static final QInfluencer influencer = new QInfluencer("influencer");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath job = createString("job");

    public final StringPath name = createString("name");

    public QInfluencer(String variable) {
        super(Influencer.class, forVariable(variable));
    }

    public QInfluencer(Path<? extends Influencer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInfluencer(PathMetadata metadata) {
        super(Influencer.class, metadata);
    }

}

