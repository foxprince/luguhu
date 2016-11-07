package cn.anthony.luguhu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPackUnit is a Querydsl query type for PackUnit
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPackUnit extends EntityPathBase<PackUnit> {

    private static final long serialVersionUID = -1180084523L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPackUnit packUnit = new QPackUnit("packUnit");

    public final QGenericEntity _super = new QGenericEntity(this);

    public final NumberPath<Short> amount = createNumber("amount", Short.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> ctime = _super.ctime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QSaleUnit saleUnit;

    public final StringPath selfDescription = createString("selfDescription");

    public final StringPath selfIntro = createString("selfIntro");

    public QPackUnit(String variable) {
        this(PackUnit.class, forVariable(variable), INITS);
    }

    public QPackUnit(Path<? extends PackUnit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPackUnit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPackUnit(PathMetadata metadata, PathInits inits) {
        this(PackUnit.class, metadata, inits);
    }

    public QPackUnit(Class<? extends PackUnit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.saleUnit = inits.isInitialized("saleUnit") ? new QSaleUnit(forProperty("saleUnit"), inits.get("saleUnit")) : null;
    }

}

