package com.springboot.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class MyConfig {

	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
		daoAuthenticationProvider.setPasswordEncoder(PasswordEncoder());

		return daoAuthenticationProvider;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**",
				"/img/**", "/lib/**", "/favicon.ico","/oauth2");

	}

//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//			throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}

	@Bean

	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//             declares which Page(URL) will have What access type
		    http.authorizeHttpRequests()
		        .requestMatchers("/signup")
		        .authenticated().requestMatchers("/contact")
		        .authenticated().requestMatchers("/admin").hasRole("ADMIN")
		        .requestMatchers("/user").hasRole("USER")
		        .requestMatchers("/**").permitAll().anyRequest().authenticated()
                       // Login Form Details
				.and().formLogin().loginPage("/signin").loginProcessingUrl("/user/index")
				       // Login Form Details
//				.and().formLogin().defaultSuccessUrl("/user/index", true)

			           // Logout Form Details
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

				       // Exception Details
				.and().exceptionHandling().accessDeniedPage("/accessDenied");
		return http.build();
	}

}