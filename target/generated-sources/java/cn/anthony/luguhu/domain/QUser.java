package cn.anthony.luguhu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1970154653L;

    public static final QUser user = new QUser("user");

    public final QGenericEntity _super = new QGenericEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath address = createString("address");

    public final NumberPath<Byte> age = createNumber("age", Byte.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> ctime = _super.ctime;

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.util.Date> lastLogin = createDateTime("lastLogin", java.util.Date.class);

    public final NumberPath<Byte> level = createNumber("level", Byte.class);

    public final NumberPath<Byte> loginType = createNumber("loginType", Byte.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath origin = createString("origin");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final NumberPath<Byte> sex = createNumber("sex", Byte.class);

    public final BooleanPath verified = createBoolean("verified");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

