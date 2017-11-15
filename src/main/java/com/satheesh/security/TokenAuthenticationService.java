package com.satheesh.security;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class TokenAuthenticationService {
  public static final long EXPIRATIONTIME = 864_000_000; // 10 days
  public static final String SECRET = "ThisIsASecret";
  public static final String TOKEN_PREFIX = "Bearer";
  public static final String HEADER_STRING = "Authorization";
  

  private static Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

  //add authentication when login
  public static void addAuthentication(HttpServletResponse res, Authentication u) throws JsonGenerationException, JsonMappingException, IOException {
	  logger.info("\n******************Token Building************************\n");
  
	    UserDetail context = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Claims claims = Jwts.claims().setSubject(u.getName());
        claims.put("id", context.getId() + "");
        claims.put("role", context.getAuthorities());
	  
	  String JWT = Jwts.builder()
        .setClaims(claims)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
	  
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    res.setStatus(HttpStatus.OK.value());
    res.setContentType(MediaType.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(res.getOutputStream(), "Success");
    
  }

  
  public static Authentication getAuthentication(HttpServletRequest request) {
	  logger.info("\n******************PARSING TOKEN************************\n");
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      // parse the token.
      String user = Jwts.parser()
          .setSigningKey(SECRET)
          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
          .getBody()
          .getSubject();
      logger.info("\n******************USER OBJ FROM TOKEN************************\n"+user);
      return user != null ?
          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
          null;
    }
    return null;
  }
}