package cn.anthony.luguhu.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.anthony.luguhu.service.SalePackService;
import cn.anthony.luguhu.service.SaleUnitService;

@RestController
@RequestMapping(value = "/api/product")
public class ProductApi {

	@Resource
	SaleUnitService unitService;
	@Resource
	SalePackService packService;
	
//	@RequestMapping(value = { "list"})
//	public JsonResponse list() {
//		List<Saleable> l = Lists.newArrayList((packService.findAll()));
//		l.addAll(unitService.findBySaleable(true));
//		return new JsonResponse(new PageImpl<Saleable>(l));
//	}
}
