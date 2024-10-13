package team7.inplace.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOpenTime is a Querydsl query type for OpenTime
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOpenTime extends BeanPath<OpenTime> {

    private static final long serialVersionUID = -535010310L;

    public static final QOpenTime openTime = new QOpenTime("openTime");

    public final StringPath dayOfWeek = createString("dayOfWeek");

    public final StringPath timeName = createString("timeName");

    public final StringPath timeSE = createString("timeSE");

    public QOpenTime(String variable) {
        super(OpenTime.class, forVariable(variable));
    }

    public QOpenTime(Path<? extends OpenTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOpenTime(PathMetadata metadata) {
        super(OpenTime.class, metadata);
    }

}

