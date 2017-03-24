package cn.anthony.luguhu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/**","/resources/**", "/api/**")
				.permitAll().antMatchers("/users/**").hasAuthority("ADMIN").anyRequest().fullyAuthenticated().and().formLogin()
				.loginPage("/login").failureUrl("/login?error").usernameParameter("email").permitAll().and().logout()
				.logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/").permitAll().and().rememberMe();
	}

}

class PasswordCrypto {
	@Autowired
	private PasswordEncoder passwordEncoder;
	private static PasswordCrypto instance;

	public static PasswordCrypto getInstance() {
		if (instance == null) {
			instance = new PasswordCrypto();
		}
		return instance;
	}

	public String encrypt(String str) {
		return passwordEncoder.encode(str);
	}
}