package edu.darshandedhia.info6250.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.config.JWTUtils;
import edu.darshandedhia.info6250.constants.Message;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.exception.UserException;
import edu.darshandedhia.info6250.pojo.Group;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.LoginResponse;
import edu.darshandedhia.info6250.response.Response;

@Component
public class AuthDao extends DAO{
	@Autowired
	@Qualifier(value = "userDao")
	public UserDao userDao;
	
	@Autowired
	@Qualifier(value = "jwtUtils")
	public JWTUtils jwtUtils;
	
	public Response authenticate(String userName, String password) {
		try {
			User user = userDao.getUserByUsername(userName);
			Collection<Group> groupList = user.getGroupList();
			Set<User> friendsList = new HashSet<User>();
			for(User friend : user.getFriends())
				friendsList.add(friend);
			for(User friendOf : user.getFriendsOf())
				friendsList.add(friendOf);
			
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			if (bcrypt.matches(password, user.getPassword())) {
				//If raw supplied password matches with already stored encoded password
				JWTUtils utl = new JWTUtils();
				String token = utl.generateUserJWTToken(userName, user.getUserId());
				
				return new LoginResponse(StatusCode.success, Status.success, Message.userLoggedIn, token, user, groupList, friendsList);
			} else {
				//If supplied password doesn't match
				return new Response(StatusCode.loginPasswordMismatch, Status.failure, Message.loginPasswordMismatch);
			}
			
		} catch(UserException ue) {
			return new Response(StatusCode.badRequest, Status.failure, ue.getMessage());
		} catch(Exception e){ 
			e.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.userLogInError); 
		}
			 
	}
		
}
