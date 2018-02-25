package cn.anthony.luguhu.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
@Entity
@QueryEntity
public class UserAccount extends GenericEntity {
	private static final long serialVersionUID = -3926203739654212938L;
	private Integer balance,prePaied,status = 1;
	@OneToOne
	@JoinColumn(name = "main_user_id")
	User user;
}
