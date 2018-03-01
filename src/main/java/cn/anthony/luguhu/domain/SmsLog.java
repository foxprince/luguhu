package cn.anthony.luguhu.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
@QueryEntity
@Entity
public class SmsLog extends GenericEntity {

	private String channel,msgId,phone,code,msg,sendStatus,reportStatus;
	private boolean used;
	private Timestamp sendTime,reportTime;
}
