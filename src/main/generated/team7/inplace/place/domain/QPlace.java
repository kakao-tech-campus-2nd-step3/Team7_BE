package team7.inplace.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlace is a Querydsl query type for Place
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlace extends EntityPathBase<Place> {

    private static final long serialVersionUID = -969769948L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlace place = new QPlace("place");

    public final QAddress address;

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final QCoordinate coordinate;

    public final StringPath facility = createString("facility");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath menuImgUrl = createString("menuImgUrl");

    public final ListPath<Menu, QMenu> menus = this.<Menu, QMenu>createList("menus", Menu.class, QMenu.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> menuUpdatedAt = createDateTime("menuUpdatedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final ListPath<OffDay, QOffDay> offDays = this.<OffDay, QOffDay>createList("offDays", OffDay.class, QOffDay.class, PathInits.DIRECT2);

    public final ListPath<OpenTime, QOpenTime> openPeriods = this.<OpenTime, QOpenTime>createList("openPeriods", OpenTime.class, QOpenTime.class, PathInits.DIRECT2);

    public QPlace(String variable) {
        this(Place.class, forVariable(variable), INITS);
    }

    public QPlace(Path<? extends Place> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlace(PathMetadata metadata, PathInits inits) {
        this(Place.class, metadata, inits);
    }

    public QPlace(Class<? extends Place> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.coordinate = inits.isInitialized("coordinate") ? new QCoordinate(forProperty("coordinate")) : null;
    }

}

