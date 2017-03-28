package cn.anthony.luguhu.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.anthony.luguhu.api.JsonResponse;
import cn.anthony.luguhu.domain.GenericEntity;
import cn.anthony.luguhu.exception.EntityNotFound;
import cn.anthony.luguhu.service.ActionLogService;
import cn.anthony.luguhu.service.GenericService;

@Controller
public abstract class GenericController<T, ID extends Serializable> {
	public abstract T init(Model m, Object... relateId);

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	abstract GenericService<T, ID> getService();

	@Resource
	ActionLogService actionLogService;

	/**
	 * 在所有方法执行前执行，根据id构造实例
	 * 
	 * @param id
	 * @param m
	 * @return
	 */
	@ModelAttribute
	public T setUpForm(Model m, @RequestParam(value = "id", required = false) ID id,
			@RequestParam(required = false) final Object... relateId) {
		T t = init(m, relateId);
		if (id != null) {
			t = getService().findById(id);
			if (t == null)
				t = init(m);
		}
		m.addAttribute("item", t);
		return t;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model m) {
		m.addAttribute(init(m));
		return getFormView();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam ID id, Model m, SessionStatus status) {
		m.addAttribute(getService().findById(id));
		return getFormView();
	}

	/**
	 * 在列表页上方直接编辑form，适用于字段较少的对象
	 * 
	 * @param id
	 * @param m
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/editInList", method = RequestMethod.GET)
	public String editInList(@RequestParam ID id, Model m, SessionStatus status) {
		m.addAttribute(getService().findById(id));
		return list(m);
	}

	/**
	 * 添加或删除的提交操作
	 * 
	 * @param t
	 * @param redirectAttributes
	 * @param request
	 * @param result
	 * @return
	 * @throws EntityNotFound
	 */
	@RequestMapping(value = { "/add", "/edit" }, method = RequestMethod.POST)
	public String addOrUpdate(@ModelAttribute T t, final RedirectAttributes redirectAttributes, HttpServletRequest request,
			BindingResult result) throws EntityNotFound {
		if (result.hasErrors()) {
			return getFormView();
		} else {
			getService().saveOrUpdate(t);
			String action = request.getServletPath().substring(request.getServletPath().lastIndexOf("/") + 1);
			String actionDesc = action.equalsIgnoreCase("add") ? "添加" : "修改";
			logAction((GenericEntity) t, action.toUpperCase(), actionDesc);
			redirectAttributes.addFlashAttribute("message", actionDesc + updateHint(t));
			return "redirect:list";
		}
	}

	protected void logAction(GenericEntity t, String action, String actionDesc) {
		actionLogService.add(t.getId().toString(), t.getClass().getName(), action + "_" + t.getClass().getSimpleName(),
				actionDesc + t.getSelfIntro(), t.getSelfDescription(), 0l);
	}

	private void logAction(ID id, GenericEntity t, String action, String actionDesc) {
		actionLogService.add(id.toString(), t.getClass().getName(), action + "_" + t.getClass().getSimpleName(), actionDesc + t.getSelfIntro(),
				null, 0l);
	}

	/**
	 * 用于添加/删除表单的ajax验证
	 * 
	 * @param t
	 * @param result
	 * @return
	 * @throws EntityNotFound
	 */
	@RequestMapping(value = { "/edit.json", "/add.json" }, method = RequestMethod.POST)
	public @ResponseBody ValidationResponse processFormAjaxJson(@ModelAttribute @Valid T t, BindingResult result)
			throws EntityNotFound {
		validate(t, result);
		ValidationResponse res = new ValidationResponse();
		if (!result.hasErrors()) {
			res.setStatus("SUCCESS");
		} else {
			res.setStatus("FAIL");
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getDefaultMessage()));
			}
			res.setErrorMessageList(errorMesages);
		}
		return res;
	}

	/**
	 * 适用于首页就是列表页
	 * 
	 * @param m
	 * @param status
	 * @return
	 */
	@RequestMapping(value = { "/", "/index" })
	public String index(Model m) {
		return getIndexView();
	}

	@RequestMapping(value = { "/defaultList"})
	public String listAll(Model m,@PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		m.addAttribute("itemList", getService().getRepository().findAll(pageable));
		return getListView();
	}
	@RequestMapping(value = { "/list.json" }) @ResponseBody
	public JsonResponse listAll(@PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return new JsonResponse(getService().getRepository().findAll(pageable));
	}

	/**
	 * 根据关联id列出列表
	 * 
	 * @param m
	 * @param relateId
	 * @return
	 */
	@RequestMapping(value = { "/listAll" }, params = { "relateId" })
	public String list(Model m, @RequestParam(required = false) Long... relateId) {
		listByRelate(m, relateId);
		return getListView();
	}

	protected void listByRelate(Model m, Long... relateId) {
	}

	/**
	 * 
	 * @return jsp的页面地址
	 */
	protected abstract String getListView();

	protected abstract String getIndexView();

	protected abstract String getFormView();

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam ID id, final RedirectAttributes redirectAttributes, SessionStatus status)
			throws EntityNotFound {
		ModelAndView mav = new ModelAndView("redirect:list");
		T t = getService().delete(id);
		logAction(id, (GenericEntity) t, "DELETE", "删除");
		status.setComplete();
		redirectAttributes.addFlashAttribute("message", deleteHint(t));
		return mav;
	}

	public void validate(T t, Errors errors) {
	}

	public static String extractPathFromPattern(final HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		System.out.println(path);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		System.out.println(bestMatchPattern);
		AntPathMatcher apm = new AntPathMatcher();
		String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);
		return finalPath;
	}

	protected void logPara(Model m, HttpServletRequest request, HttpSession session) {
		System.out.println("Inside of dosomething handler method");
		System.out.println("--- Model data ---");
		Map<String, Object> modelMap = m.asMap();
		for (Object modelKey : modelMap.keySet()) {
			Object modelValue = modelMap.get(modelKey);
			System.out.println(modelKey + " -- " + modelValue);
		}
		System.out.println("=== Request data ===");
		java.util.Enumeration<String> reqEnum = request.getAttributeNames();
		while (reqEnum.hasMoreElements()) {
			String s = reqEnum.nextElement();
			System.out.println(s);
			System.out.println("==" + request.getAttribute(s));
		}
		System.out.println("*** Session data ***");
		Enumeration<String> e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			System.out.println(s);
			System.out.println("**" + session.getAttribute(s));
		}
	}

	String updateHint(T t) {
		return "成功";
	}

	String deleteHint(T t) {
		return "成功删除";
	}

	void info(Object s) {
		logger.info(s.toString());
	}
}
