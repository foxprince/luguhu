package cn.anthony.luguhu.repository;

import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.Asset;
import cn.anthony.luguhu.domain.QAsset;

public interface AssetRepository extends BaseRepository<Asset, QAsset, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QAsset p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}
