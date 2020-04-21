package edu.darshandedhia.info6250.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.darshandedhia.info6250.constants.Message;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.dao.UserDao;
import edu.darshandedhia.info6250.exception.UserException;
import edu.darshandedhia.info6250.pojo.Mail;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.Response;
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
	public ResponseEntity<Object> addUser(@RequestBody User user, BindingResult result){
		return userService.addUserService(user, result);
	}
	
	
	@RequestMapping(value = "/addFriend/{friendId}", produces = "application/json", method=RequestMethod.GET)
	public ResponseEntity<Object> addFriend(@PathVariable("friendId") String friendUserName, HttpServletRequest request){
		try {
			
			String selfUserName =  request.getAttribute("userName").toString();
			System.out.println(selfUserName);
			if (selfUserName != null || selfUserName != "") {
				System.out.println(selfUserName);
				return userService.addFriend(selfUserName, friendUserName);
			} else {
				return new ResponseEntity<Object>(new Response(StatusCode.inappropriateInputFormat, Status.failure, Message.malFormedApiUrlRequest), HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	@RequestMapping(value = "/mail", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> mailUser(@RequestBody Mail mail){
		return userService.sendMail(mail);
	}
	
}
