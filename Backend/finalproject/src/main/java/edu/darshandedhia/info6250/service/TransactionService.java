package edu.darshandedhia.info6250.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.dao.TransactionsDao;
import edu.darshandedhia.info6250.pojo.Transaction;

@Component
public class TransactionService {
	
	@Autowired
	@Qualifier("transactionsDao")
	private TransactionsDao transactionsDao;
	
	public ResponseEntity<Object> fetchFriendTransactions (int userId, int friendUserId){
		return new ResponseEntity<Object>(transactionsDao.fetchFriendTransactions(userId, friendUserId), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> addTransaction(Transaction transaction){
		return new ResponseEntity<Object>(transactionsDao.addTransaction(transaction), HttpStatus.OK);
	}
}
