package edu.darshandedhia.info6250.dao;

import static edu.darshandedhia.info6250.dao.DAO.fetchSession;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.exception.UserException;
import edu.darshandedhia.info6250.pojo.Group;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.Response;
import edu.darshandedhia.info6250.constants.*;
@Component
public class UserDao extends DAO{
	
	public Response registerUser(User user) {
		try {
		begin();
		userExist(user); //Check for user Exist. If user exists then it will throw UserException
		//BCrypt Password Hashing
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String bcryptPassword = bcrypt.encode(user.getPassword());
		user.setPassword(bcryptPassword);
		fetchSession().save(user);
		commit();
		return new Response(StatusCode.created, Status.success, Message.userCreated);
		} catch (UserException e) {
			return new Response(StatusCode.badRequest, Status.failure, e.getMessage());
		} catch (HibernateException he) {
			return new Response(StatusCode.internalServerError, Status.error, Message.userCreationError);
		} catch (Exception ex) {
			return new Response(StatusCode.internalServerError, Status.error, Message.userCreationError);
		} finally {
			close();
		}
	}
	
	// userExist method checks user with provided Username or email ID Exist?
	public boolean userExist(User user) throws UserException{
		List<User> users;
		//Username check
		Query q = fetchSession().createQuery("from User where userName =:userName");
		q.setParameter("userName", user.getUserName());
		users = q.list();
		if(users.size() > 0) throw new UserException("Username already exist");
		
		//EmailID Check
		q = fetchSession().createQuery("from User where email =:email");
		q.setParameter("email", user.getEmail());
		users = q.list();
		if(users.size() > 0) throw new UserException("Email already registered");
		
		return false;
	}
	
	public User getUserByUsername(String username) throws UserException{
		try {
		begin();
		Query q = fetchSession().createQuery("from User where userName =: username");
		q.setParameter("username", username);
		List<User> user = q.list();
		if (user.size() == 0) throw new UserException("No user exists with username: " + username);
		if (user.size() > 1) throw new UserException("Multiple users exist with username: " + username);
		User users = user.get(0);
		Hibernate.initialize(users.getFriends());
		Hibernate.initialize(users.getFriendsOf());
		Hibernate.initialize(users.getGroupList());
		return users;
		} catch (UserException ue) {
			throw ue;
		} finally {
			close();
		}
	}
	
	public User getUserByUserId(int userId) throws UserException, Exception {
		try {
			begin();
			User user = fetchSession().get(User.class, userId);
			if (user == null) throw new UserException(Message.userByUserIdNotFound);
			return user;
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public User updateUser(User user) {
		begin();
		fetchSession().update(user);
		fetchSession().getTransaction().commit();
		User userr = fetchSession().get(User.class, user.getUserId());
		close();
		return userr;
	}
}
