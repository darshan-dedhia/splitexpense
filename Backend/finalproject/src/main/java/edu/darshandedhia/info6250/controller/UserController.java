package edu.darshandedhia.info6250.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
//	Service Class Auto-wiring 
	@Autowired
	@Qualifier(value="userService")
	private UserService userService;
	
//	Controller Methods declaration	
	@RequestMapping(value = "/addUser", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> addUser(@RequestBody User user){
		return userService.addUserService(user);
	}
}
