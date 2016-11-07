package cn.anthony.luguhu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @RequestMapping(value = { "/", "/index", "/main" }, method = RequestMethod.GET)
    public String index() {
	return "main/index2";
    }
    @RequestMapping(value = {"/index2"}, method = RequestMethod.GET)
    public String index2() {
	return "index2";
    }
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login() {
	return "main/login";
    }
    
    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String register() {
	return "main/register";
    }
}
