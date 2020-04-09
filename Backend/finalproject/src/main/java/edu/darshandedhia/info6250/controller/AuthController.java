package edu.darshandedhia.info6250.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.service.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	@Qualifier(value="authService")
	private AuthService authService;
	
	@RequestMapping(value = "/login", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> authenticate(@RequestBody User user){
		return authService.authenticate(user);
	}
}
