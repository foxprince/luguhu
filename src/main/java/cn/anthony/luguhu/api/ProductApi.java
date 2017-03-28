package cn.anthony.luguhu.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import cn.anthony.luguhu.domain.Saleable;
import cn.anthony.luguhu.service.SalePackService;
import cn.anthony.luguhu.service.SaleUnitService;

@RestController
@RequestMapping(value = "/api/product")
public class ProductApi {

	@Resource
	SaleUnitService unitService;
	@Resource
	SalePackService packService;
	
	@RequestMapping(value = { "list"})
	public JsonResponse list() {
		List<Saleable> l = Lists.newArrayList((packService.findAll()));
		l.addAll(unitService.findBySaleable(true));
		return new JsonResponse(new PageImpl<Saleable>(l));
	}
}
