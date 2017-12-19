package cn.anthony.luguhu.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "product,operator")
@QueryEntity
@Entity
public class SaleUnit extends GenericEntity implements Saleable {
	private static final long serialVersionUID = -934407005154746958L;
	@ManyToOne
	@JoinColumn(name = "product_id")
	//@JsonIgnore
	private Product product;
	private String title, content;
	private String unit;
	private Float price;
	private Integer total;//库存总数
	private boolean saleable;// 是否可以单独销售
	private Short minBatch;// 最小起售数量
	@ManyToOne
	@JoinColumn(name = "asset_id")
	private Asset asset;
	@ManyToOne
	@JoinColumn(name = "operator_id")
	private User operator;

	@Override
	public String getSelfIntro() {
		return "产品规格";
	}

	public String getSaleableDesc() {
		if (saleable)
			return "可以";
		else
			return "不可以";
	}

	@Override
	public String getSelfDescription() {
		return product.getTitle() + "产品销售的最小规格";
	}

	@Override
	public Boolean isPack() {
		return false;
	}

	@Override
	public String getImg() {
		if(asset!=null)
			return asset.getLocation();
		return null;
	}

}
