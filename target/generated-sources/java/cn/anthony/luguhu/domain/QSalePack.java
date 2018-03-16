package cn.anthony.luguhu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalePack is a Querydsl query type for SalePack
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSalePack extends EntityPathBase<SalePack> {

    private static final long serialVersionUID = 5338776L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSalePack salePack = new QSalePack("salePack");

    public final QGenericEntity _super = new QGenericEntity(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final QAsset asset;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.sql.Timestamp> ctime = _super.ctime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath intro = createString("intro");

    public final NumberPath<Integer> maxBatch = createNumber("maxBatch", Integer.class);

    public final NumberPath<Integer> minBatch = createNumber("minBatch", Integer.class);

    public final NumberPath<Integer> minPrice = createNumber("minPrice", Integer.class);

    public final QUser operator;

    public final StringPath packType = createString("packType");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath priceType = createString("priceType");

    public final DateTimePath<java.util.Date> saleBegin = createDateTime("saleBegin", java.util.Date.class);

    public final DateTimePath<java.util.Date> saleEnd = createDateTime("saleEnd", java.util.Date.class);

    public final ListPath<SaleUnit, QSaleUnit> saleUnits = this.<SaleUnit, QSaleUnit>createList("saleUnits", SaleUnit.class, QSaleUnit.class, PathInits.DIRECT2);

    public final StringPath shipPlace = createString("shipPlace");

    public final StringPath title = createString("title");

    public final StringPath visitLevel = createString("visitLevel");

    public QSalePack(String variable) {
        this(SalePack.class, forVariable(variable), INITS);
    }

    public QSalePack(Path<? extends SalePack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSalePack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSalePack(PathMetadata metadata, PathInits inits) {
        this(SalePack.class, metadata, inits);
    }

    public QSalePack(Class<? extends SalePack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.asset = inits.isInitialized("asset") ? new QAsset(forProperty("asset"), inits.get("asset")) : null;
        this.operator = inits.isInitialized("operator") ? new QUser(forProperty("operator"), inits.get("operator")) : null;
    }

}

