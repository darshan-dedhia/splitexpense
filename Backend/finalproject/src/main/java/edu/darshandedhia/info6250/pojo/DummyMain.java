package edu.darshandedhia.info6250.pojo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
			 * User user01 = session.get(User.class, 1); User user02 =
			 * session.get(User.class,2); User user03 = session.get(User.class, 3);
			 * 
			 * user01.getFriends().add(user02);
			 * 
			 * 
			 * user02.getFriends().add(user03);
			 * 
			 * 
			 * user03.getFriends().add(user02);
			 * 
			 * 
			 * session.update(user01); session.update(user03); session.update(user02);
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
			 * System.out.println(user.getName()); }
			 * 
			 * for(User user : user01.getFriendsOf()) {
			 * System.out.println("User 1 is friend of: ");
			 * System.out.println(user.getName()); }
			 * 
			 * for(User user : user02.getFriends()) {
			 * System.out.println("Friends of User 2: ");
			 * System.out.println(user.getName()); }
			 * 
			 * for(User user : user02.getFriendsOf()) {
			 * System.out.println("User 2 is friend of: ");
			 * System.out.println(user.getName()); }
			 */
			
			/*
			 * User user01 = session.get(User.class, 1); User user02 =
			 * session.get(User.class, 2);
			 * 
			 * Transaction transaction = new Transaction();
			 * transaction.setCategory("Grocery");
			 * transaction.setDescription("Bill for Target 04/06/2020");
			 * transaction.setPaymentIndividualOrGroupId(0);
			 * 
			 * TransactionDetails td1 = new TransactionDetails(); td1.setUser(user01);
			 * td1.setTransaction(transaction); td1.setOwnShare(50); td1.setPaid(100);
			 * 
			 * TransactionDetails td2 = new TransactionDetails(); td2.setUser(user02);
			 * td2.setTransaction(transaction); td2.setOwnShare(50); td2.setPaid(0);
			 * 
			 * transaction.getTransactionDetails().add(td2);
			 * transaction.getTransactionDetails().add(td1);
			 * 
			 * session.save(td1); session.save(td2); session.save(transaction);
			 */

			User user01 = session.get(User.class, 1);
			User user02 = session.get(User.class, 2);
			
			Group group = new Group();
			group.setGroupName("Flatmates");
			group.getUserList().add(user01);
			group.getUserList().add(user02);
			
			session.save(group);
			session.getTransaction().commit();
			
			User user001 = session.get(User.class, 1);
			User user002 = session.get(User.class, 2);
			
			for(Group group001 : user001.getGroupList()) {
				System.out.println(group001.getGroupId() + "  " + group001.getGroupName());
			}
			
			for(Group group002 : user002.getGroupList()) {
				System.out.println(group002.getGroupId() + "  " + group002.getGroupName());
			}
			
			
		} catch (HibernateException he) {
			System.out.println(he);
		} finally {
			session.close();
		}

	}

}
