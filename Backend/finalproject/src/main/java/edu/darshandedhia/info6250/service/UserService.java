package edu.darshandedhia.info6250.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.dao.UserDao;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.Response;

@Component
public class UserService {
	
	@Autowired
	@Qualifier(value = "userDao")
	public UserDao userDao;
	
	public ResponseEntity<Object> addUserService(User user){
		Response response = userDao.registerUser(user);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
}
