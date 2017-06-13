package cn.anthony.luguhu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSaleUnit is a Querydsl query type for SaleUnit
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSaleUnit extends EntityPathBase<SaleUnit> {

    private static final long serialVersionUID = 5500419L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSaleUnit saleUnit = new QSaleUnit("saleUnit");

    public final QGenericEntity _super = new QGenericEntity(this);

    public final QAsset asset;

    //inherited
    public final DateTimePath<java.sql.Timestamp> ctime = _super.ctime;

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Short> minBatch = createNumber("minBatch", Short.class);

    public final QUser operator;

    public final NumberPath<Float> price = createNumber("price", Float.class);

    public final QProduct product;

    public final BooleanPath saleable = createBoolean("saleable");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public final StringPath unit = createString("unit");

    public QSaleUnit(String variable) {
        this(SaleUnit.class, forVariable(variable), INITS);
    }

    public QSaleUnit(Path<? extends SaleUnit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSaleUnit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSaleUnit(PathMetadata metadata, PathInits inits) {
        this(SaleUnit.class, metadata, inits);
    }

    public QSaleUnit(Class<? extends SaleUnit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.asset = inits.isInitialized("asset") ? new QAsset(forProperty("asset"), inits.get("asset")) : null;
        this.operator = inits.isInitialized("operator") ? new QUser(forProperty("operator"), inits.get("operator")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

