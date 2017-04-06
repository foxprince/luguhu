package cn.anthony.luguhu.wp;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * @author Binary Wang
 * edit by zj
 */
@Configuration
@ConditionalOnClass(WxMpService.class)
public class WechatMpConfiguration {
	@Resource
	WpConfig wpConfig;
	
	
    @Bean @ConditionalOnMissingBean public WxMpService wxMpService() throws WxErrorException {
		WxMpService wms =  new WxMpServiceImpl();
		//设置微信参数
		wms.setWxMpConfigStorage(wpConfig);
		return wms;
	}
//    
//    @Bean WxMpMenuService wxMenuService(WxMpService wxMpService) {
//    	final WxMpMenuService menuService = new WxMpMenuServiceImpl(wxMpService);
//    	StringBuilder sb = new StringBuilder();
//    	sb.append("{menu:{");
//    	sb.append("    \"button\": [");
//    	sb.append("        {");
//    	sb.append("            \"name\": \"扫码\", ");
//    	sb.append("            \"sub_button\": [");
//    	sb.append("                {");
//    	sb.append("                    \"type\": \"scancode_waitmsg\", ");
//    	sb.append("                    \"name\": \"扫码带提示\", ");
//    	sb.append("                    \"key\": \"rselfmenu_0_0\", ");
//    	sb.append("                    \"sub_button\": [ ]");
//    	sb.append("                }, ");
//    	sb.append("                {");
//    	sb.append("                    \"type\": \"scancode_push\", ");
//    	sb.append("                    \"name\": \"扫码推事件\", ");
//    	sb.append("                    \"key\": \"rselfmenu_0_1\", ");
//    	sb.append("                    \"sub_button\": [ ]");
//    	sb.append("                }");
//    	sb.append("            ]");
//    	sb.append("        }, ");
//    	sb.append("        {");
//    	sb.append("            \"name\": \"发图\", ");
//    	sb.append("            \"sub_button\": [");
//    	sb.append("                {");
//    	sb.append("                    \"type\": \"pic_sysphoto\", ");
//    	sb.append("                    \"name\": \"系统拍照发图\", ");
//    	sb.append("                    \"key\": \"rselfmenu_1_0\", ");
//    	sb.append("                   \"sub_button\": [ ]");
//    	sb.append("                 }, ");
//    	sb.append("                {");
//    	sb.append("                    \"type\": \"pic_photo_or_album\", ");
//    	sb.append("                    \"name\": \"拍照或者相册发图\", ");
//    	sb.append("                    \"key\": \"rselfmenu_1_1\", ");
//    	sb.append("                    \"sub_button\": [ ]");
//    	sb.append("                }, ");
//    	sb.append("                {");
//    	sb.append("                    \"type\": \"pic_weixin\", ");
//    	sb.append("                    \"name\": \"微信相册发图\", ");
//    	sb.append("                    \"key\": \"rselfmenu_1_2\", ");
//    	sb.append("                    \"sub_button\": [ ]");
//    	sb.append("                }");
//    	sb.append("            ]");
//    	sb.append("        }, ");
//    	sb.append("        {");
//    	sb.append("            \"name\": \"发送位置\", ");
//    	sb.append("            \"type\": \"location_select\", ");
//    	sb.append("            \"key\": \"rselfmenu_2_0\"");
//    	sb.append("        },");
//    	sb.append("        {");
//    	sb.append("           \"type\": \"media_id\", ");
//    	sb.append("           \"name\": \"图片\", ");
//    	sb.append("           \"media_id\": \"MEDIA_ID1\"");
//    	sb.append("        }, ");
//    	sb.append("        {");
//    	sb.append("           \"type\": \"view_limited\", ");
//    	sb.append("           \"name\": \"图文消息\", ");
//    	sb.append("           \"media_id\": \"MEDIA_ID2\"");
//    	sb.append("        }");
//    	sb.append("    ]");
//    	sb.append("}}");
//    	WxMenu menu = WxMenu.fromJson(sb.toString());
//    	try {
//			menuService.menuCreate(menu.toJson());
//		} catch (WxErrorException e) {
//			e.printStackTrace();
//		}
//    	return menuService;
//    }
//    
    @Bean 
    public WxMpMessageRouter router(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.EVT_KF_CREATE_SESSION)
            .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.EVT_KF_CLOSE_SESSION).handler(this.kfSessionHandler)
            .end();
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.EVT_KF_SWITCH_SESSION)
            .handler(this.kfSessionHandler).end();
        
        // 门店审核事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.EVT_POI_CHECK_NOTIFY)
            .handler(this.storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.BUTTON_CLICK).handler(this.menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.BUTTON_VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.EVT_SUBSCRIBE).handler(this.subscribeHandler)
            .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.EVT_UNSUBSCRIBE)
            .handler(this.unsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.EVT_LOCATION).handler(this.locationHandler)
            .end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_LOCATION)
            .handler(this.locationHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
            .event(WxConsts.EVT_SCAN).handler(this.scanHandler).end();

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
