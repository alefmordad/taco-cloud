package tacos.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("buzz")
				.password("infinity")
				.authorities("ROLE_USER")
				.and()
				.withUser("woody")
				.password("bullseye")
				.authorities("ROLE_USER");
	}

}
