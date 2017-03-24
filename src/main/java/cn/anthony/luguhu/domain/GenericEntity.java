package cn.anthony.luguhu.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.anthony.util.DateUtil;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class GenericEntity implements Serializable {
	private static final long serialVersionUID = 4365837246243902781L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "ctime", nullable = false)
	protected Timestamp ctime;

	@JsonIgnore
	transient protected String action;
	@JsonIgnore
	transient protected String actionDesc;

	public GenericEntity() {
		this.ctime = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public abstract String getSelfIntro();

	public abstract String getSelfDescription();

	public String getAction() {
		if (isAdd())
			return "add";
		else
			return "edit";
	}

	public String getActionDesc() {
		if (isAdd())
			return "添加";
		else
			return "修改";
	}

	public String getFormatCtime() {
		return DateUtil.format(getCtime(), "yyyy-MM-dd HH:mm:ss");
	}

	public boolean isAdd() {
		if (id != null)
			return false;
		return true;
	}
}
