package cn.anthony.luguhu.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "operator")
@QueryEntity
@Entity
@Table
/*网站素材管理，特指图片信息*/
public class Asset extends GenericEntity {
	private static final long serialVersionUID = 3495193991195031152L;
	private String title;//资源标题，缺省与sourceName相同
	private String location;//文件位置
	private String type;
	private String sourceName;
	private Boolean open = true;//是否开放浏览
	@ManyToOne
	@JoinColumn(name = "operator_id")
	private User operator;

	@Override
	public String getSelfIntro() {
		return "素材";
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
