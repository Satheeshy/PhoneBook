package com.satheesh.Domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="phonenumber")
public class Phonenumber implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="phone_id")
	private Long id;
	@Column(name="phone_number")
	private String phonenumber;
	private String email;
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@OneToOne(cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="address_id")
	private Address address;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="group_id")
	private Phonegroup group;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Phonegroup getGroup() {
		return group;
	}

	public void setGroup(Phonegroup group) {
		this.group = group;
	}
	
	
	@Override
	public boolean equals(Object obj) {
	if(this == obj) return true;
		
		if(obj == null ||!(obj instanceof User)) {
			return false;
		}
		
		Phonenumber u = (Phonenumber) obj;
		
		if(this.getId() == null || u.getId() == null) return false;
		
		if(this.getId() == u.getId()) return true;
		
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
