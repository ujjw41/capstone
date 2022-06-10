package chatbot.chatbot.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
//	private final UserDetailsService userDetailsService;
//	private final BCryptPasswordEncoder bCryptPasswordEncoder;

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();//.formLogin();
//		http.sessionManagement().sessionCreationPolicy(STATELESS);
//		http.authorizeRequests().antMatchers("/0/register", "/0/refresh/token/**").permitAll();
//		http.authorizeRequests().antMatchers(GET,"/0/student/**").hasAnyAuthority("ROLE_USER");
//		http.authorizeRequests().antMatchers(GET,"/0/students/all", "/0/users/all").hasAnyAuthority("ROLE_ADMIN");
//		http.authorizeRequests().anyRequest().authenticated();
//		http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
//		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/", "/web", "/styles/**","/bot.png" ,"/user.jpg", "/api/**","/web/register")
				.permitAll()
				.antMatchers("/admin/**")
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

	//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception{
//		return super.authenticationManagerBean();
//	}
}
