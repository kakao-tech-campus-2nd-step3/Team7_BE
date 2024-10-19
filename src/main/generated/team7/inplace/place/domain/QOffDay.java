package team7.inplace.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOffDay is a Querydsl query type for OffDay
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOffDay extends BeanPath<OffDay> {

    private static final long serialVersionUID = -32148432L;

    public static final QOffDay offDay = new QOffDay("offDay");

    public final StringPath holidayName = createString("holidayName");

    public final StringPath temporaryHolidays = createString("temporaryHolidays");

    public final StringPath weekAndDay = createString("weekAndDay");

    public QOffDay(String variable) {
        super(OffDay.class, forVariable(variable));
    }

    public QOffDay(Path<? extends OffDay> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOffDay(PathMetadata metadata) {
        super(OffDay.class, metadata);
    }

}

