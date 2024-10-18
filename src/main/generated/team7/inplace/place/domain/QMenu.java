package team7.inplace.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMenu extends BeanPath<Menu> {

    private static final long serialVersionUID = 1908284066L;

    public static final QMenu menu = new QMenu("menu");

    public final StringPath description = createString("description");

    public final StringPath menuImgUrl = createString("menuImgUrl");

    public final StringPath menuName = createString("menuName");

    public final StringPath price = createString("price");

    public final BooleanPath recommend = createBoolean("recommend");

    public QMenu(String variable) {
        super(Menu.class, forVariable(variable));
    }

    public QMenu(Path<? extends Menu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenu(PathMetadata metadata) {
        super(Menu.class, metadata);
    }

}

