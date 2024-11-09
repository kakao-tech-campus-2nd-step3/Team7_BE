package team7.inplace.global.exception;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QErrorLog is a Querydsl query type for ErrorLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QErrorLog extends EntityPathBase<ErrorLog> {

    private static final long serialVersionUID = 50351046L;

    public static final QErrorLog errorLog = new QErrorLog("errorLog");

    public final StringPath errorMessage = createString("errorMessage");

    public final StringPath errorUrl = createString("errorUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isResolved = createBoolean("isResolved");

    public final StringPath stackTrace = createString("stackTrace");

    public QErrorLog(String variable) {
        super(ErrorLog.class, forVariable(variable));
    }

    public QErrorLog(Path<? extends ErrorLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QErrorLog(PathMetadata metadata) {
        super(ErrorLog.class, metadata);
    }

}

