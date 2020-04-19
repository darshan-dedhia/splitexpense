package edu.darshandedhia.info6250.pojo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

public class DummyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		try {

			session.beginTransaction();
			
			
			/*
			 * User user1 = new User(); user1.setUserName("user1");
			 * user1.setPassword("user1password"); user1.setName("User1Name");
			 * user1.setEmail("user1@email.com");
			 * 
			 * User user2 = new User(); user2.setUserName("user2");
			 * user2.setPassword("user2password"); user2.setName("User2Name");
			 * user2.setEmail("user2@email.com");
			 * 
			 * User user3 = new User(); user3.setUserName("user3");
			 * user3.setPassword("user3password"); user3.setName("User3Name");
			 * user3.setEmail("user3@email.com");
			 * 
			 * 
			 * session.save(user1); session.save(user2); session.save(user3);
			 */
			  
			  
			 // session.getTransaction().commit();
			 
			
			
			
			
			
			/*
			 * User user01 = session.get(User.class, 9); User user02 =
			 * session.get(User.class, 8); User user03 = session.get(User.class, 7); User
			 * user04 = session.get(User.class, 6); User user05 = session.get(User.class,
			 * 5);
			 * 
			 * user01.getFriends().add(user02); user01.getFriends().add(user03);
			 * user01.getFriends().add(user04); user01.getFriends().add(user05);
			 * 
			 * user02.getFriends().add(user01); user02.getFriends().add(user03);
			 * user02.getFriends().add(user04); user02.getFriends().add(user05);
			 * 
			 * user03.getFriends().add(user01); user03.getFriends().add(user02);
			 * user03.getFriends().add(user04); user03.getFriends().add(user05);
			 * 
			 * user04.getFriends().add(user01); user04.getFriends().add(user02);
			 * user04.getFriends().add(user03); user04.getFriends().add(user05);
			 * 
			 * user05.getFriends().add(user01); user05.getFriends().add(user02);
			 * user05.getFriends().add(user04); user05.getFriends().add(user03);
			 * 
			 * session.update(user01); session.update(user02);
			 * session.update(user03);session.update(user04); session.update(user05);
			 * 
			 * 
			 * session.getTransaction().commit();
			 */ 
			 
			
			
			
			/*
			 * User user01 = session.get(User.class, 1); User user02 =
			 * session.get(User.class,2); User user03 = session.get(User.class, 3);
			 * 
			 * for(User user : user01.getFriends()) {
			 * System.out.println("Friends of User 1: ");
			 * System.out.println(user.getUserName()); }
			 * 
			 * for(User user : user01.getFriendsOf()) {
			 * System.out.println("User 1 is friend of: ");
			 * System.out.println(user.getUserName()); }
			 * 
			 * for(User user : user02.getFriends()) {
			 * System.out.println("Friends of User 2: ");
			 * System.out.println(user.getUserName()); }
			 * 
			 * for(User user : user02.getFriendsOf()) {
			 * System.out.println("User 2 is friend of: ");
			 * System.out.println(user.getUserName()); }
			 */
			 
			
			
			
			/*
			 * User user01 = session.get(User.class, 1); User user02 =
			 * session.get(User.class, 2);
			 * 
			 * Transaction transaction = new Transaction();
			 * transaction.setCategory("Grocery2");
			 * transaction.setDescription("Bill for Target 04/14/2020");
			 * transaction.setPaymentIndividualOrGroupId(0);
			 * 
			 * TransactionDetails td1 = new TransactionDetails(); td1.setUser(user01);
			 * td1.setTransaction(transaction); td1.setOwnShare(50); td1.setPaid(75);
			 * 
			 * TransactionDetails td2 = new TransactionDetails(); td2.setUser(user02);
			 * td2.setTransaction(transaction); td2.setOwnShare(50); td2.setPaid(25);
			 * 
			 * transaction.getTransactionDetails().add(td2);
			 * transaction.getTransactionDetails().add(td1); session.persist(transaction);
			 * //session.save(td1); session.save(td2); session.save(transaction);
			 * session.getTransaction().commit();
			 */
			 
			 

			
			
			/*
			 * User user01 = session.get(User.class, 5); User user02 =
			 * session.get(User.class, 6); User user03 = session.get(User.class, 7); User
			 * user04 = session.get(User.class, 8); User user05 = session.get(User.class,
			 * 9);
			 * 
			 * Group group = new Group(); group.setGroupName("Flatmates NEU");
			 * group.getUserList().add(user01); group.getUserList().add(user02);
			 * group.getUserList().add(user03); group.getUserList().add(user04);
			 * group.getUserList().add(user05);
			 * 
			 * session.save(group); session.getTransaction().commit();
			 * 
			 */
			 
			  
			//Query q = session.createQuery("from Transaction as t, TransactionDetails as td1, TransactionDetails as td2 where td1.transaction = td2.transaction and td1.transaction = t.transactionId and td1.user =: userId1 and td2.user =: userId2 ");
			/*
			 * String sqlQuery =
			 * "select * from transactions t, transaction_details td1, transaction_details td2 where td1.TRANSACTION_ID = td2.TRANSACTION_ID and t.TRANSACTION_ID = td1.TRANSACTION_ID and td1.USER_ID = :userId1 and td2.USER_ID = :userId2"
			 * ; NativeQuery<Transaction> q = session.createNativeQuery(sqlQuery,
			 * Transaction.class); q.setParameter("userId1", 1); q.setParameter("userId2",
			 * 2); List<Transaction> transaction = q.list();
			 * System.out.println(transaction.size());
			 * System.out.println(transaction.get(0).getTransactionDetails().size());
			 */
			 
			
		} catch (HibernateException he) {
			System.out.println(he);
		} finally {
			session.close();
		}

	}

}
