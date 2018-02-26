package cn.anthony.luguhu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@PropertySource(value = "classpath:application-${env}.properties", encoding = "UTF-8")
//@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
// @EnableSwagger2
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}
}