package cn.anthony.luguhu.domain;

import javax.persistence.Entity;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
@QueryEntity
@Entity
public class SiteConfig extends GenericEntity {
	private static final long serialVersionUID = -2736781485679462490L;
	private String title,info,logo,copyright;
}
