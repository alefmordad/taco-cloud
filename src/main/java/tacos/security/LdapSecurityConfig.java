package tacos.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LdapSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> ldapAuth = auth.ldapAuthentication()
				.userSearchBase("ou=people")
				.userSearchFilter("(uid={0})")
				.groupSearchBase("ou=groups")
				.groupSearchFilter("member={0}");
		ldapAuth.contextSource()
				// embedded ldap server with user info file
				.root("dc=tacocloud,dc=com")
				.ldif("classpath:users.ldif");
		// remote ldap server
//				.url("ldap://tacocloud.com:389/dc=tacocloud,dc=com");
		ldapAuth.passwordCompare()
				.passwordEncoder(new BCryptPasswordEncoder())
				// attribute in user's ldap entry
				.passwordAttribute("passcode");
	}

}
