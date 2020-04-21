package edu.darshandedhia.info6250.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import edu.darshandedhia.info6250.constants.Message;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.pojo.Group;
import edu.darshandedhia.info6250.pojo.TransactionDetails;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.Response;
import edu.darshandedhia.info6250.response.ResponseObject;

public class GroupDao extends DAO{
	@Autowired
	@Qualifier(value="userDao")
	private UserDao userDao;
	
	public Response addUpdateGroup(Group group) {
		try {
			List<User> userList = new ArrayList<>();
			for(User u : group.getUserList()) {
				User user = new User();
				user = userDao.getUserByUserId(u.getUserId());
				userList.add(user);
			}
			group.setUserList(userList);
			begin();
			fetchSession().saveOrUpdate(group);
			fetchSession().getTransaction().commit();
			return new ResponseObject(StatusCode.success, Status.success, Message.groupCreatedSuccessfully, group);
		} catch(HibernateException he) {
			he.printStackTrace();
			return new Response(StatusCode.badRequest, Status.error, Message.databaseExceptionOccured);
		} catch(Exception e) {
			e.printStackTrace();
			return new Response(StatusCode.badRequest, Status.error, Message.internalServerError);
		} finally {
			close();
		}
	}
}
