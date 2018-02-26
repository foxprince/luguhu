package cn.anthony.luguhu.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.rest.core.annotation.RestResource;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "products,depositList")
@QueryEntity
@Entity
@Table
public class User extends GenericEntity {
	private static final long serialVersionUID = 2614801674646477358L;
	// @NotNull
	// @NotEmpty
	@Column(nullable = false, length = 64)
	private String email;
	private Byte loginType;// '1:手机号码，2：邮箱，3：自定义用户名',4:微信
	@Size(min = 6, max = 30)
	@RestResource(exported = false)
	private String password;
	private String name;
	private String nickname;
	private Byte sex, age;
	private Byte level = 0;// '0-普通用户，1：份额用户,2：股东用户',
	private String phone;
	private String origin = "web";
	private Date lastLogin;
	private boolean active = true;
	private boolean verified = false;
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "wx_user_id")
	private WxUser wxUser = null;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "account_id")
	@RestResource(exported = true)
	private UserAccount account = null;
	@OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "producer")
	transient private List<Product> products;
	@OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	@RestResource(path = "addresses", rel = "addresses")
	private List<Address> addresses;
	@OneToMany(targetEntity = UserDeposit.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<UserDeposit> depositList;
	public User() {
	}

	public User(Byte loginType) {
		this.loginType = loginType;
	}

	public String getOriginDesc() {
		if (origin == null)
			return null;
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
		if (level == null)
			return null;
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
		if (sex == null)
			return null;
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
