package cn.anthony.luguhu.wp;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.anthony.luguhu.api.JsonResponse;
import cn.anthony.luguhu.domain.User;
import cn.anthony.luguhu.domain.WxUser;
import cn.anthony.luguhu.repository.UserRepository;
import cn.anthony.luguhu.repository.WxUserRepository;
import cn.anthony.luguhu.util.JsonUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews.WxMpMaterialNewsArticle;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Binary Wang
 */
@Controller
@RequestMapping("/api/wp")
public class WechatController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WxMpService wxService;//注意，此类的bean声明是通过WechatMpConfiguration实现的
	@Autowired
	private WxMpMessageRouter router;//注意，此类的bean声明是通过WechatMpConfiguration实现的
	@Autowired private WxUserRepository wxUserRepo;
	@Autowired private UserRepository userRepo;
	@Value("classpath:wpMenu.json")
	Resource wxMenuResource;
	

	/** 验证微信服务可用，返回同样内容
	 */
	@GetMapping(produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String authGet(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr) {
		this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
		if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
			throw new IllegalArgumentException("请求参数非法，请核实!");
		}
		if (this.wxService.checkSignature(timestamp, nonce, signature)) {
			return echostr;
		}
		return "非法请求";
	}

	@PostMapping(value = "/menuCreate", produces = "application/xml; charset=UTF-8")
	@ResponseBody
	public String menuCreate() throws WxErrorException, IOException {
		WxMenu menu = WxMenu.fromJson(IOUtils.toString(wxMenuResource.getInputStream()));
		logger.info("create menu:{}", wxService.getMenuService().menuCreate(menu));
		return "SUCCESS";
	}

	@RequestMapping(value = "/pushtest")
	@ResponseBody
	public boolean test() throws WxErrorException {
		boolean bool = false; 
		WxMpKefuMessage message = new WxMpKefuMessage();
		message.setToUser("o6AWbjpi4e7MRmXP4qYQpN5zSoIM");
		message.setMsgType(WxConsts.KefuMsgType.TEXT);
		message.setContent("测试推送消息");
		bool = wxService.getKefuService().sendKefuMessage(message);
		return bool;
	}
	
	/*下载素材*/
	private void fetchMedia() throws WxErrorException {
		WxMpMaterialNewsBatchGetResult newsList = wxService.getMaterialService().materialNewsBatchGet(20, 40);
		for(WxMaterialNewsBatchGetNewsItem news :newsList.getItems()) {
			for(WxMpMaterialNewsArticle article : news.getContent().getArticles()) {
				try {
					System.out.println(article.getTitle());
					FileUtils.writeStringToFile(new File("/Users/zj/tmp/lm/"+article.getTitle()+".html"), article.getContent());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
	 * 若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数redirect_uri?state=STATE
	 * @param code
	 * @param state
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping(value = "/auth")
	public String auth(String code,String state,HttpServletResponse response) throws WxErrorException {
		logger.info("auth code:"+code);
		WxMpOAuth2AccessToken accessToken = wxService.oauth2getAccessToken(code);
		WxMpUser wxUser = wxService.getUserService().userInfo(accessToken.getOpenId());//wxService.oauth2getUserInfo(accessToken, null);
		//添加或更新用户信息
		WxUser wuser = wxUserRepo.findByOpenId(wxUser.getOpenId());
		if(wuser==null)
			wuser = new WxUser();
		BeanUtils.copyProperties(wxUser, wuser);
		wuser.setSubscribeTime(new Timestamp(wxUser.getSubscribeTime()*1000l));
		wxUserRepo.save(wuser);
		User user = userRepo.findByWxUserOpenId(wxUser.getOpenId());
		if(user==null)
			user = new User();
		user.setLoginType((byte) 4);
		user.setWxUser(wuser);
		userRepo.save(user);
		//根据state的不同导向到不同页面，带参数openId
		if(state.equals("new"))
			return "redirect:/wp/user.html?openId="+wuser.getOpenId();
		else	
			return "redirect:/solidState/userIndex.html?openId="+wuser.getOpenId();
	}
	
	//测试cookie和session用
	@RequestMapping(value = "/visit")
	public String visit(@CookieValue(name="openId",defaultValue="") String openId,HttpServletRequest httpRequest,HttpSession session) throws WxErrorException {
		Cookie[] cookies = httpRequest.getCookies();
        logger.info("cookies info"+JsonUtils.toJson(cookies));
		logger.info("visit openId cookie:"+openId);
		logger.info("session openId:"+session.getAttribute("openId"));
		openId = (String)session.getAttribute("openId");
		logger.info("*** Session data ***");
		Enumeration<String> e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			logger.info(s+":" + session.getAttribute(s));
		}
		if(openId!=null) {
		WxMpUser wxUser = wxService.getUserService().userInfo(openId);//wxService.oauth2getUserInfo(accessToken, null);
		//添加或更新用户信息
		WxUser wuser = wxUserRepo.findByOpenId(wxUser.getOpenId());
		if(wuser==null)
			wuser = new WxUser();
		BeanUtils.copyProperties(wxUser, wuser);
		wuser.setSubscribeTime(new Timestamp(wxUser.getSubscribeTime()*1000l));
		wxUserRepo.save(wuser);}
		//根据state的不同导向到不同页面，带参数openId
		return "redirect:/wp/user.html?openId="+openId;
	}
	
	@RequestMapping(value = "/cookietest")
	public String cookie(String openId,HttpServletResponse response) throws WxErrorException {
		Cookie foo = new Cookie("openId", openId); 
		foo.setMaxAge(365*24*3600);
		response.addCookie(foo);
		//根据state的不同导向到不同页面，带参数openId
		return "redirect:/solidState/profile.html?openId="+openId;
	}
	
	@RequestMapping("oauthUrl")@ResponseBody
	public String oauthUrl(String redirectURI,String scope,String state){
		return wxService.oauth2buildAuthorizationUrl(redirectURI, scope, state);
	}
	@RequestMapping(value = "/shorturl")
	@ResponseBody
	public String shortUrl(String url) throws WxErrorException {
		return wxService.shortUrl(url);
	}
	@RequestMapping(value = "/token")
	@ResponseBody
	public String token() throws WxErrorException {
		return wxService.getAccessToken();
	}
	@RequestMapping(value = "/profile")
	public String profile()  {
		return "redirect:/solidState/profile.html";
	}
	@RequestMapping(value = "/getUser")
	@ResponseBody
	private WxUser getUser(String openId) throws WxErrorException {
		WxMpUserService wus = wxService.getUserService();
		WxMpUser userWxInfo = wus.userInfo(openId, null);
		if (userWxInfo != null) {
			WxUser wuser = new WxUser();
			BeanUtils.copyProperties(userWxInfo, wuser);
			wuser.setSubscribeTime(new Timestamp(userWxInfo.getSubscribeTime()*1000l));
			return wxUserRepo.save(wuser);
		}
		return null;
	}
	/**
	 * @param openId 根据
	 * @return
	 * @throws WxErrorException
	 */
	//@RequestMapping(value = "/wxUser/{openId}", method = RequestMethod.GET)
	//@ResponseBody
	private JsonResponse get(@PathVariable String openId) throws WxErrorException {
		WxMpUser userWxInfo = wxService.getUserService().userInfo(openId, null);
		if (userWxInfo != null) {
			WxUser wxUser = wxUserRepo.findByOpenId(openId);
			if(wxUser==null)
				wxUser = new WxUser();
			BeanUtils.copyProperties(userWxInfo, wxUser);
			wxUser.setSubscribeTime(new Timestamp(userWxInfo.getSubscribeTime()*1000l));
			wxUserRepo.save(wxUser);
			User user = userRepo.findByWxUserOpenId(openId);
			if(user==null)
				user = new User(Byte.valueOf((byte) 4));
			user.setWxUser(wxUser);
			userRepo.save(user);
			return new JsonResponse(user);
		}
		return null;
	}

	@RequestMapping(value = "/getAllUsers")
	@ResponseBody
	public boolean getAllUsers() throws WxErrorException {
		boolean bool = false;
		WxMpUserService wus = wxService.getUserService();
		List<WxUser> list = new ArrayList<WxUser>();
		for (String openId : wus.userList(null).getOpenids()) {
			WxMpUser userWxInfo = wus.userInfo(openId, null);
			if (userWxInfo != null) {
				WxUser wuser = new WxUser();
				BeanUtils.copyProperties(userWxInfo, wuser);
				wuser.setSubscribeTime(new Timestamp(userWxInfo.getSubscribeTime()*1000l));
				list.add(wuser);
			}
		}
		wxUserRepo.saveAll(list);
		return bool;
	}

	/**消息处理主方法**/
	@PostMapping(produces = "application/xml; charset=UTF-8")
	@ResponseBody
	public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature,HttpServletRequest httpRequest,HttpServletResponse response,HttpSession session) throws WxErrorException, IOException {
		this.logger.info( "接收微信POST请求：[signature=[{}], encType=[{}], msgSignature=[{}]," + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ", signature, encType, msgSignature, timestamp, nonce, requestBody);
		if (!this.wxService.checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}
		String out = null;
		WxMpXmlMessage inMessage = null;
		WxMpXmlOutMessage outMessage = null;
		if (encType == null) {
			// 明文传输的消息
			inMessage = WxMpXmlMessage.fromXml(requestBody);
			outMessage = this.route(inMessage);
			if (outMessage == null) {
				return "";
			}
			out = outMessage.toXml();
		} else if ("aes".equals(encType)) {
			// aes加密的消息
			inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, this.wxService.getWxMpConfigStorage(), timestamp, nonce,
					msgSignature);
			this.logger.info("\n消息解密后内容为：\n{} ", inMessage.toString());
			outMessage = this.route(inMessage);
			if (outMessage == null) {
				return "";
			}
			out = outMessage.toEncryptedXml(this.wxService.getWxMpConfigStorage());
		}
		this.logger.info("\n组装回复信息：{}", outMessage.toXml());
		return out;
	}

	private WxMpXmlOutMessage route(WxMpXmlMessage message) {
		try {
			// WxMpMessageInterceptor interceptor;
			// WxMpMessageHandler handler;
			// router.rule().msgType("MSG_TYPE").event("EVENT").eventKey("EVENT_KEY").content("CONTENT").interceptor(interceptor)
			// .handler(handler).end().rule()
			// // 另外一个匹配规则
			// .end();
			return this.router.route(message);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Component
	class TextHandler extends DefaultHandler {
		@Override
		public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
				WxSessionManager sessionManager) throws WxErrorException {
			WxMpXmlOutTextMessage out = new WxMpXmlOutTextMessage();
			out.setContent("echo:" + wxMessage.getContent());
			wrapperOutMessage(wxMessage, out);
			logger.info(out.toXml());
			return out;
		}
	}

	abstract class DefaultHandler implements WxMpMessageHandler {
		protected final Logger logger = LoggerFactory.getLogger(this.getClass());

		protected void wrapperOutMessage(WxMpXmlMessage wxMessage, WxMpXmlOutMessage out) {
			out.setToUserName(wxMessage.getFromUser());
			out.setFromUserName(wxMessage.getToUser());
			out.setCreateTime(System.currentTimeMillis() / 1000l);
		}
	}
}
