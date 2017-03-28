package cn.anthony.luguhu.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BasePageRequest {

	@NotNull(message = "Please enter ")
	@Min(value = 1, message = "Please addresss.")
	@Max(100000)
	protected int page = 0;
	@NotNull
	@Min(1)
	@Max(1000)
	protected int size = 10;

	public void setPage(int page) {
		this.page = page - 1;
	}

}
