package cn.anthony.luguhu.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.querydsl.core.types.Predicate;

import cn.anthony.luguhu.domain.SaleUnit;
import cn.anthony.luguhu.exception.EntityNotFound;
import cn.anthony.luguhu.service.ProductService;
import cn.anthony.luguhu.service.SaleUnitService;
import cn.anthony.luguhu.util.Constant;
import cn.anthony.luguhu.util.ControllerUtil;
import lombok.Data;

@Controller
@RequestMapping(value = "/productSaleUnit")
public class SaleUnitController extends GenericController<SaleUnit, Long> {
	@Resource
	SaleUnitService service;
	@Resource
	ProductService productService;

	@Override
	SaleUnitService getService() {
		return this.service;
	}

	@Override
	public SaleUnit init(Model m, Object... relateId) {
		if (relateId != null && relateId.length > 0)
			m.addAttribute("product", productService.findById(Long.parseLong(String.valueOf((relateId[0])))));
		m.addAttribute("unitList", Constant.UNIT_LIST);
		m.addAttribute("productList",productService.findAll());
		return new SaleUnit();
	}

	@Override
	protected String getListView() {
		return "/product/saleUnitList";
	}

	@Override
	protected String getIndexView() {
		return "/product/saleUnitList";
	}

	@Override
	protected String getFormView() {
		return "/product/saleUnitForm";
	}

	@RequestMapping(value = { "list", "listPage" })
	public String list(@ModelAttribute("search") SaleUnitSearch us, @RequestParam(required = false) Long productId,
			@QuerydslPredicate(root = SaleUnit.class) Predicate predicate,
			@PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Model m) {
		if (productId != null && productId > 0)
			m.addAttribute("product", productService.findById(productId));
		ControllerUtil.setPageVariables(m, getService().getRepository().findAll(predicate, pageable));
		return getListView();
	}

	@RequestMapping(value = { "/addByRelate", "/editByRelate" }, method = RequestMethod.POST)
	public String addOrEdit(HttpServletRequest request, @ModelAttribute SaleUnit t, final RedirectAttributes redirectAttributes)
			throws EntityNotFound {
		getService().saveOrUpdate(t);
		String action = request.getServletPath().substring(request.getServletPath().lastIndexOf("/") + 1);
		String actionDesc = action.equalsIgnoreCase("add") ? "添加" : "修改";
		logAction(t, action.toUpperCase(), actionDesc);
		redirectAttributes.addFlashAttribute("message", actionDesc + updateHint(t));
		return "redirect:listAll?relateId=" + t.getProduct().getId();
	}

	@Override
	protected void listByRelate(Model m, Long... relateId) {
		m.addAttribute("itemList", service.findByProduct(relateId[0]));
	}
}

@Data
class SaleUnitSearch {
	private String title, description;
}
