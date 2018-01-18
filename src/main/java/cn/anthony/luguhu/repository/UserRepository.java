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
	
	public List<User> findByWxUserId(@Param("wxUserId")Long wxUserId);

	public List<User> findByNameOrPhone(@Param("name")String name, @Param("phone")String phone);

	public List<User> findByLevel(@Param("level")byte level);

	@Override
	default public void customize(QuerydslBindings bindings, QUser p) {
		bindings.bind(p.email).first((path, value) -> path.contains(value));
		bindings.excluding(p.password);
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
	@RestResource(path="openId",rel="openId")
	public User findByWxUserOpenId(@Param("openId")String openId);
}
