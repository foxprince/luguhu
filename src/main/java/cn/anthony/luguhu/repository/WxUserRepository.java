package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.anthony.luguhu.domain.QWxUser;
import cn.anthony.luguhu.domain.WxUser;

public interface WxUserRepository extends BaseRepository<WxUser, QWxUser, Long> {
	public WxUser findByOpenId(String openId);

	@Query("select distinct wxUser from Asset asset join asset.wxUser wxUser where asset.createFrom='WECHAT'")
	List<WxUser> findDistinctWxUser();
}
