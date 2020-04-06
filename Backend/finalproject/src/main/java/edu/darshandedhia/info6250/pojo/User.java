package edu.darshandedhia.info6250.pojo;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER",
	   uniqueConstraints = {
			   @UniqueConstraint(columnNames = "EMAIL", name = "UK_EMAIL"),
			   @UniqueConstraint(columnNames = "USER_NAME", name = "UK_USERNAME")
	   })
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@ManyToMany
	@JoinTable(name="USER_FRIENDS", 
	joinColumns=@JoinColumn(name="USER_ID"),
	inverseJoinColumns = @JoinColumn(name="FRIEND_ID"))
	private Collection<User> friends = new ArrayList<User>();
	
	@ManyToMany
	@JoinTable(name="USER_FRIENDS",
	joinColumns=@JoinColumn(name="FRIEND_ID"),
	inverseJoinColumns=@JoinColumn(name="USER_ID"))
	private Collection<User> friendsOf = new ArrayList<User>();
	
	@ManyToMany(mappedBy = "userList")
	private Collection<Group> groupList = new ArrayList<Group>();
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Collection<User> getFriends() {
		return friends;
	}
	public void setFriends(Collection<User> friends) {
		this.friends = friends;
	}
	public Collection<User> getFriendsOf() {
		return friendsOf;
	}
	public void setFriendsOf(Collection<User> friendsOf) {
		this.friendsOf = friendsOf;
	}
	public Collection<Group> getGroupList() {
		return groupList;
	}
	public void setUserList(Collection<Group> groupList) {
		this.groupList = groupList;
	}
	
}
