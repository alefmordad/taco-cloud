package tacos.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class JdbcSecurityConfig extends WebSecurityConfigurerAdapter {

	private final DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// first query is similar to JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY
		// second is similar to JdbcDaoImpl.DEF_AUTHORITIES_BY_USERNAME_QUERY
		// groupAuthoritiesByUsername is similar to JdbcDaoImpl.DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from Users where username=?")
				.authoritiesByUsernameQuery("select username, authority from UserAuthorities where username=?")
				.groupAuthoritiesByUsername("???")
				.passwordEncoder(new StandardPasswordEncoder("53crt"));
	}

}
