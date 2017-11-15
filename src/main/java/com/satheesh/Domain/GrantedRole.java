package com.satheesh.Domain;

import org.springframework.security.core.GrantedAuthority;

public class GrantedRole implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	private String authority;

	public GrantedRole() {

	}

	public GrantedRole(String authority) {
		super();
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
