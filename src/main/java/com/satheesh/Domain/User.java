package com.satheesh.Domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstname;

	private String lastname;

	private String email;

	
	private String password;



	@OneToMany(fetch=FetchType.EAGER,mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Role> roles;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Phonenumber> phonenumbers;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Phonegroup> group;
	
	public User() {
		this.roles = new HashSet<>();
		this.phonenumbers = new HashSet<>();
		this.group = new HashSet<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Phonenumber> getPhonenumbers() {
		return phonenumbers;
	}

	public void setPhonenumbers(Set<Phonenumber> phonenumbers) {
		this.phonenumbers = phonenumbers;
	}

	public Set<Phonegroup> getGroup() {
		return group;
	}

	public void setGroup(Set<Phonegroup> group) {
		this.group = group;
	}
	
	//helper classes
	
	//helper class to add role
	public void addRole(Role r) {
		this.roles.add(r);
		r.setUser(this);
	}
	
	//helper class to add phonenumber
	
	public void addPhoneNumber(Phonenumber number) {
		this.phonenumbers.add(number);
		number.setUser(this);
	}
	
	//helper class to create Group
	
	public void addGroup(Phonegroup g) {
		this.group.add(g);
		g.setUser(this);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || !(obj instanceof User)) {
			return false;
		}

		User u = (User) obj;

		if (this.getId() == null || u.getId() == null)
			return false;

		if (this.getId() == u.getId())
			return true;

		return false;
	}

	@Override
	 public int hashCode() {
	        if (id != null) {
	            return id.hashCode();
	        } else {
	            return super.hashCode();
	        }
	    }


	
	
}
