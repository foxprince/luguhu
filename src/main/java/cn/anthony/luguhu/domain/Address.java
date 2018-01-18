package cn.anthony.luguhu.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "user")
@QueryEntity
@Entity
public class Address extends GenericEntity {
	private static final long serialVersionUID = 7775642104198507254L;
	private String consignee,phone,postAddress,city,region,tag;
	private boolean isDefault;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
