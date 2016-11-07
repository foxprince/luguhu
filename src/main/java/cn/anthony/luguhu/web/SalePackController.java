package cn.anthony.luguhu.web;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.querydsl.core.types.Predicate;

import cn.anthony.luguhu.domain.SalePack;
import cn.anthony.luguhu.service.ProductService;
import cn.anthony.luguhu.service.SalePackService;
import cn.anthony.luguhu.util.ControllerUtil;
import lombok.Data;

@Controller
@RequestMapping(value = "/salePack")
public class SalePackController extends GenericController<SalePack,Long> {
    @Resource
    SalePackService service;
    @Resource
    ProductService productService;

    @Override
    SalePackService getService() {
	return this.service;
    }
    @Override
    public SalePack init(Model m, Object... relateId) {
	m.addAttribute("productList", productService.findAll());
	return new SalePack();
    }

    @Override
    protected String getListView() {
	return "/product/salePackList";
    }

    @Override
    protected String getIndexView() {
	return "/product/salePackList";
    }

    @Override
    protected String getFormView() {
	return "/product/salePackForm";
    }

    @RequestMapping(value = { "list", "listPage" })
    public String list(@ModelAttribute("search") SalePackSearch us,
	    @QuerydslPredicate(root = SalePack.class) Predicate predicate,
	    @PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Model m) {
	ControllerUtil.setPageVariables(m, getService().getRepository().findAll(predicate, pageable));
	return getListView();
    }
}
@Data class SalePackSearch {
    private String title,description;
}
