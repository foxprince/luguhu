package cn.anthony.luguhu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
//@ComponentScan(basePackages = {"cn.anthony.luguhu","me.chanjar.weixin.mp"})
// @EnableSwagger2
public class Application extends SpringBootServletInitializer {
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(Application.class);
//	}

//	@Resource
//	static WpConfig wpConfig;
//	
//	@Bean public static WxMpService wxMpService() {
//		WxMpService wms =  new WxMpServiceImpl();
//		//设置微信参数
//		wms.setWxMpConfigStorage(wpConfig);
//		return wms;
//	}
//	@Bean public WxMpMessageRouter wxMpMessageRouter() {
//		return new WxMpMessageRouter(wxMpService());
//	}
//	@Bean public static WxMpMenuService wxMpMenuService() {
//		return new WxMpMenuServiceImpl(wxMpService());
//	}
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
//		WxMenu menu = new WxMenu();
//		List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();
//		menu.setButtons(buttons);
//		try {
//			wxMpMenuService().menuCreate(menu);
//		} catch (WxErrorException e) {
//			e.printStackTrace();
//		}
		app.run(args);
	}
}