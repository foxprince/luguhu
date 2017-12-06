package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.QUser;
import cn.anthony.luguhu.domain.User;
public interface UserRepository extends BaseRepository<User, QUser, Long> {
	@RestResource(path="email",rel="email")
	public User findByEmail(@Param("email") String email);
	
	public List<User> findByWxUserId(@Param("wxUser")Long wxUserId);

	public List<User> findByNameOrPhone(String name, String phone);

	public List<User> findByLevel(byte level);

	@Override
	default public void customize(QuerydslBindings bindings, QUser p) {
		bindings.bind(p.email).first((path, value) -> path.contains(value));
		bindings.excluding(p.password);
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

	public User findByWxUserOpenId(String openId);
}
