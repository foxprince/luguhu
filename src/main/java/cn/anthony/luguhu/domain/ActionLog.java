package cn.anthony.luguhu.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
@QueryEntity
@Entity
@Table(name = "action_log")
public class ActionLog extends GenericEntity {
    private Long operatorId;
    private Long relateId;
    private String relateObject;
    private String action;
    private String title;
    private String description;
    public ActionLog(Long relateId, String relateObject, String action, String title, String description,
	    Long operatorId) {
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
