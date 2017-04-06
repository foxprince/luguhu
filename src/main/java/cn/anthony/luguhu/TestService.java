package cn.anthony.luguhu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestService implements CommandLineRunner {

	public static void main(String[] args) {
		System.setProperty("DB.TRACE", "true");
		SpringApplication.run(TestService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("run test");
	}

}
