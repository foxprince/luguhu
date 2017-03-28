package cn.anthony.luguhu.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@QueryEntity
@Entity
@Table(name = "ActionLog")
public class ActionLog extends GenericEntity {
	private static final long serialVersionUID = -4374405596876283608L;
	private Long operatorId;
	private String relateId;
	private String relateObject;
	private String action;
	private String title;
	private String description;

	public ActionLog(String relateId, String relateObject, String action, String title, String description, Long operatorId) {
		super();
		this.relateId = relateId;
		this.relateObject = relateObject;
		this.action = action;
		this.title = title;
		this.description = description;
		this.operatorId = operatorId;
	}

	public ActionLog() {
	}

	@Override
	public String getSelfIntro() {
		return "操作日志";
	}

	@Override
	public String getSelfDescription() {
		return "操作日志";
	}

}
