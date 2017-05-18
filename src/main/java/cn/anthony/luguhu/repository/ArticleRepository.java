package cn.anthony.luguhu.repository;

import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.Article;
import cn.anthony.luguhu.domain.QArticle;

public interface ArticleRepository extends BaseRepository<Article, QArticle, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QArticle p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}
