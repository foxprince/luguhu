package cn.anthony.luguhu.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hankcs.hanlp.HanLP;

import cn.anthony.luguhu.util.Constant;

@Controller
public class HomeController {
	@Resource
	Constant constant;

	@RequestMapping(value = { "/", "/index", "/main" }, method = RequestMethod.GET)
	public String index() {
		return "redirect:resources/solidState/index.html";
	}

	@RequestMapping(value = { "/index2" }, method = RequestMethod.GET)
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

	@RequestMapping(value = { "/api/getSiteTitle" }, method = RequestMethod.GET)
	public @ResponseBody String getSiteTitle() {
		return constant.getSiteTitle();
	}

	@RequestMapping(value = { "/api/getSummary" })
	public @ResponseBody List<String> summary(String content) {
		String regMatch = "\\s*|\t|\r|\n";
		content = content.replaceAll(regMatch, "");
		String regMatchTag = "<[^>]*>";
		content = content.replaceAll(regMatchTag, "");
		List<String> sentenceList = HanLP.extractSummary(content, 3);
		return sentenceList;
	}
}
