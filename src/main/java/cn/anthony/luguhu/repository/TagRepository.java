package cn.anthony.luguhu.repository;

import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.QTag;
import cn.anthony.luguhu.domain.Tag;

public interface TagRepository extends BaseRepository<Tag, QTag, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QTag p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

	public Tag findByLabel(String s);
	
}
