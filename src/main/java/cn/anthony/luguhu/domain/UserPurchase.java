package cn.anthony.luguhu.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "salePack,user")
@QueryEntity
@Entity
public class UserPurchase extends GenericEntity {
	private static final long serialVersionUID = -934407005154746958L;

	@OneToMany(targetEntity = SaleUnit.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<PackUnit> packUnits;
	private String remark;//备注
	private Float price;//总价
	private Integer amount;//数量
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp expectExpressTime; // 期望快递发货时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp orderTime; // 下单时间
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "pack_id")
	private SalePack salePack;

	@Override
	public String getSelfIntro() {
		return "用户购买产品包";
	}

	@Override
	public String getSelfDescription() {
		return "用户购买产品包，由产品规格及购买数量组合而成";
	}


}
