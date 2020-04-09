package edu.darshandedhia.info6250.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.dao.AuthDao;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.Response;

@Component
public class AuthService {
	@Autowired
	@Qualifier(value = "authDao")
	public AuthDao authDao;
	
	public ResponseEntity<Object> authenticate(User user){
		Response response = authDao.authenticate(user.getUserName(), user.getPassword());
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
}
