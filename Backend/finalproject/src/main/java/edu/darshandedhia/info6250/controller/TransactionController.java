package edu.darshandedhia.info6250.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.darshandedhia.info6250.constants.Message;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.pojo.Transaction;
import edu.darshandedhia.info6250.pojo.User;
import edu.darshandedhia.info6250.response.Response;
import edu.darshandedhia.info6250.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	@Qualifier("transactionService")
	private TransactionService transactionService;
	
	@RequestMapping(value = "/fetchFriendTransactions/{userId}", produces = "application/json", method=RequestMethod.GET)
	public ResponseEntity<Object> fetchFriendTransactions(@PathVariable("userId") String userid, HttpServletRequest request){
		int friendUserId;
		int userId;
		try {
			friendUserId = Integer.parseInt(userid);
			userId = Integer.parseInt((String) request.getAttribute("userId"));
		} catch (NumberFormatException nfe) {
			return new ResponseEntity<Object>(new Response(StatusCode.badRequest, Status.failure, Message.malFormedApiUrlRequest) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			
			return new ResponseEntity<Object>(new Response(StatusCode.badRequest, Status.error, Message.malFormedApiUrlRequest) ,HttpStatus.BAD_REQUEST);
		}
		return transactionService.fetchFriendTransactions(userId, friendUserId);
	}
	
	@RequestMapping(value = "/addTransaction", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> addTransaction(@RequestBody Transaction transaction){
		return transactionService.addTransaction(transaction);
	}
	
	@RequestMapping(value = "/deleteTransaction/{transactionId}", produces = "application/json", method=RequestMethod.POST)
	public ResponseEntity<Object> deleteTransaction(@PathVariable("transactionId") String transactionId){
		return transactionService.deleteTransaction(transactionId);
	}
	
	@RequestMapping(value = "/groups/{groupId}", produces = "application/json", method=RequestMethod.GET)
	public ResponseEntity<Object> fetchGroupTransactions(@PathVariable("groupId") String groupId){
		return transactionService.fetchGroupTransactions(groupId);
	}
	
	
}
