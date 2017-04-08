package cn.anthony.luguhu.wp.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.anthony.luguhu.util.JsonUtils;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Binary Wang
 */
@Component
public class LogHandler extends AbstractHandler {
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		this.logger.info("\n接收到请求消息，内容：{}", JsonUtils.toJson(wxMessage));
		try {
			WxMpUser userWxInfo = wxMpService.getUserService().userInfo(wxMessage.getFromUser(), null);
			logger.info("\n微信用户信息，{}", userWxInfo.toString());
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
}
