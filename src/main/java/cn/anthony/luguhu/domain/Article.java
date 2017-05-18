package cn.anthony.luguhu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "author,operator")
@QueryEntity
@Entity
@Table
public class Article extends GenericEntity {
	private static final long serialVersionUID = 4843647685343415645L;
	private String title;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	private String origin;
	@ManyToOne
	@JoinColumn(name = "asset_id")
	private Asset asset;
	private String summary;
	@Column(columnDefinition="TEXT")
	private String content;
	@ManyToOne
	@JoinColumn(name = "operator_id")
	private User operator;

	@Override
	public String getSelfIntro() {
		return "文章";
	}

	@Override
	public String getSelfDescription() {
		return getTitle();
	}

}
