package cn.anthony.luguhu.domain;

public interface Saleable {
	public Long getId();
	public String getTitle();
	public String getContent();
	public String getImg();
	public Boolean isPack();
	public Integer getPrice();
}
