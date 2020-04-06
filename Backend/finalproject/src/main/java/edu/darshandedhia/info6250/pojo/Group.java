package edu.darshandedhia.info6250.pojo;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GROUP_DETAILS")
public class Group {
	
	@Id @Column(name="GROUP_ID")
	private int groupId;
	
	@Column(name="GROUP_NAME")
	private String groupName;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="GROUP_USER_DETAILS", 
	joinColumns=@JoinColumn(name="GROUP_ID"),
	inverseJoinColumns = @JoinColumn(name="USER_ID"))
	private Collection<User> userList = new ArrayList<User>();

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Collection<User> getUserList() {
		return userList;
	}

	public void setUserList(Collection<User> userList) {
		this.userList = userList;
	}
	
}
