package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.ActionLog;
import cn.anthony.luguhu.domain.QActionLog;

@RepositoryRestResource
public interface ActionLogRepository extends BaseRepository<ActionLog, QActionLog, Long> {

	@Override
	default public void customize(QuerydslBindings bindings, QActionLog p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

	@Query("select distinct title from ActionLog p order by p.title")
	List<String> findAllTitles();
}
