package cn.anthony.luguhu.domain;

import javax.persistence.Entity;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
@QueryEntity
@Entity
public class PackUnit extends GenericEntity {
	private static final long serialVersionUID = 8468112793712160304L;
	private SaleUnit saleUnit;
	private Short amount;

	@Override
	public String getSelfIntro() {
		return "销售包内容";
	}

	@Override
	public String getSelfDescription() {
		return "产品销售包内的具体规格";
	}

}
