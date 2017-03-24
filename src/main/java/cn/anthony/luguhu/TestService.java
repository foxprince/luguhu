package cn.anthony.luguhu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.anthony.luguhu.service.ProductService;
import cn.anthony.luguhu.service.UserService;
import cn.anthony.luguhu.util.Constant;

@SpringBootApplication
public class TestService implements CommandLineRunner {
	@Autowired
	private ProductService service;
	@Resource
	UserService userService;

	public static void main(String[] args) {
		System.setProperty("DB.TRACE", "true");
		System.setProperty("DEBUG.MONGO", "true");
		SpringApplication.run(TestService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("run test");
		try {
			long t1 = System.currentTimeMillis();
			List l = userService.findByLevel(Constant.USER_LEVEL_PPRODUCER);
			System.out.println(l);
			long t2 = System.currentTimeMillis();
			System.out.println(t2 - t1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
