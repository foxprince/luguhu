package cn.anthony.luguhu.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.Data;
@Data
public class WebPageRequest extends PageRequest {
    
    public static int page = 0;
    public static int size = 1;
    
    public int getPage() {
	return super.getPageNumber();
    }
    
    public int getSize() {
	return super.getPageSize();
    }
    public WebPageRequest(){
	super(page,size,new Sort(Direction.DESC,"id"));
    }
    
    public WebPageRequest(int page, int size) {
	this(page-1, size, null);
    }

    public WebPageRequest(int page, int size, Direction direction, String... properties) {
	super(page-1, size, new Sort(direction, properties));
    }

    public WebPageRequest(int page, int size, Sort sort) {
	super(page-1, size,sort);
    }
    
    @Override
    public String toString() {
	return "WebPageRequest("+getPageNumber()+","+getPageSize()+")";
    }
}
