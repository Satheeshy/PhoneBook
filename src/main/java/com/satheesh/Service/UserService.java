package com.satheesh.Service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.satheesh.Domain.Phonegroup;
import com.satheesh.Domain.Phonenumber;
import com.satheesh.Domain.Role;
import com.satheesh.Domain.User;
import com.satheesh.Exception.PasswordIncorrect;

@Repository
@Transactional
public class UserService {
	
	@Autowired
	private SessionFactory session;
	public static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Session getSession() {
		return session.getCurrentSession();
	}
	
	//create User
	public Long addUser(User u) {
		
		return (Long) getSession().save(u);
		
	}
	
	public void addRole(String role_name,Long user_id) {
		Role r = new Role();
		r.setName(role_name);
		logger.info("................\nUSER ID"+user_id+"\n...............");
		r.setId(user_id);
		getSession().save(r);
	}
	
	public User CheckUser(String email,String password) throws PasswordIncorrect  {
		isUserExists(getUserByEmail(email));
		Query q = getSession().createQuery("from com.satheesh.Domain.User where email=:em and password=:p");
		q.setParameter("em", email);
		q.setParameter("p",password);
		List<User> list=  q.list();
		
		if(list.isEmpty()) throw new PasswordIncorrect("Password doesn't match");
		User u = list.get(0);
		return u;
	}
	
	public User getUserByEmail(String email) {
		Query q = getSession().createQuery("from com.satheesh.Domain.User where email=:e");
		System.out.println(email);
		q.setParameter("e", email);
		List<User> list=  q.list();
		if(list.isEmpty()) return null;
	
		User u = list.get(0);
		return u;
	}
	
	public boolean isUserExists(User u) {
	       Query q = getSession().createQuery("from com.satheesh.Domain.User");
	       List<User> dbUsers = q.list();
	       for(User user : dbUsers) {
	    	       if(user.getEmail().equals(u.getEmail())) {
	    	    	   return true;
	    	       }
	       }
	       return false;
	       
	}
	
	public User getUser(Long id) {
		return getSession().get(User.class, id);
	}
	
	//addPhoneNumber
	public void addPhoneNumber(Long user_id,Phonenumber ph) {
		User u = getSession().get(User.class,user_id);
		u.addPhoneNumber(ph);
		getSession().update(u);
		
	}
	
	public Phonenumber getPhoneNumber(Long id) {
		return getSession().get(Phonenumber.class, id);
	}
	
	
	//delete phoneNumber
	public void deletePhoneNumber(Long phone_id) {
		Phonenumber ph = getSession().get(Phonenumber.class,phone_id);
		getSession().delete(ph);
	}
	
	public void createGroup(Phonegroup group,Long user_id) {
		User u = getUser(user_id);
		group.setUser(u);
		getSession().persist(group);
	}
	
	public void addNumberToGroup(Long phone_id,Long group_id) {
		Phonenumber phone = getSession().get(Phonenumber.class, phone_id);
		Phonegroup group = getSession().get(Phonegroup.class, group_id);
	    phone.setGroup(group);
	    updatePhoneNumber(phone);
		
	}
	
	//get phoengroup
	public Phonegroup getPhoneGroup(Long group_id) {
		return getSession().get(Phonegroup.class,group_id);
	}
	
	//delete phonenumber from a group
	public void deletePhoneNumberFromGroup(Long group_id) {
		Query q = getSession().createQuery("from com.satheesh.Domain.Phonenumber where group.id=:g");
		q.setParameter("g",group_id);
		List<Phonenumber> ph = q.list();
		for(Phonenumber p : ph) {
			p.setGroup(null);
			getSession().update(p);
		}
	}
	
	//delete singlenumber from a group
	
	public void deleteNumberFromGroup(Long group_id,Long phone_id) {
		Query q = getSession().createQuery("from com.satheesh.Domain.Phonenumber where group.id=:g");
		q.setParameter("g",group_id);
		List<Phonenumber> ph = q.list();
		
		for(Phonenumber p : ph) {
			System.out.println("\n****************delete number************\n"+p.getGroup().getId());
			if(p.getGroup().getId() == group_id) {
				p.setGroup(null);
				getSession().update(p);
				break;
			}
		}
	}
	
	//delete the group
	
	public void deletePhoneGroup(Long g) {
	    deletePhoneNumberFromGroup(g);
	    Phonegroup group = getSession().get(Phonegroup.class, g);
	    getSession().delete(group);
	}
	
	//update the user
	public void updateUser(User u) {
		getSession().update(u);
	}
	
	public void deleteUser(User u) {
		getSession().delete(u);
	}
	
	public void updatePhoneNumber(Phonenumber number) {
		Phonenumber p = getSession().get(Phonenumber.class, number.getId());
		p.setName(number.getName());
		p.getAddress().setCity(number.getAddress().getCity());
		p.getAddress().setState(number.getAddress().getState());
		p.getAddress().setStreet(number.getAddress().getStreet());
		p.getAddress().setZip(number.getAddress().getZip());
		p.setEmail(number.getEmail());
		p.setPhonenumber(number.getPhonenumber());
		System.out.println("****\n******"+p.getName() + p.getEmail() + p.getEmail() + p.getAddress().getCity() + p.getAddress().getState() + p.getAddress().getStreet());
		getSession().update(p);
	}

	public List<Phonenumber> getAllPhoneNumbersOfAGroup(Long group_id){
		return (List<Phonenumber>) getSession().createQuery("from com.satheesh.Domain.Phonenumber where group.id=:a").setParameter("a",group_id).list();
	}
	
	public List<Phonenumber> getAllPhoneNumbersOfUser(Long user_id){
		Query q =  getSession().createQuery("from com.satheesh.Domain.Phonenumber where user.id=:u");
		q.setParameter("u", user_id);
		
		List<Phonenumber> numbers = q.list();
		if(numbers.isEmpty()) return null;
		return numbers;
	}
	
	
	public List<Phonegroup> getListOfGroups(Long user_id){
		Query q =  getSession().createQuery("from com.satheesh.Domain.Phonegroup where user.id=:u");
		q.setParameter("u", user_id);
		List<Phonegroup> groups = q.list();
		return groups;
	}
	
	//delete later
	public List<Phonegroup> getGroupIdOfPhone(Long phone_id) {
		//get all the groups of a phone id
		List<Phonegroup> groups = new ArrayList<>();
		Query q=  getSession().createQuery("from com.satheesh.Domain.Phonenumber where id=:a ");
		q.setParameter("a", phone_id);
		List<Phonenumber> ph = q.list();
		if(ph.isEmpty())return null;
		for(Phonenumber p : ph) {
			groups.add(p.getGroup());
		}
		
		return groups;
		
	}
	
	public String resetPassword(Long user_id,String oldpassword,String np) {
		User u = getSession().get(User.class, user_id);
		if(!u.getPassword().equals(oldpassword)) {
			return "wrongpassword";
		}
		else {
			u.setPassword(np);
			return "success";
		}
	}

}
