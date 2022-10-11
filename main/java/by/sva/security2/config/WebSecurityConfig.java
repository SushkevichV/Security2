package by.sva.security2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import by.sva.security2.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	//@Autowired
	private UserService userService;
	//@Autowired
	private PasswordEncoder passwordEncoder;
	
	public WebSecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			//.csrf().disable() //отключить проверку csrf-токена -> https://sysout.ru/csrf-token/
			.authorizeRequests()
				.antMatchers("/registration").not().fullyAuthenticated() // Доступ только для не зарегистрированных пользователей
				.antMatchers("/admin/**").hasRole("ADMIN") //Доступ только для пользователей с ролью ADMIN
				.antMatchers("/news").hasRole("USER") //Доступ только для пользователей с ролью USER
				.antMatchers("/", "/resources/**").permitAll() //Доступ разрешен всем пользователям
			.anyRequest().authenticated() //Все остальные страницы требуют аутентификации
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll()
			.and()
				.logout()
				.permitAll()
				.logoutSuccessUrl("/");
	}
	
	@Override
	//protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	/*
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests((requests) -> requests
					.antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/news").hasRole("USER")
					.antMatchers("/", "/index", "/login").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin((form) -> form
					.loginPage("/login")
					.permitAll()
			)
			.logout((logout) -> logout.permitAll());
		return http.build();
	}
	*/
	/*
	// создать пользователя - устарел
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
								.username("user")
								.password("password")
								.roles("USER")
								.build();
		return new InMemoryUserDetailsManager(user);
	}
	*/

}
