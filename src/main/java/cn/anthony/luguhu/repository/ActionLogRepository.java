package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cn.anthony.luguhu.domain.ActionLog;
import cn.anthony.luguhu.domain.QActionLog;

@RepositoryRestResource
public interface ActionLogRepository extends BaseRepository<ActionLog, QActionLog, Long> {

	@Query("select distinct title from ActionLog p order by p.title")
	List<String> findAllTitles();
}
