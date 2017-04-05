package cn.anthony.luguhu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@SpringBootApplication
//@ComponentScan(basePackages = {"cn.anthony.luguhu","me.chanjar.weixin.mp"})
// @EnableSwagger2
public class Application extends SpringBootServletInitializer {
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(Application.class);
//	}

	@Bean
	public WxMpService wxMpService() {
		return new WxMpServiceImpl();
	}
	@Bean public WxMpMessageRouter WxMpMessageRouter() {
		return new WxMpMessageRouter(wxMpService());
	}
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}
}