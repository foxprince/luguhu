package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.QWxUser;
import cn.anthony.luguhu.domain.WxUser;

public interface WxUserRepository extends BaseRepository<WxUser, QWxUser, Long> {
	public WxUser findByOpenId(String openId);

	@Override
	default public void customize(QuerydslBindings bindings, QWxUser p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

	@Query("select distinct wxUser from Asset asset join asset.wxUser wxUser where asset.createFrom='WECHAT'")
	List<WxUser> findDistinctWxUser();
}
