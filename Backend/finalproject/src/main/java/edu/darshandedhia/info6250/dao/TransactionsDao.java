package edu.darshandedhia.info6250.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import edu.darshandedhia.info6250.constants.Message;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.exception.DatabaseException;
import edu.darshandedhia.info6250.exception.UserException;
import edu.darshandedhia.info6250.pojo.Group;
import edu.darshandedhia.info6250.pojo.Transaction;
import edu.darshandedhia.info6250.pojo.TransactionDetails;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.GroupResponse;
import edu.darshandedhia.info6250.response.Reports;
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
			String sqlQuery = "select * from transactions t, transaction_details td1, transaction_details td2 where td1.TRANSACTION_ID = td2.TRANSACTION_ID and t.PAYMENT_IND_GRP_ID = 0 and t.TRANSACTION_ID = td1.TRANSACTION_ID and td1.USER_ID = :userId1 and td2.USER_ID = :userId2";
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
	
	public Response fetchFriendSums(int userId, int friendUserId) {
		try {
			begin();
			String sqlQuery = "select 0 as id, tdd.USER_ID as user, SUM(tdd.OWN_SHARE) as ownShare, SUM(tdd.PAID) as paid, 0 as percentage, 0 as transaction from transaction_details tdd where tdd.TRANSACTION_ID in\r\n" + 
					"(select t.TRANSACTION_ID from transactions t, transaction_details td1, transaction_details td2 \r\n" + 
					"where td1.TRANSACTION_ID = td2.TRANSACTION_ID and t.PAYMENT_IND_GRP_ID = 0 \r\n" + 
					"and t.TRANSACTION_ID = td1.TRANSACTION_ID and td1.USER_ID =  :userId\r\n" + 
					"and td2.USER_ID = :friendUserId)\r\n" + 
					"group by tdd.USER_ID";
			NativeQuery<Reports> q = fetchSession().createNativeQuery(sqlQuery);
			q.setParameter("userId", userId);
			q.setParameter("friendUserId", friendUserId);
			q.setResultTransformer(Transformers.aliasToBean(Reports.class));
			List<Reports> td = (List<Reports>)q.list();
			return new ResponseObject(StatusCode.success, Status.success, Status.success, td); 
		} catch (HibernateException he) {
			he.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.databaseExceptionOccured);
		} catch (Exception e) {
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
			fetchSession().saveOrUpdate(transaction);
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
	
	public Response deleteTransaction(int transactionId) {
		System.out.println("Transaction Id: " + transactionId);
		try {
			begin();
				System.out.println("Deletion Started");
				String transDel = "DELETE FROM TRANSACTIONS where TRANSACTION_ID= :transactionId";
				String transDetDel = "DELETE FROM TRANSACTION_DETAILS where TRANSACTION_ID= :transactionId";
				System.out.println("Deleting TD");
	            NativeQuery<TransactionDetails> query = fetchSession().createNativeQuery(transDetDel, TransactionDetails.class);
	            query.setParameter("transactionId", transactionId);
	            if(query.executeUpdate() == 0) throw new DatabaseException("Unable to delete Transaction Details");
	            
	            System.out.println("Deleting Transaction");
	            NativeQuery<Transaction> query1 = fetchSession().createNativeQuery(transDel, Transaction.class);
	            query1.setParameter("transactionId", transactionId);
	            
	            if(query1.executeUpdate() == 0) throw new DatabaseException("Unable to delete Transaction");
	            
	            commit();
	          
			return new Response(StatusCode.success, Status.success, Message.recordDeletedSuccessfully);
		} catch (DatabaseException de) {
			rollback();
			de.printStackTrace();
			return new Response(StatusCode.badRequest, Status.failure, de.getMessage());
		} catch (HibernateException hfe) {
			hfe.printStackTrace();
			rollback();
			return new Response(StatusCode.internalServerError, Status.error, Message.databaseExceptionOccured);
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return new Response(StatusCode.internalServerError, Status.error, Message.internalServerError);
		} finally {
			close();
		}
	}
	
	public Response fetchGroupTransactions(int groupId) {
		try {
			begin();
			String sqlQuery = "select t.* from transactions t, group_details gd where t.PAYMENT_IND_GRP_ID = gd.GROUP_ID and gd.GROUP_ID = :groupId";
			NativeQuery<Transaction> q = fetchSession().createNativeQuery(sqlQuery, Transaction.class);
			q.setParameter("groupId", groupId);
			List<Transaction> transactions = q.list();
			
			Query qry = fetchSession().createQuery("from Group where groupId = :groupId");
			qry.setParameter("groupId", groupId);
			List<Group> groups = qry.list();
			Group group = groups.get(0);
			Hibernate.initialize(group.getUserList());
			
			String nativeQry = "select u.* from user u, group_user_details gud where u.USER_ID = gud.USER_ID and gud.GROUP_ID =  :groupId";
			NativeQuery<User> u = fetchSession().createNativeQuery(nativeQry, User.class);
			u.setParameter("groupId", groupId);
			List<User> users = new ArrayList<User>();
			users = u.list();
			
			
			return new GroupResponse(StatusCode.success, Status.success, Status.success, group, transactions, users); 
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
	
	public Response fetchGroupSums(int userId, int groupId) {
		try {
			begin();
			String sqlQuery = "select 0 as id, td.USER_ID as user, SUM(td.OWN_SHARE) as ownShare, SUM(td.PAID) as paid, 0 as percentage, 0 as transaction from transactions t, transaction_details td \r\n" + 
					"where t.PAYMENT_IND_GRP_ID = :groupId\r\n" + 
					"and td.TRANSACTION_ID = t.TRANSACTION_ID group by td.user_id";
			NativeQuery<Reports> q = fetchSession().createNativeQuery(sqlQuery);
			q.setParameter("groupId", groupId);
			q.setResultTransformer(Transformers.aliasToBean(Reports.class));
			List<Reports> td = (List<Reports>)q.list();
			return new ResponseObject(StatusCode.success, Status.success, Status.success, td); 
		} catch (HibernateException he) {
			he.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.databaseExceptionOccured);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.internalServerError);
		} finally {
			close();
		}
	}
	
	public Response fetchSelfTotals(int userId) {
		try {
			begin();
			String sqlQuery = "select 0 as id, td.USER_ID as user, ifnull(sum(ifnull(td.OWN_SHARE, 0)), 0) as ownShare, ifnull(SUM(ifnull(td.PAID, 0)), 0) as paid, 0 as percentage, 0 as transaction \r\n" + 
					"from transaction_details td where td.USER_ID = :userId";
			NativeQuery<Reports> q = fetchSession().createNativeQuery(sqlQuery);
			q.setParameter("userId", userId);
			q.setResultTransformer(Transformers.aliasToBean(Reports.class));
			List<Reports> td = (List<Reports>)q.list();
			Reports report = td.get(0);
			return new ResponseObject(StatusCode.success, Status.success, Status.success, report); 
		} catch (HibernateException he) {
			he.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.databaseExceptionOccured);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.internalServerError);
		} finally {
			close();
		}
	}
	
	
	public Response fetchCategorisedTotals(int userId) {
		try {
			begin();
			String sqlQuery = "select ifnull(t.CATEGORY, 'UNCATEGORISED'), ifnull(sum(ifnull(td.own_share,0)),0) from transactions t, transaction_details td\r\n" + 
					"where t.TRANSACTION_ID = td.TRANSACTION_ID and td.USER_ID = :userId\r\n" + 
					"group by t.CATEGORY";
			NativeQuery<Object> q = fetchSession().createNativeQuery(sqlQuery);
			q.setParameter("userId", userId);
			List<Object> td = q.list();
			return new ResponseObject(StatusCode.success, Status.success, Status.success, td); 
		} catch (HibernateException he) {
			he.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.databaseExceptionOccured);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(StatusCode.internalServerError, Status.error, Message.internalServerError);
		} finally {
			close();
		}
	}
	
	
}
