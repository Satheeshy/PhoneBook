package com.satheesh.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satheesh.Domain.UserCredentials;

public class LoginFilter extends AbstractAuthenticationProcessingFilter{

	private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	
	
	private AuthenticationManager authManager;
	protected LoginFilter(String url,AuthenticationManager a) {
		super(new AntPathRequestMatcher(url));
		this.authManager = a;
	}
	
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		logger.info("\n******************Authenticating api/login************************\n");
		UserCredentials user = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
		//pass credentials to authentication manager
		
		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),user.getRoles());
		
		
		Authentication auth = this.authManager.authenticate(creds);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		//If username and password is correct
		logger.info("\n******************Successfull Authentication --- api/login************************\n");
		
		TokenAuthenticationService.addAuthentication(response,authResult);
	}
	
	

}
