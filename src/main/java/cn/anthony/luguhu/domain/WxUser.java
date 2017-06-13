package cn.anthony.luguhu.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
@QueryEntity
@Entity
public class WxUser extends GenericEntity implements Comparable<WxUser> {
	private static final long serialVersionUID = -7964486603201004905L;
	private Boolean subscribe;
	private String openId;
	private String nickname;
	private String sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headImgUrl;
	private Timestamp subscribeTime;
	private String unionId;
	private Integer sexId;
	private String remark;
	private Integer groupId;
	private Short level;// 用于表示特定用户,0:普通用户，5:生产者

	@Override
	public String getSelfIntro() {
		return null;
	}

	@Override
	public String getSelfDescription() {
		return null;
	}

	@Override
	public int compareTo(WxUser o) {
		System.out.println(o);
		if (o != null)
			return (int) (this.getId() - o.getId());
		else
			return this.getId().intValue();
	}
}
