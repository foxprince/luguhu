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

import cn.anthony.util.DateUtil;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "product,operator")
@QueryEntity
@Entity
public class SalePack extends GenericEntity {
    private static final long serialVersionUID = -934407005154746958L;

    /*
     `id` bigint NOT NULL primary key AUTO_INCREMENT,
	title varchar(50) not null comment '产品标题',
	price float not null comment '价格',
	amount integer null comment '库存总数',
	sale_begin timestamp not null comment '开始销售日期',
	sale_end timestamp null comment '截止销售日期',
	description text null,
	operator_id bigint default 0  null,
	ctime timestamp not null
     */
    @OneToMany(targetEntity = SaleUnit.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<PackUnit> packUnits;
    private String title,description;
    private Float price;
    private Integer amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Timestamp saleBegin,saleEnd;
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
	return DateUtil.format(getSaleBegin(), "yyyy-MM-dd HH:mm:ss");
    }
    
    public String getFormatSaleEnd() {
	return DateUtil.format(getSaleEnd(), "yyyy-MM-dd HH:mm:ss");
    }

}
