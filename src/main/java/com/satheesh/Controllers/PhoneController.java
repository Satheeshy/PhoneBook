package com.satheesh.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.satheesh.Domain.Phonegroup;
import com.satheesh.Domain.Phonenumber;
import com.satheesh.Domain.Role;
import com.satheesh.Domain.User;
import com.satheesh.Exception.EmailAlreadyExist;
import com.satheesh.Service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PhoneController {

	public static final Logger logger = LoggerFactory.getLogger(PhoneController.class);

	@Autowired
	private UserService repository;
	@Autowired
	private JavaMailSender emailSender;

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		logger.info("..........GETTING USER DETAILS........" + id);
		User u = repository.getUser(id);
		return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/checkemail", method = RequestMethod.GET)
	public ResponseEntity<?> checkEmail(@RequestParam(value = "email", required = false) String email)
			throws EmailAlreadyExist {
		if (repository.getUserByEmail(email) != null) {
			return new ResponseEntity<String>("failure", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
	}

	// sign up for user
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User u, UriComponentsBuilder urlbuilder) throws EmailAlreadyExist {
		logger.info("......creating a user......");

		if (repository.isUserExists(u)) {
			logger.error("Unable to create..Email already exist");
			throw new EmailAlreadyExist("Email is already Taken");
		}

		Role r = new Role();
		r.setName("USER");
		u.addRole(r);

		Long user_id = repository.addUser(u);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(urlbuilder.path("/api/user/{id}").buildAndExpand(user_id).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestBody User u) {
		repository.updateUser(u);
		return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
	}

	// get particular phonenumber
	@RequestMapping(value = "/view/phone/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> showPhoneDetails(@PathVariable Long id) {
		Phonenumber p = repository.getPhoneNumber(id);
		return new ResponseEntity<Phonenumber>(p, HttpStatus.CREATED);
	}

	// get all phonenumbers of a user
	@RequestMapping(value = "/phone/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Phonenumber>> getAllPhonenumbersOfUser(@PathVariable Long id) {
		List<Phonenumber> numbers = repository.getAllPhoneNumbersOfUser(id);
		if(numbers == null) return new ResponseEntity<List<Phonenumber>>(HttpStatus.ACCEPTED);
		logger.info(numbers.get(0).getPhonenumber());
		return new ResponseEntity<List<Phonenumber>>(numbers, HttpStatus.ACCEPTED);
	}

	// get all phonenumbers of a group
	@RequestMapping(value = "/view/group/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Phonenumber>> getPhoneNumbersOfAGroup(@PathVariable Long id) {
		List<Phonenumber> numbers = repository.getAllPhoneNumbersOfAGroup(id);
		return new ResponseEntity<List<Phonenumber>>(numbers, HttpStatus.ACCEPTED);
	}
	// add phonenumber

	@RequestMapping(value = "/addphone/{user_id}", method = RequestMethod.POST)
	public ResponseEntity<?> addPhoneNumber(@RequestBody Phonenumber number, @PathVariable Long user_id) {
		repository.addPhoneNumber(user_id, number);
		return new ResponseEntity<Phonenumber>(number, HttpStatus.CREATED);
	}

	// Created a Group

	@RequestMapping(value = "/group/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> createGroup(@PathVariable Long id, @RequestBody Phonegroup group) {
		repository.createGroup(group, id);
		return new ResponseEntity<Phonegroup>(group, HttpStatus.CREATED);
	}

	// add PhoneNumbers To a Group
	@RequestMapping(value = "/add/{phone_id}/{group_id}")
	public ResponseEntity<?> addPhoneNumberToGroup(@PathVariable Long phone_id, @PathVariable Long group_id) {
		repository.addNumberToGroup(phone_id, group_id);
		Phonegroup group = repository.getPhoneGroup(group_id);
		return new ResponseEntity<Phonegroup>(group, HttpStatus.ACCEPTED);
	}

	// get all groups of user

	@RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> showgroup(@PathVariable Long id) {
		List<Phonegroup> group = repository.getListOfGroups(id);
		return new ResponseEntity<List<Phonegroup>>(group, HttpStatus.ACCEPTED);

	}

	// delete a phonenumber
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletephonenumber(@PathVariable Long id) {
		repository.deletePhoneNumber(id);
		return new ResponseEntity<String>("deleted", HttpStatus.OK);

	}

	// update phonenumber

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> updatephonenumber(@RequestBody Phonenumber number) {
		repository.updatePhoneNumber(number);
		System.out.println("****\n******"+number);
		Phonenumber p = repository.getPhoneNumber(number.getId());
		return new ResponseEntity<Phonenumber>(p, HttpStatus.OK);

	}

	// delete group
	@RequestMapping(value = "/delete/group/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		repository.deletePhoneGroup(id);
		return new ResponseEntity<String>("deleted", HttpStatus.OK);

	}

	// delete number from a group
	@RequestMapping(value = "/delete/number/{group_id}/{phone_id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNumberFromGroup(@PathVariable Long group_id, @PathVariable Long phone_id) {

		repository.deleteNumberFromGroup(group_id, phone_id);
		return new ResponseEntity<String>("removed", HttpStatus.OK);

	}

	@RequestMapping(value = "/group/phone/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Phonegroup>> getGroupNameOfNumber(@PathVariable Long id) {
		List<Phonegroup> groups = repository.getGroupIdOfPhone(id);
		System.out.println(groups.size());
		return new ResponseEntity<List<Phonegroup>>(groups, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/checkpassword")
	public ResponseEntity<?> checkPassword(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		User u = repository.getUserByEmail(email);
		System.out.println("********************" + u.getPassword());
		System.out.println("********************" + password);
		if (!u.getPassword().equals(password)) {
			return new ResponseEntity<String>("wrongpassword", HttpStatus.OK);
		}
		return new ResponseEntity<String>("allowreset", HttpStatus.OK);
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ResponseEntity<?> resetPassword(@RequestParam("id") Long id, @RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword) {

		String msg = repository.resetPassword(id, oldpassword, newpassword);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	@RequestMapping("/sendemail")
	public ResponseEntity<?> sendEmail(@RequestParam String email) {
		User user = repository.getUserByEmail(email);
		
        try {
			sendNotification(user, email);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@Async
	public void sendNotification(User user,String email) throws InterruptedException {
		
		System.out.println("Sleeping now...");
        Thread.sleep(10000);
		
        System.out.println("Sending email...");
        
        String username = user.getFirstname() + " " + user.getLastname();
		String password = user.getPassword();

		String subject = "Reset Password : Welcome To Drop Number";
		String msg = String.format("Hello %s,\n\tPlease enter this password to login: %s", username, password);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(subject);
		message.setText(msg);
		emailSender.send(message);
		
		System.out.println("Email Sent!");
		
	}

}
