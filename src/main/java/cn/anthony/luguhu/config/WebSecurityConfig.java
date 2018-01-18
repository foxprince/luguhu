package cn.anthony.luguhu.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Build the request matcher for CSFR protection
		// http://blog.netgloo.com/2014/09/28/spring-boot-enable-the-csrf-check-selectively-only-for-some-requests/
		RequestMatcher csrfRequestMatcher = new RequestMatcher() {
			// Disable CSFR protection on the following urls:
			private RequestMatcher[] requestMatchers = {new IpAddressMatcher("127.0.0.1")};//,new AntPathRequestMatcher("/resources/**"),new AntPathRequestMatcher("/asset/preview/**"),new AntPathRequestMatcher("/wp/**"),new AntPathRequestMatcher("/api/**") };
			@Override
			public boolean matches(HttpServletRequest request) {
				for (RequestMatcher rm : requestMatchers) {
					if (rm.matches(request)) {
						return false;
					}
				}
				return true;
			} 
		}; 
		// Set security configurations
		//http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher);
		//http.csrf().ignoringAntMatchers("/wp/**").and().authorizeRequests().antMatchers( "/api/**", "/wp/**")
				//.permitAll();
		// .antMatchers("/users/**").hasAuthority("ADMIN").anyRequest().fullyAuthenticated().and().formLogin()
		// .loginPage("/login").failureUrl("/login?error").usernameParameter("email").permitAll().and().logout()
		// .logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/").permitAll().and().rememberMe();
		
		//  简单粗暴的方式：禁用csrf
		http.csrf().disable();
		
		http.authorizeRequests()
		.antMatchers("/api.txt","/*.html","/bootstrap/**","/dist/**","/pages/**","/solidState/**","/plugins/**","/rest/**","/api/**", "/wp/**").permitAll()
		.antMatchers("/user/login","/login").permitAll()
		.anyRequest().fullyAuthenticated()
		.and()
		.formLogin().loginPage("/login").failureUrl("/login?error")
			.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
		.exceptionHandling().accessDeniedPage("/access?error");
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("test").roles("USER");
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