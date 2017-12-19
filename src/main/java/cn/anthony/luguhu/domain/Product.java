package cn.anthony.luguhu.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "producer,operator,saleUnits")
@QueryEntity
@Entity
@Table
public class Product extends GenericEntity {
	private static final long serialVersionUID = 4843647685343415645L;
	private String title;
	@ManyToOne
	@JoinColumn(name = "producer_id")
	private User producer;
	private String place;
	private String content;
	@ManyToOne
	@JoinColumn(name = "operator_id")
	private User operator;
	@OneToMany(targetEntity = SaleUnit.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	@JsonIgnore
	private List<SaleUnit> saleUnits;

	@Override
	public String getSelfIntro() {
		return "产品";
	}

	@Override
	public String getSelfDescription() {
		return getTitle();
	}

	@Override
	public String toString() {
		return title;
	}
}
