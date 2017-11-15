package com.satheesh.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class AuthorizationFilter extends BasicAuthenticationFilter {

	private static Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
	
    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
		logger.info("\n******************Authorizing filter************************\n");
        String header = req.getHeader(TokenAuthenticationService.HEADER_STRING);

        System.out.println(header);
        if (header == null) {
            chain.doFilter(req, res);
            return;
        }

        Authentication authentication = TokenAuthenticationService.getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}
