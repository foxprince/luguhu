package cn.anthony.luguhu.domain;

import java.sql.Timestamp;

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
public class WxPayOrder extends GenericEntity {
	private Integer fee;
	private String openId,tradeNo,body,deviceInfo,tradeType,clientIp,attach;
	private String returnCode,resultCode,errCode;
	private String prepayId,transactionId,timeEnd;
	private Timestamp orderTime,notifyTime;
	private String notifyResult,notifyErrCode;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	@ManyToOne
	@JoinColumn(name = "account_id")
	UserAccount account;
}
