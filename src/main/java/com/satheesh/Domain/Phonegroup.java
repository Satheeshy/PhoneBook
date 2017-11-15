package com.satheesh.Domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name="phonegroup")
public class Phonegroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="group_id")
	private Long id;
	
	@Column(name="group_name")
	private String Name;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	
	@OneToMany(mappedBy="group",orphanRemoval = false,fetch=FetchType.EAGER)
	private Set<Phonenumber> phonenumbers;
	
	
	public Phonegroup() {
		// TODO Auto-generated constructor stub
		this.phonenumbers = new HashSet<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Phonenumber> getPhonenumbers() {
		return phonenumbers;
	}

	public void setPhonenumbers(Set<Phonenumber> phonenumbers) {
		this.phonenumbers = phonenumbers;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj) return true;
		
		if(obj == null ||!(obj instanceof User)) {
			return false;
		}
		
		Phonegroup u = (Phonegroup) obj;
		
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
