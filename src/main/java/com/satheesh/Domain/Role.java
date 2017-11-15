package com.satheesh.Domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "role")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "role_name")
	private String name;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "role_user")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || !(obj instanceof User)) {
			return false;
		}

		Role u = (Role) obj;

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
