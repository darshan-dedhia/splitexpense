package edu.darshandedhia.info6250.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "GROUP_DETAILS")
public class Group implements Serializable{
	
	@SequenceGenerator(name = "groupIdSequence", sequenceName = "GROUP_ID_SEQ", initialValue = 1, allocationSize = 100)
	@Id @Column(name="GROUP_ID") @GeneratedValue(generator = "groupIdSequence")
	private int groupId;
	
	@Column(name="GROUP_NAME")
	private String groupName;
	
	//@JsonIgnore This is commented while Adding groups change
	@JsonProperty(value = "userList", access = JsonProperty.Access.WRITE_ONLY)
 	@ManyToMany(cascade = CascadeType.ALL)
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
