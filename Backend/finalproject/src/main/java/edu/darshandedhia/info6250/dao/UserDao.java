package edu.darshandedhia.info6250.dao;

import static edu.darshandedhia.info6250.dao.DAO.fetchSession;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.exception.UserException;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.Response;
import edu.darshandedhia.info6250.constants.*;
@Component
public class UserDao extends DAO{
	
	public Response registerUser(User user) {
		try {
		begin();
		userExist(user); //Check for user Exist. If user exists then it will throw UserException
		fetchSession().save(user);
		commit();
		return new Response(StatusCode.created, Status.success, Message.userCreated);
		} catch (UserException e) {
			return new Response(StatusCode.badRequest, Status.failure, e.getMessage());
		} catch (HibernateException he) {
			return new Response(StatusCode.internalServerError, Status.error, Message.userCreationError);
		} finally {
			close();
		}
	}
	
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

}
