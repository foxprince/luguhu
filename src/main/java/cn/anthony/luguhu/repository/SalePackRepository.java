package cn.anthony.luguhu.repository;

import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.QSalePack;
import cn.anthony.luguhu.domain.SalePack;

public interface SalePackRepository extends BaseRepository<SalePack, QSalePack, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QSalePack p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}
