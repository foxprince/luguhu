package cn.anthony.luguhu.repository;

import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.Product;
import cn.anthony.luguhu.domain.QProduct;

public interface ProductRepository extends BaseRepository<Product, QProduct, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QProduct p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}
