package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.Asset;
import cn.anthony.luguhu.domain.QAsset;
import cn.anthony.luguhu.domain.WxUser;

public interface AssetRepository extends BaseRepository<Asset, QAsset, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QAsset p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

	public List<Asset> findByWxUserAndTagsNotNull(WxUser wxUser,Pageable pageable);
	
}
