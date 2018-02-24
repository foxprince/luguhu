package cn.anthony.luguhu.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "product,operator,saleUnits")
@QueryEntity
@Entity
public class SalePack extends GenericEntity {
	private static final long serialVersionUID = -934407005154746958L;
	/*
	 * `id` bigint NOT NULL primary key AUTO_INCREMENT, title varchar(50) not
	 * null comment '产品标题', price float not null comment '价格', amount integer
	 * null comment '库存总数', sale_begin timestamp not null comment '开始销售日期',
	 * sale_end timestamp null comment '截止销售日期', description text null,
	 * operator_id bigint default 0 null, ctime timestamp not null
	 */
	@ManyToMany(targetEntity = SaleUnit.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name="sale_pack_unit",
	    joinColumns=
	        @JoinColumn(name="pack_id", referencedColumnName="ID"),
	    inverseJoinColumns=
	        @JoinColumn(name="unit_id", referencedColumnName="ID")
    )
	private List<SaleUnit> saleUnits;
	private String title, intro, content;
	private Byte packType, priceType;
	private Integer price;
	private Integer amount;
	private Integer minBatch, maxBatch, minPrice;// 最小起售数量
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
	private Date saleBegin, saleEnd;
	@ManyToOne
	@JoinColumn(name = "asset_id")
	private Asset asset;
	@ManyToOne
	@JoinColumn(name = "operator_id")
	private User operator;

	@Override
	public String getSelfIntro() {
		return "产品销售包";
	}

	@Override
	public String getSelfDescription() {
		return "产品销售包，由产品规格组合而成";
	}

	public String getFormatSaleBegin() {
		return DateFormatUtils.format(getSaleBegin(), "yyyy-MM-dd HH:mm:ss");
	}

	public String getFormatSaleEnd() {
		return DateFormatUtils.format(getSaleEnd(), "yyyy-MM-dd HH:mm:ss");
	}
}
