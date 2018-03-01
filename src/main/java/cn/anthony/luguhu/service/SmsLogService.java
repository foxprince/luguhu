package cn.anthony.luguhu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.QSmsLog;
import cn.anthony.luguhu.domain.SmsLog;
import cn.anthony.luguhu.repository.BaseRepository;
import cn.anthony.luguhu.repository.SmsLogRepository;

@Service
public class SmsLogService extends GenericService<SmsLog, Long> {
	@Resource
	SmsLogRepository repsitory;

	@Override
	public BaseRepository<SmsLog, QSmsLog, Long> getRepository() {
		return this.repsitory;
	}
}
