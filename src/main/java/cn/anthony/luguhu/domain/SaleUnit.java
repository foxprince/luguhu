package cn.anthony.luguhu.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "product,operator")
@QueryEntity
@Entity
public class SaleUnit extends GenericEntity implements Saleable {
	private static final long serialVersionUID = -934407005154746958L;

	/*
	 * product_id bigint not null, title varchar(50) not null comment
	 * '规格标题，如一级骏枣，个重70-90克猕猴桃', unit varchar(6) not null comment
	 * '规格，斤、克、公斤、箱、打、个、头', price float null comment '单价，可以为空', amount integer
	 * null comment '库存总数', operator_id bigint default 0 null,
	 */
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;
	private String title, description,img;
	private String unit;
	private Float price;
	private Integer total;//库存总数
	private boolean saleable;// 是否可以单独销售
	private Short minBatch;// 最小起售数量
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

}
