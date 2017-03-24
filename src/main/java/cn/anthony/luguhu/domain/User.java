package cn.anthony.luguhu.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "products")
@QueryEntity
@Entity
@Table
public class User extends GenericEntity {

	private static final long serialVersionUID = 2614801674646477358L;
	@NotNull
	@NotEmpty
	@Column(nullable = false, length = 64)
	@Email
	private String email;
	private byte loginType;
	@NotEmpty
	@Size(min = 6, max = 30)
	private String password;
	@NotEmpty
	private String name;
	private String nickname;
	private byte sex, age;
	private byte level;
	private String phone;
	private String address;
	private String origin = "web";
	private Date lastLogin;
	private boolean active = true;
	private boolean verified = false;

	@OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "producer")
	transient private List<Product> products;

	public String getOriginDesc() {
		switch (origin) {
		case "web":
			return "网站注册";
		default:
			return origin;
		}
	}

	public String getVerifiedDesc() {
		if (isVerified())
			return "正常";
		else
			return "待审核";
	}

	public String getActiveDesc() {
		if (isActive())
			return "正常";
		else
			return "停用";
	}

	public String getLevelDesc() {
		switch (level) {
		case 1:
			return "份额用户";
		case 2:
			return "股东用户";
		case 3:
			return "生产者";
		default:
			return "普通用户";
		}
	}

	public String getSexDesc() {
		switch (sex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "未知";
		}
	}

	@Override
	public String getSelfIntro() {
		return getLevelDesc();
	}

	@Override
	public String getSelfDescription() {
		return getName();
	}
}
