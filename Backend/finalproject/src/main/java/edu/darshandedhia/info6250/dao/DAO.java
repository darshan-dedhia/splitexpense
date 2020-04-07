package edu.darshandedhia.info6250.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DAO {
	
	private static final ThreadLocal sessionThread = new ThreadLocal();
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    
    protected DAO() {}
    
    public static Session fetchSession() {
    	Session session = (Session) DAO.sessionThread.get();

        if (session == null) {
            session = sessionFactory.openSession();
            DAO.sessionThread.set(session);
        }
        return session;
    }
    
    protected void begin() {
    	fetchSession().beginTransaction();
    }
    
    protected void commit() {
    	fetchSession().getTransaction().commit();
    }
    
    protected void rollback() {
    	fetchSession().getTransaction().rollback();
    }
    
    public static void close() {
    	fetchSession().close();
    	DAO.sessionThread.set(null);
    }
}
