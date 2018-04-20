package cn.anthony.luguhu.repository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.QUser;
import cn.anthony.luguhu.domain.User;

public interface UserRepository extends BaseRepository<User, QUser, Long> {
	@RestResource(path = "email", rel = "email")
	public User findByEmail(@Param("email") String email);

	public List<User> findByWxUserId(@Param("wxUserId") Long wxUserId);

	public List<User> findByNameOrPhone(@Param("name") String name, @Param("phone") String phone);

	public List<User> findByLevel(@Param("level") byte level);

	@Override
	default void customize(QuerydslBindings bindings, QUser p) {
		bindings.bind(p.loginType).first((path, value) -> {
			List<String> l = Arrays.asList(value);
			BooleanExpression b = path.isNotNull();
			for (String s : l) {
				b.or(path.eq(s));
			}
			return b;
			// return path.in(l);
		});
		
		bindings.bind(p.email).first((path, value) -> path.contains(value));
		bindings.excluding(p.password);
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
		
		bindings.bind(p.ctime).first((path, value) -> {
			List<Timestamp> l = Arrays.asList(value);
			return path.between(l.get(0), l.get(1));
		});
	}

	// @Override
	default public void customize2(QuerydslBindings bindings, QUser p) {
		//bindings.bind(p.ctime).all((path, value) -> path.in(value));
		// bindings.bind(p.email).first((path, value) -> path.contains(value));
		// bindings.excluding(p.password);
		// bindings.bind(String.class).first((StringPath path, String value) ->
		// path.containsIgnoreCase(value));
		// bindings.bind(p.loginType).all((path, value) -> {
		// return path.in(value);
		// return value.contains(path);
		// Iterator<? extends BigDecimal> it = value.iterator();
		// BigDecimal from = it.next();
		// if (value.size() >= 2) {
		// BigDecimal to = it.next();
		// return path.between(from, to); // between - if you specify
		// heightMeters two times
		// } else {
		// return path.goe(from); // or greter than - if you specify
		// heightMeters one time
		// }
		// });
	}

	@RestResource(path = "openId", rel = "openId")
	public User findByWxUserOpenId(@Param("openId") String openId);
}
