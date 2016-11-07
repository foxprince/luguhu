package cn.anthony.luguhu.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.querydsl.core.types.Predicate;

import cn.anthony.luguhu.domain.QUser;
import cn.anthony.luguhu.domain.User;
import cn.anthony.luguhu.exception.EntityNotFound;
import cn.anthony.luguhu.service.ActionLogService;
import cn.anthony.luguhu.service.UserService;
import cn.anthony.luguhu.util.Constant;
import cn.anthony.luguhu.util.ControllerUtil;
import lombok.Data;

@Controller
@RequestMapping(value = "/user")
public class UserController extends GenericController<User, Long> {

    @Override
    public User init(Model m, Object... relateId) {
	m.addAttribute("userLevelMap", Constant.levelMap);
	return new User();
    }

    @Resource
    UserService userService;
    @Resource
    ActionLogService actionLogService;

    @Override
    UserService getService() {
	return this.userService;
    }

    @Override
    protected String getListView() {
	return "/user/list";
    }

    @Override
    protected String getIndexView() {
	return "/user/list";
    }

    @Override
    protected String getFormView() {
	return "/user/form";
    }

    @Override
    public void validate(User user, Errors errors) {
	// if (!user.getPassword().equals(user.getRepassword()))
	// errors.rejectValue("repassword", "password.invalid", "两次输入的密码不一致");
    }

    @RequestMapping(value = { "/regist" }, method = RequestMethod.POST)
    public String addOrUpdate(@ModelAttribute User user, final RedirectAttributes redirectAttributes, Model m)
	    throws EntityNotFound {
	getService().saveOrUpdate(user);
	m.addAttribute("s", "regist-success-box");
	m.addAttribute("title", "注册成功");
	actionLogService.add(user, "REGIST", "用户注册", user.getName()+"自助注册", 0l);
	return "/user/register";
    }

    @RequestMapping(value = { "/regist" }, method = RequestMethod.GET)
    public String addOrUpdate(Model m) {
	m.addAttribute("s", "signup-box");
	m.addAttribute("title", "注册");
	return "/user/register";
    }
    
    @RequestMapping(value = { "list", "listPage" })
    public String list(@ModelAttribute("search") UserSearch us,
	    @QuerydslPredicate(root = User.class) Predicate predicate,
	    @PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Model m) {
	if (predicate != null && StringUtils.isNoneBlank(us.getName())) {
	    predicate = QUser.user.phone.startsWith(us.getName()).or(predicate);
	}
	ControllerUtil.setPageVariables(m, getService().getRepository().findAll(predicate, pageable));
	return getListView();
    }

    @RequestMapping(value = { "/checkEmailAndPasswd" })
    public @ResponseBody boolean checkEmailAndPasswd(String email, String password, Model m) {
	if (getService().findByEmail(email).getPassword().equals(password))
	    return true;
	return false;
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.POST)
    public String login(String email, String password, Model m) {
	if (getService().findByEmail(email).getPassword().equals(password)) {
	    logger.info("login success");
	    return "redirect:/index";
	} else {
	    m.addAttribute("s", "login-box");
	}
	return getFormView();
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model m) {
	m.addAttribute("s", "login-box");
	m.addAttribute("title", "登录");
	return getFormView();
    }

    @RequestMapping("emailCanRegisted")
    public @ResponseBody boolean emailHasNotRegisted(String email) {
	if (getService().emailExists(email))
	    return false;
	return true;
    }

    @RequestMapping("emailExists")
    public @ResponseBody boolean emailExists(String email) {
	if (getService().emailExists(email))
	    return true;
	return false;
    }

    @RequestMapping("verified")
    public @ResponseBody boolean verified(Long id) throws EntityNotFound {
	User u = getService().findById(id);
	u.setVerified(true);
	getService().saveOrUpdate(u);
	return true;
    }
}
@Data class UserSearch {
    private String name,level;
}