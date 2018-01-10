package cn.anthony.luguhu;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;

import cn.anthony.luguhu.domain.WxUser;
import cn.anthony.luguhu.repository.AssetRepository;
import cn.anthony.luguhu.repository.WxUserRepository;
import cn.anthony.luguhu.service.AssetService;
import me.chanjar.weixin.mp.api.WxMpService;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TestService implements CommandLineRunner {
	@Autowired
	private WxUserRepository userRepo;
	@Autowired
	private AssetService assetServ;
	@Resource
	AssetRepository repsitory;
	@Resource
	private WxPayService wxPayService;
	@Autowired
	private WxMpService wxService;//注意，此类的bean声明是通过WechatMpConfiguration实现的
	public static void main(String[] args) {
		System.setProperty("DB.TRACE", "true");
		SpringApplication.run(TestService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String openId = "o6AWbjpi4e7MRmXP4qYQpN5zSoIM";
		WxUser wxUser = userRepo.findByOpenId(openId);
		System.out.println(repsitory.findByWxUserAndTagsNotNull(wxUser, new PageRequest(0, 1, Sort.Direction.DESC, "id")));
	    WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
	    orderRequest.setBody("翡翠湾产品主题");
	    orderRequest.setOutTradeNo("fcw00001");
	    orderRequest.setTotalFee(1);//元转成分
	    orderRequest.setOpenid(openId);
	    //orderRequest.setNotifyURL(notifyURL);
	    orderRequest.setTradeType("JSAPI");
		orderRequest.setSpbillCreateIp("103.10.87.204");
	    System.out.println(wxPayService.unifiedOrder(orderRequest));
		System.out.println("run test");
	}

}
