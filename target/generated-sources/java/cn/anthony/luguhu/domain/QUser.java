package cn.anthony.luguhu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1970154653L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final QGenericEntity _super = new QGenericEntity(this);

    public final QUserAccount account;

    public final BooleanPath active = createBoolean("active");

    public final ListPath<Address, QAddress> addresses = this.<Address, QAddress>createList("addresses", Address.class, QAddress.class, PathInits.DIRECT2);

    public final NumberPath<Byte> age = createNumber("age", Byte.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> ctime = _super.ctime;

    public final ListPath<UserDeposit, QUserDeposit> depositList = this.<UserDeposit, QUserDeposit>createList("depositList", UserDeposit.class, QUserDeposit.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.util.Date> lastLogin = createDateTime("lastLogin", java.util.Date.class);

    public final StringPath level = createString("level");

    public final StringPath loginType = createString("loginType");

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath origin = createString("origin");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath sex = createString("sex");

    public final BooleanPath verified = createBoolean("verified");

    public final QWxUser wxUser;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QUserAccount(forProperty("account"), inits.get("account")) : null;
        this.wxUser = inits.isInitialized("wxUser") ? new QWxUser(forProperty("wxUser")) : null;
    }

}

