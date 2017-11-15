package com.satheesh.Domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="address")
public class Address implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	private String Street;	
	private String city;
	private String State;	
	private String zip;
	
	@JsonIgnore
	@OneToOne(mappedBy = "address")
	private Phonenumber number;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Phonenumber getNumber() {
		return number;
	}
	public void setNumber(Phonenumber number) {
		this.number = number;
	}
	@Override
	public boolean equals(Object obj) {
	if(this == obj) return true;
		
		if(obj == null ||!(obj instanceof User)) {
			return false;
		}
		
		Address u = (Address) obj;
		
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
