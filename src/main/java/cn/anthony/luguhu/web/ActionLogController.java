package cn.anthony.luguhu.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.querydsl.core.types.Predicate;

import cn.anthony.luguhu.domain.ActionLog;
import cn.anthony.luguhu.domain.QActionLog;
import cn.anthony.luguhu.service.ActionLogService;
import cn.anthony.luguhu.util.ControllerUtil;

@Controller
@RequestMapping(value = "/actionLog")
public class ActionLogController extends GenericController<ActionLog, Long> {

	@Override
	public ActionLog init(Model m, Object... relateId) {
		m.addAttribute("titles", service.findAllTitles());
		return new ActionLog();
	}

	@Resource
	ActionLogService service;

	@Override
	ActionLogService getService() {
		return this.service;
	}

	@Override
	protected String getListView() {
		return "/log/actionLog";
	}

	@Override
	protected String getIndexView() {
		return "/log/actionLog";
	}

	@Override
	protected String getFormView() {
		return "/log/actionLog";
	}

	@RequestMapping(value = { "list", "listPage" })
	public String list(@ModelAttribute("search") ActionLogSearch searchItem,
			@QuerydslPredicate(root = ActionLog.class) Predicate predicate,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Model m) {
		if (predicate != null && StringUtils.isNoneBlank(searchItem.getTitle())) {
			predicate = QActionLog.actionLog.description.contains(searchItem.getTitle()).or(predicate);
		}
		ControllerUtil.setPageVariables(m, getService().getRepository().findAll(predicate, pageable));
		return getListView();
	}
}
