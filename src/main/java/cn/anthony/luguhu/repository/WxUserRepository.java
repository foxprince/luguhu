package cn.anthony.luguhu.repository;

import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.QWxUser;
import cn.anthony.luguhu.domain.WxUser;

public interface WxUserRepository extends BaseRepository<WxUser, QWxUser, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QWxUser p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
