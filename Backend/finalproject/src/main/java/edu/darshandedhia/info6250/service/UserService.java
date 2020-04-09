package edu.darshandedhia.info6250.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.dao.UserDao;
import edu.darshandedhia.info6250.pojo.ApiError;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.ErrorResponse;
import edu.darshandedhia.info6250.response.Response;
import edu.darshandedhia.info6250.validator.UserValidator;


@Component
public class UserService {
	
	@Autowired
	@Qualifier(value = "userDao")
	public UserDao userDao;
	
	@Autowired
	@Qualifier(value = "userValidator")
	public UserValidator userValidator;
	
	public ResponseEntity<Object> addUserService(User user, BindingResult result){
		
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			ArrayList<ApiError> errorList = new ArrayList<>();
			for(ObjectError obj : result.getAllErrors()) {
				errorList.add(new ApiError(obj.getCode(), obj.getDefaultMessage()));
			}
			return new ResponseEntity<Object>(new ErrorResponse(StatusCode.inappropriateInputFormat, Status.failure, errorList), HttpStatus.BAD_REQUEST);
			}
		Response response = userDao.registerUser(user);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
}
