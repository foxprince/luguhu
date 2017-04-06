package cn.anthony.luguhu.wp;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

/**
 * @author Binary Wang
 */
@RestController
@RequestMapping("/wp/portal")
public class WechatController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WxMpService wxService;
	@Autowired
	private WxMpMessageRouter router;

	/**
     * 初始化路由过滤规则
     */
    @PostConstruct 
    public void init() {
      router
//          .rule()
//              .async(false)
//              .msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE) // 微信推送过来的消息的类型，和发送给微信xml格式消息的消息类型
//              .handler(subscribeHandler)
//              .end()
//          .rule()
//              .async(false)
//              .msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_UNSUBSCRIBE)
//              .handler(unsubscribeHandler)
//              .end()
//          .rule()
//              .async(false)
//              .msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_VIEW)
//              .handler(viewHandler)
//              .end()
//          .rule()
//              .async(false)
//              .msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_CLICK)
//              .handler(clickHandler)
//              .end()
          .rule()
              .async(false)
              .msgType(WxConsts.XML_MSG_TEXT)
              .handler(new WxMpMessageHandler(){
					@Override
					public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
							WxSessionManager sessionManager) throws WxErrorException {
						WxMpXmlOutTextMessage out = new WxMpXmlOutTextMessage();
						out.setContent("echo:"+wxMessage.getContent());
						out.setToUserName(wxMessage.getFromUser());
						out.setFromUserName(wxMessage.getToUser());
						out.setCreateTime(System.currentTimeMillis() / 1000l);
						logger.info(out.toXml());
						return out;
					}})
              .end();
    } 
	@GetMapping(produces = "text/plain;charset=utf-8")
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

	@PostMapping(produces = "application/xml; charset=UTF-8")
	public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature) {
		this.logger.info(
				"\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}]," + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				signature, encType, msgSignature, timestamp, nonce, requestBody);
		if (!this.wxService.checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}
		String out = null;
		if (encType == null) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
			WxMpXmlOutMessage outMessage = this.route(inMessage);
			if (outMessage == null) {
				return "";
			}
			out = outMessage.toXml();
		} else if ("aes".equals(encType)) {
			// aes加密的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, this.wxService.getWxMpConfigStorage(), timestamp,
					nonce, msgSignature);
			this.logger.info("\n消息解密后内容为：\n{} ", inMessage.toString());
			WxMpXmlOutMessage outMessage = this.route(inMessage);
			if (outMessage == null) {
				return "";
			}
			out = outMessage.toEncryptedXml(this.wxService.getWxMpConfigStorage());
		}
		this.logger.info("\n组装回复信息：{}", out);
		return out;
	}

	private WxMpXmlOutMessage route(WxMpXmlMessage message) {
		try {
//			WxMpMessageInterceptor interceptor;
//			WxMpMessageHandler handler;
//			router.rule().msgType("MSG_TYPE").event("EVENT").eventKey("EVENT_KEY").content("CONTENT").interceptor(interceptor)
//					.handler(handler).end().rule()
//					// 另外一个匹配规则
//					.end();
			return this.router.route(message);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}
		return null;
	}
}
class SubscribeHandle implements WxMpMessageHandler {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
