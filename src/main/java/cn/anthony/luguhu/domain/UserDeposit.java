package cn.anthony.luguhu.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "user,account")
@QueryEntity
public class UserDeposit extends GenericEntity {
	private static final long serialVersionUID = -3926203739654212938L;
	private Integer amount,balance,entry,status = 0;
	private Long relateId;
	private String notes;
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	@ManyToOne
	@JoinColumn(name = "account_id")
	UserAccount account;
}
