package cn.anthony.luguhu.domain;

import javax.persistence.Entity;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
@QueryEntity
@Entity
public class Tag extends GenericEntity {
	public Tag(){super();}
	public Tag(String s) {
		this.label = s;
	}
	private static final long serialVersionUID = -7585687273203339268L;
	private String label;
}
