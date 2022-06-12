package chatbot.chatbot.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/", "/web/", "/styles/**", "/bot.png", "/user.jpg", "/background.jpg", "/api/**", "/web/register", "/web/student/register")
				.permitAll()
				.antMatchers("**/student/**")
				.hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
				.antMatchers("**/admin/**")
				.hasAnyAuthority("ROLE_ADMIN")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
//				.loginPage("/login")
//				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
				.logout()
				.permitAll();
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.jdbcAuthentication()
				.passwordEncoder(new BCryptPasswordEncoder())
				.dataSource(dataSource)
				.usersByUsernameQuery("SELECT username, password, enabled FROM user where username=?")
				.authoritiesByUsernameQuery("SELECT username, role FROM user where username=?");
	}


}
