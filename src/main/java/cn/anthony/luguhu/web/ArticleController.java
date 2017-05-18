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

import cn.anthony.luguhu.domain.Article;
import cn.anthony.luguhu.service.ArticleService;
import cn.anthony.luguhu.service.UserService;
import cn.anthony.luguhu.util.Constant;
import cn.anthony.luguhu.util.ControllerUtil;
import lombok.Data;

@Controller
@RequestMapping(value = "/article")
public class ArticleController extends GenericController<Article, Long> {

	@Resource
	ArticleService service;
	@Resource
	UserService userService;

	@Override
	ArticleService getService() {
		return this.service;
	}

	@Override
	public Article init(Model m, Object... relateId) {
		m.addAttribute("authorList", userService.findByLevel(Constant.USER_LEVEL_PPRODUCER));
		return new Article();
	}

	@Override
	protected String getListView() {
		return "/article/list";
	}

	@Override
	protected String getIndexView() {
		return "/article/list";
	}

	@Override
	protected String getFormView() {
		return "/article/form";
	}

	@RequestMapping(value = { "list", "listPage" })
	public String list(@ModelAttribute("search") ArticleSearch us, @QuerydslPredicate(root = Article.class) Predicate predicate,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Model m) {
		ControllerUtil.setPageVariables(m, getService().getRepository().findAll(predicate, pageable));
		return getListView();
	}
}

@Data
class ArticleSearch {
	private String title;
	private String description;
}