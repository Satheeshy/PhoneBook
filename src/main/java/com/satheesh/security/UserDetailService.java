package com.satheesh.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.satheesh.Domain.GrantedRole;
import com.satheesh.Domain.Role;
import com.satheesh.Service.UserService;

@Service
public class UserDetailService implements UserDetailsService{

	private static Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
	
	@Autowired
	private UserService userService;
	
	private 	com.satheesh.Domain.User user;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("\n******************Loading User By email************************\n");
		
		
			user = userService.getUserByEmail(email);
			List<GrantedRole> r = getAuthorities(user.getRoles());
			UserDetail u = new UserDetail(user.getId(),user.getEmail(), user.getPassword(), true, true, true, true,r);
			System.out.println("*********UserDetailService:"+u);
			return u;
		
	}
	private List<GrantedRole> getAuthorities(Set<Role> roles) {
		
			    List<GrantedRole> authorities= new ArrayList<>();
			    for (Role role: roles) {
			      authorities.add(new GrantedRole(role.getName()));
			    }
			     
			    return authorities;
			}

}
