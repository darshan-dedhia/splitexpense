package edu.darshandedhia.info6250.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.constants.Message;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.dao.TransactionsDao;
import edu.darshandedhia.info6250.pojo.Transaction;
import edu.darshandedhia.info6250.response.Response;

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
	
	public ResponseEntity<Object> deleteTransaction(String strTransactionId){
		try {
			int transactionId = Integer.parseInt(strTransactionId);
		return new ResponseEntity<Object>(transactionsDao.deleteTransaction(transactionId), HttpStatus.OK);
		} catch (NumberFormatException nfe) {
			return new ResponseEntity<Object>(new Response(StatusCode.badRequest, Status.failure, Message.malFormedApiUrlRequest), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new Response(StatusCode.badRequest, Status.error, Message.internalServerError), HttpStatus.OK);
		}
		
	}
	
	public ResponseEntity<Object> fetchGroupTransactions (String strGroupId){
		try {
			int groupId = Integer.parseInt(strGroupId);
			return new ResponseEntity<Object>(transactionsDao.fetchGroupTransactions(groupId), HttpStatus.OK);
		} catch (NumberFormatException nfe) {
			return new ResponseEntity<Object>(new Response(StatusCode.badRequest, Status.failure, Message.malFormedApiUrlRequest), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new Response(StatusCode.badRequest, Status.error, Message.internalServerError), HttpStatus.OK);
		}
	}
}
