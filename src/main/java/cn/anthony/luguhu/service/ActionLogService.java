package cn.anthony.luguhu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.ActionLog;
import cn.anthony.luguhu.domain.GenericEntity;
import cn.anthony.luguhu.repository.ActionLogRepository;
import cn.anthony.luguhu.repository.BaseRepository;

@Service
public class ActionLogService extends GenericService<ActionLog, Long> {

	@Resource
	ActionLogRepository repsitory;

	@Override
	public BaseRepository getRepository() {
		return this.repsitory;
	}

	public ActionLog add(String relateId, String relateObject, String action, String title, String description, Long operatorId) {
		ActionLog entity = new ActionLog(relateId, relateObject, action, title, description, operatorId);
		return repsitory.save(entity);
	}

	public ActionLog add(GenericEntity t, String action, String title, String description, Long operatorId) {
		ActionLog entity = new ActionLog(t.getId().toString(), t.getClass().getName(), action + "_" + t.getClass().getSimpleName(), title,
				description, operatorId);
		return repsitory.save(entity);
	}

	public List<String> findAllTitles() {
		return repsitory.findAllTitles();
	}
}
