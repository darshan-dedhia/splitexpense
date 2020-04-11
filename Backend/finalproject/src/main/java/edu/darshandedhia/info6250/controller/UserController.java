package edu.darshandedhia.info6250.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.darshandedhia.info6250.dao.UserDao;
import edu.darshandedhia.info6250.exception.UserException;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
//	Service Class Auto-wiring 
	@Autowired
	@Qualifier(value="userService")
	private UserService userService;
	
	@Autowired
	@Qualifier(value="userDao")
	private UserDao userDao;
	
//	Controller Methods declaration	
	@RequestMapping(value = "/addUser", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> addUser(@RequestBody User user, BindingResult result){
		return userService.addUserService(user, result);
	}
	
	//This is dummy method. Needs code cleaning post testing
	@RequestMapping(value = "/valUser", produces = "application/json", method=RequestMethod.POST)
	public void valUser(HttpServletRequest request){
		System.out.println("Validating user");
		System.out.println(request.getAttribute("userName").toString());
		System.out.println(System.currentTimeMillis());
	}
	
	@RequestMapping(value = "/addFriend", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> addFriend(HttpServletRequest request){
		System.out.println("AddFriend here:");
		try {
			System.out.println("UserName: " + request.getAttribute("userName").toString());
			System.out.println("Friend: " + request.getAttribute("friend").toString());
			User user = userDao.getUserByUsername(request.getAttribute("userName").toString());
			User friend = userDao.getUserByUsername(request.getAttribute("friend").toString());
			user.getFriends().add(friend);
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/updateUser", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> updateUser(@RequestBody User user){
		User userr = userDao.updateUser(user);
		return new ResponseEntity<Object>(userr, HttpStatus.OK);
	}
}
