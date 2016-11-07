package cn.anthony.luguhu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGenericEntity is a Querydsl query type for GenericEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QGenericEntity extends EntityPathBase<GenericEntity> {

    private static final long serialVersionUID = -1182728190L;

    public static final QGenericEntity genericEntity = new QGenericEntity("genericEntity");

    public final DateTimePath<java.sql.Timestamp> ctime = createDateTime("ctime", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QGenericEntity(String variable) {
        super(GenericEntity.class, forVariable(variable));
    }

    public QGenericEntity(Path<? extends GenericEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGenericEntity(PathMetadata metadata) {
        super(GenericEntity.class, metadata);
    }

}

