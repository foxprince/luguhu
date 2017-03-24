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

import cn.anthony.luguhu.domain.Product;
import cn.anthony.luguhu.service.ProductService;
import cn.anthony.luguhu.service.UserService;
import cn.anthony.luguhu.util.Constant;
import cn.anthony.luguhu.util.ControllerUtil;
import lombok.Data;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends GenericController<Product, Long> {

	@Resource
	ProductService service;
	@Resource
	UserService userService;

	@Override
	ProductService getService() {
		return this.service;
	}

	@Override
	public Product init(Model m, Object... relateId) {
		m.addAttribute("producerList", userService.findByLevel(Constant.USER_LEVEL_PPRODUCER));
		return new Product();
	}

	@Override
	protected String getListView() {
		return "/product/list";
	}

	@Override
	protected String getIndexView() {
		return "/product/list";
	}

	@Override
	protected String getFormView() {
		return "/product/form";
	}

	@RequestMapping(value = { "list", "listPage" })
	public String list(@ModelAttribute("search") ProductSearch us, @QuerydslPredicate(root = Product.class) Predicate predicate,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Model m) {
		ControllerUtil.setPageVariables(m, getService().getRepository().findAll(predicate, pageable));
		return getListView();
	}
}

@Data
class ProductSearch {
	private String title;
	private String description;
}