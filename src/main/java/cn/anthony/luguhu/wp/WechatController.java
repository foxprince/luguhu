package cn.anthony.luguhu.wp;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.anthony.luguhu.api.JsonResponse;
import cn.anthony.luguhu.domain.WxUser;
import cn.anthony.luguhu.repository.WxUserRepository;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Binary Wang
 */
@Controller
@RequestMapping("/wp/portal")
public class WechatController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WxMpService wxService;
	@Autowired
	private WxMpMessageRouter router;
	@Autowired
	private WxUserRepository userRepo;
	@Value("classpath:wpMenu.json")
	Resource wxMenuResource;

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
		WxMpKefuMessage message = new WxMpKefuMessage();
		message.setToUser("o6AWbjpi4e7MRmXP4qYQpN5zSoIM");
		message.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
		message.setContent("测试推送消息");
		boolean bool = wxService.getKefuService().sendKefuMessage(message);
		return bool;
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
		logger.info("code="+code);
		WxMpOAuth2AccessToken accessToken = wxService.oauth2getAccessToken(code);
		WxMpUser wxUser = wxService.oauth2getUserInfo(accessToken, null);
		//添加或更新用户信息
		WxUser wuser = new WxUser();
		logger.info("wxUser:"+wxUser+",time:"+wxUser.getSubscribeTime());
		BeanUtils.copyProperties(wxUser, wuser);
		//wuser.setSubscribeTime(new Timestamp(wxUser.getSubscribeTime()*1000l));
		userRepo.save(wuser);
		//把openId和unionId存入cookie
		Cookie foo = new Cookie("wxOpenId", wuser.getOpenId()); 
		foo.setMaxAge(365*24*3600);
		response.addCookie(foo);
		//根据state的不同导向到不同页面，带参数openId
		return "redirect:/resources/solidState/profile.html?openId="+wuser.getOpenId();
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
		return "redirect:/resources/solidState/profile.html";
	}
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public WxUser getUser(String openId) throws WxErrorException {
		WxMpUserService wus = wxService.getUserService();
		WxMpUser userWxInfo = wus.userInfo(openId, null);
		if (userWxInfo != null) {
			WxUser wuser = new WxUser();
			BeanUtils.copyProperties(userWxInfo, wuser);
			wuser.setSubscribeTime(new Timestamp(userWxInfo.getSubscribeTime()*1000l));
			return userRepo.save(wuser);
		}
		return null;
	}
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(@PathVariable String id) throws WxErrorException {
		return new JsonResponse(wxService.getUserService().userInfo(id, null));
	}

	@RequestMapping(value = "/getAllUsers")
	@ResponseBody
	public boolean getAllUsers() throws WxErrorException {
		boolean bool = false;
		WxMpUserService wus = wxService.getUserService();
		List<WxUser> list = new ArrayList<WxUser>();
		for (String openId : wus.userList(null).getOpenIds()) {
			WxMpUser userWxInfo = wus.userInfo(openId, null);
			if (userWxInfo != null) {
				WxUser wuser = new WxUser();
				BeanUtils.copyProperties(userWxInfo, wuser);
				wuser.setSubscribeTime(new Timestamp(userWxInfo.getSubscribeTime()*1000l));
				list.add(wuser);
			}
		}
		userRepo.save(list);
		return bool;
	}

	@PostMapping(produces = "application/xml; charset=UTF-8")
	@ResponseBody
	public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature) throws WxErrorException, IOException {
		this.logger.info(
				"\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}]," + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				signature, encType, msgSignature, timestamp, nonce, requestBody);
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
