package cn.anthony.luguhu.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.anthony.luguhu.domain.SalePack;
import cn.anthony.luguhu.service.SalePackService;
import cn.anthony.luguhu.web.GenericRestController;

@Controller
@RequestMapping(value = "/api/productSalePack")
public class SalePackApi extends GenericRestController<SalePack, Long> {
	
	public SalePackApi(SalePackService service) {
		super(service);
	}

}
