package cn.anthony.luguhu.wp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

import cn.anthony.luguhu.wp.handler.KfSessionHandler;
import cn.anthony.luguhu.wp.handler.LocationHandler;
import cn.anthony.luguhu.wp.handler.LogHandler;
import cn.anthony.luguhu.wp.handler.MenuHandler;
import cn.anthony.luguhu.wp.handler.MsgHandler;
import cn.anthony.luguhu.wp.handler.NullHandler;
import cn.anthony.luguhu.wp.handler.ScanHandler;
import cn.anthony.luguhu.wp.handler.StoreCheckNotifyHandler;
import cn.anthony.luguhu.wp.handler.SubscribeHandler;
import cn.anthony.luguhu.wp.handler.UnsubscribeHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * wechat mp configuration
 *
 * @author Binary Wang edit by zj
 */
@Configuration // 此注解用于注入bean，特别是在jar包之内的第三方类
//@ConditionalOnClass(value = { WxMpService.class, WxPayService.class })
public class WechatMpConfiguration {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public static final String EVT_KF_CREATE_SESSION = "kf_create_session"; // 客服接入会话
	public static final String EVT_KF_CLOSE_SESSION = "kf_close_session"; // 客服关闭会话
	public static final String EVT_KF_SWITCH_SESSION = "kf_switch_session"; // 客服转接会话
	public static final String EVT_POI_CHECK_NOTIFY = "poi_check_notify"; // 门店审核事件推送
	@Autowired
	WxProperties wpConfig;
	@Autowired
	WxPayProperties wxPayConfig;
	@Value("classpath:wpMenu.json")
	Resource wxMenuResource;

	@Bean
	@ConditionalOnMissingBean
	public WxPayService wxPayService() throws WxErrorException {
		WxPayService wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(wxPayConfig);
		return wxPayService;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public WxMpService wxMpService() throws WxErrorException {
		WxMpService wms = new WxMpServiceImpl();
		// 设置微信参数
		wms.setWxMpConfigStorage(wpConfig);
		return wms;
	}

	@Bean
	public WxMpMessageRouter router(WxMpService wxMpService) {
		logger.debug("init wechat router with service : " + wxMpService);
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
		// 记录所有事件的日志 （异步执行）
		newRouter.rule().handler(this.logHandler).next();
		// 接收客服会话管理事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(EVT_KF_CREATE_SESSION).handler(this.kfSessionHandler)
				.end();
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(EVT_KF_CLOSE_SESSION).handler(this.kfSessionHandler)
				.end();
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(EVT_KF_SWITCH_SESSION).handler(this.kfSessionHandler)
				.end();
		// 门店审核事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(EVT_POI_CHECK_NOTIFY)
				.handler(this.storeCheckNotifyHandler).end();
		// 自定义菜单事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.CLICK).handler(this.menuHandler)
				.end();
		// 点击菜单连接事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.VIEW).handler(this.menuHandler)
				.end();
		// 关注事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)
				.handler(this.subscribeHandler).end();
		// 取消关注事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE)
				.handler(this.unsubscribeHandler).end();
		// 上报地理位置事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.LOCATION)
				.handler(this.locationHandler).end();
		// 接收地理位置消息
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).handler(this.locationHandler).end();
		// 扫码事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN).handler(this.scanHandler).end();
		// 默认
		newRouter.rule().async(false).handler(this.msgHandler).end();
		return newRouter;
	}

	@Autowired
	private LocationHandler locationHandler;
	@Autowired
	private ScanHandler scanHandler;
	@Autowired
	private MenuHandler menuHandler;
	@Autowired
	private MsgHandler msgHandler;
	@Autowired
	protected LogHandler logHandler;
	@Autowired
	protected NullHandler nullHandler;
	@Autowired
	protected KfSessionHandler kfSessionHandler;
	@Autowired
	protected StoreCheckNotifyHandler storeCheckNotifyHandler;
	@Autowired
	private UnsubscribeHandler unsubscribeHandler;
	@Autowired
	private SubscribeHandler subscribeHandler;
}
