package cn.anthony.luguhu.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Pageable;

import cn.anthony.luguhu.domain.QSmsLog;
import cn.anthony.luguhu.domain.SmsLog;

public interface SmsLogRepository extends BaseRepository<SmsLog, QSmsLog, Long> {
	public SmsLog findByPhone(String s);
	public SmsLog findByPhoneAndCodeAndUsedAndCtimeGt(String phone,String code,boolean used,Timestamp validTime);
	public List<SmsLog> findByPhone(String phone,Pageable pageable);
}
