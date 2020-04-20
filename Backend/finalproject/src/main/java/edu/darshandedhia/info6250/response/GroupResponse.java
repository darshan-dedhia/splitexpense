package edu.darshandedhia.info6250.response;

import java.util.List;

import edu.darshandedhia.info6250.pojo.Group;
import edu.darshandedhia.info6250.pojo.Transaction;
import edu.darshandedhia.info6250.pojo.User;

public class GroupResponse extends Response{
	private Group group;
	private List<Transaction> transactions;
	private List<User> users;
	public GroupResponse(int statusCode, String status, String message, Group group, List<Transaction> transactions, List<User> users){
		super(statusCode, status, message);
		this.group = group;
		this.transactions = transactions;
		this.users = users;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
