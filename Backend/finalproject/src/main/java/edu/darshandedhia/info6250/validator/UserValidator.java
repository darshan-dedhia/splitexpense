package edu.darshandedhia.info6250.validator;

import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.pojo.User;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "error.invalid.userName", "userName Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "password Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.invalid.name", "name required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.email", "Email required");
	}
	
}
