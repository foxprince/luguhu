package cn.anthony.luguhu.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import cn.anthony.luguhu.domain.SaleUnit;
import cn.anthony.luguhu.service.SaleUnitService;
import cn.anthony.luguhu.web.GenericRestController;

@RestController
@RequestMapping(value = "/api/productSaleUnit")
public class SaleUnitApi extends GenericRestController<SaleUnit, Long> {
	
	public SaleUnitApi(SaleUnitService service) {
		super(service);
	}

	@RequestMapping(value = { "list"})
	public JsonResponse list(@QuerydslPredicate(root=SaleUnit.class) Predicate predicate,
			@PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return new JsonResponse(this.service.getRepository().findAll(predicate, pageable));
	}

}
