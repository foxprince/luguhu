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

import cn.anthony.luguhu.domain.WxUser;
import cn.anthony.luguhu.repository.AssetRepository;
import cn.anthony.luguhu.repository.WxUserRepository;
import cn.anthony.luguhu.service.AssetService;

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
	public static void main(String[] args) {
		System.setProperty("DB.TRACE", "true");
		SpringApplication.run(TestService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String openId = "o6AWbjpi4e7MRmXP4qYQpN5zSoIM";
		WxUser wxUser = userRepo.findByOpenId(openId);
		System.out.println(repsitory.findByWxUserAndTagsNotNull(wxUser, new PageRequest(0, 1, Sort.Direction.DESC, "id")));
		System.out.println("run test");
	}

}
