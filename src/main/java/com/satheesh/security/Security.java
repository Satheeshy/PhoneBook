package com.satheesh.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
@Configuration
public class Security extends WebSecurityConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(Security.class);

	@Autowired
	private UserDetailsService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// hasauthority(user) or hasrole(role_admin) or hasanyauthority(admin,admin2)

		logger.info("\n******************Entering web security configurer method************************\n");
		http.cors().and().csrf().disable().authorizeRequests()

//				// anonymous people can access
//				.antMatchers("/**").permitAll().antMatchers("/api/checkemail").permitAll()
//				.antMatchers("/api/signup").permitAll()
//				.antMatchers("/api/sendemail").permitAll()
				
				// anonymous people can access
				.antMatchers(HttpMethod.GET,
                        "/",
                        "/**/*.html",
                        "/**/*.{png,jpg,jpeg,svg.ico}",
                        "/**/*.css",
                        "/**/*.js").permitAll().antMatchers("/api/checkemail").permitAll()
				.antMatchers("/api/signup").permitAll()
				.antMatchers("/api/sendemail").permitAll()

				// other request should be authenticated
				.anyRequest().authenticated().and()
				
				// We filter the api/login requests
				.addFilterBefore(new LoginFilter("/api/login",this.authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new AuthorizationFilter(this.authenticationManager()),
						UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","UPDATE"));
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addExposedHeader("Authorization");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	
	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("\n******************Authentication Manager Builder************************\n");
		auth.authenticationProvider(authenticationProvider());
	}

	

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		// one of the List<AuthenticationProvider> of providermanager implementation of
		// Authentication Manager
		logger.info("\n******************Building Authentication Provider************************\n");
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		return authProvider;
	}

}
