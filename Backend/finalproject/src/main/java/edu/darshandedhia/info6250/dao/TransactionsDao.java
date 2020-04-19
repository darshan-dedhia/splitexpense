package edu.darshandedhia.info6250.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.constants.Message;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.pojo.Transaction;
import edu.darshandedhia.info6250.pojo.TransactionDetails;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.Response;
import edu.darshandedhia.info6250.response.ResponseObject;

@Component
public class TransactionsDao extends DAO{
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	
	public Response fetchFriendTransactions(int userId, int friendUserid) {
		try {
			begin();
			String sqlQuery = "select * from transactions t, transaction_details td1, transaction_details td2 where td1.TRANSACTION_ID = td2.TRANSACTION_ID and t.TRANSACTION_ID = td1.TRANSACTION_ID and td1.USER_ID = :userId1 and td2.USER_ID = :userId2";
			NativeQuery<Transaction> q = fetchSession().createNativeQuery(sqlQuery, Transaction.class);
			q.setParameter("userId1", userId);
			q.setParameter("userId2", friendUserid);
			List<Transaction> transactions = q.list();
			
			return new ResponseObject(StatusCode.success, Status.success, Status.success, transactions); 
		} catch (HibernateException he) {
			he.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.databaseExceptionOccured);
		} catch (Exception e){
			e.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.internalServerError);
		} finally {
			close();
		}
	}
	
	public Response addTransaction(Transaction transaction) {
		try {
			for(TransactionDetails td : transaction.getTransactionDetails()) {
				User user = new User();
				user = userDao.getUserByUserId(td.getUser().getUserId());
				td.setUser(user);
			}
			begin();
			fetchSession().save(transaction);
			fetchSession().getTransaction().commit();
			return new ResponseObject(StatusCode.success, Status.success, Status.success, transaction);
		} catch(HibernateException he) {
			return new Response(StatusCode.internalServerError, Status.error, Message.databaseExceptionOccured);
		} catch(Exception e){
			e.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.internalServerError);
		} finally {
			close();
		}
	}
}
