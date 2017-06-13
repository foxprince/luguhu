package cn.anthony.luguhu.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private String location;//图片文件相对存储位置
	private String createFrom;//来源，缺省为系统上传，ADMIN，WECHAT
	private String type;//类型，text,image,video,voice
	@ManyToMany(targetEntity = Tag.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name="asset_tag")
	private List<Tag> tags;
	private String sourceName;//如果是图片则为原始文件名称，如果是文本则是内容
	private Boolean open = true;//是否开放浏览
	@ManyToOne
	@JoinColumn(name = "operator_id")
	private User operator;
	@ManyToOne
	@JoinColumn(name = "wx_user_id")
	private WxUser wxUser;
	

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
	
	public List<String> getTagLables() {
		List<String> l = new ArrayList<String>();
		for(Tag tag : tags)
			l.add(tag.getLabel());
		return l;
	}
}
