package cn.anthony.luguhu.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.anthony.luguhu.domain.SalePack;
import cn.anthony.luguhu.service.SalePackService;
import cn.anthony.luguhu.web.GenericRestController;

@RestController
@RequestMapping(value = "/api/productSalePack")
public class SalePackApi extends GenericRestController<SalePack, Long> {
	
	public SalePackApi(SalePackService service) {
		super(service);
	}

}
