package tacos.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	// somehow it configures authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(encoder());
	}

	// somehow it configures authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// order of calling antMatchers is important
		// some other options are (instead of permitAll or hasRole(String)):
		// access(String): evaluates its SpEL expression using other options and authentication and principal
		// anonymous(): allow access to anonymous users
		// authenticated(): allow access to authenticated users
		// denyAll(): no one
		// permitAll(): every one
		// fullyAuthenticated(): if user is not anonymous nor remember-me (cookie)
		// rememberMe()
		// hasAnyAuthority(String...) - hasAuthority(String)
		// hasAnyRole(String...) - hasRole(String)
		// hasIpAddress(String)
		// not()

		// for authentication just in Tuesdays
//				.access("hasRole('ROLE_USER') " +
//						"&& T(java.util.Calendar).getInstance().get(T(java.util.Calendar).DAY_OF_WEEK) " +
//						"== T(java.util.Calendar).TUESDAY")

		// and() means current authorization configuration is finished
		// .usernameParameter("user").passwordParameter("pwd") to customize user pass attribute names
		// .defaultSuccessUrl("/design", true); to force user to navigate even if he requested some other url
		http.authorizeRequests()
				.antMatchers("/design", "/orders").access("hasRole('ROLE_USER')")
				.antMatchers("/", "/**").access("permitAll()")
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/design")
				.and()
				.logout()
				.logoutSuccessUrl("/");
	}

}
