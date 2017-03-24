package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.QSaleUnit;
import cn.anthony.luguhu.domain.SaleUnit;

public interface SaleUnitRepository extends BaseRepository<SaleUnit, QSaleUnit, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QSaleUnit p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

	public List<SaleUnit> findByProductId(Long productId);
}
