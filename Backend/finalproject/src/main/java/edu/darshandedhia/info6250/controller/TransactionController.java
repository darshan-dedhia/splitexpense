package edu.darshandedhia.info6250.controller;

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
	public ResponseEntity<Object> fetchFriendTransactions(@PathVariable("userId") String userid){
		int userId;
		try {
			userId = Integer.parseInt(userid);
		} catch (NumberFormatException nfe) {
			return new ResponseEntity<Object>(new Response(StatusCode.badRequest, Status.failure, Message.malFormedApiUrlRequest) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new Response(StatusCode.badRequest, Status.error, Message.malFormedApiUrlRequest) ,HttpStatus.BAD_REQUEST);
		}
		return transactionService.fetchFriendTransactions(userId);
	}
}
