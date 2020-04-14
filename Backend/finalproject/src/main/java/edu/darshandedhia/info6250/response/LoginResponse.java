package edu.darshandedhia.info6250.response;

import java.util.Collection;
import java.util.Set;

import edu.darshandedhia.info6250.pojo.Group;
import edu.darshandedhia.info6250.pojo.User;

public class LoginResponse extends Response{
	private String token;
	private User user;
	private Collection<Group> groupList;
	private Set<User> friendList;
	
	public LoginResponse(int statusCode, String status, String message, String token, User user, Collection<Group> groupList, Set<User> friendList) {
		super(statusCode, status, message);
		this.token = token;
		this.user = user;
		this.groupList = groupList;
		this.friendList = friendList;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Collection<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(Collection<Group> groupList) {
		this.groupList = groupList;
	}

	public Set<User> getFriendList() {
		return friendList;
	}

	public void setFriendList(Set<User> friendList) {
		this.friendList = friendList;
	}
	
}
