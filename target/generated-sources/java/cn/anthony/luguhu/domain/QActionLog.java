package cn.anthony.luguhu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QActionLog is a Querydsl query type for ActionLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QActionLog extends EntityPathBase<ActionLog> {

    private static final long serialVersionUID = 479170710L;

    public static final QActionLog actionLog = new QActionLog("actionLog");

    public final QGenericEntity _super = new QGenericEntity(this);

    public final StringPath action = createString("action");

    //inherited
    public final DateTimePath<java.sql.Timestamp> ctime = _super.ctime;

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> operatorId = createNumber("operatorId", Long.class);

    public final NumberPath<Long> relateId = createNumber("relateId", Long.class);

    public final StringPath relateObject = createString("relateObject");

    public final StringPath selfDescription = createString("selfDescription");

    public final StringPath selfIntro = createString("selfIntro");

    public final StringPath title = createString("title");

    public QActionLog(String variable) {
        super(ActionLog.class, forVariable(variable));
    }

    public QActionLog(Path<? extends ActionLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActionLog(PathMetadata metadata) {
        super(ActionLog.class, metadata);
    }

}

