package com.satheesh.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserCredentials {
	
	private Long id;
	private String email;
	private String password;
	private List<GrantedRole> roles;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<GrantedRole> getRoles() {
		return roles;
	}
	public void setRoles(List<GrantedRole> roles) {
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<GrantedRole> getAuthorities(Set<Role> roles) {
		
	    List<GrantedRole> authorities= new ArrayList<>();
	    for (Role role: roles) {
	      authorities.add(new GrantedRole(role.getName()));
	    }
	     
	    return authorities;
	}
	
	

}
