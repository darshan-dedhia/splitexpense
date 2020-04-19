
package edu.darshandedhia.info6250.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "TRANSACTION_DETAILS")
public class TransactionDetails implements Serializable{
	
	@Id
	@GenericGenerator(
	        name = "sequence",
	        strategy = "sequence",
	        parameters = {
	            @org.hibernate.annotations.Parameter(
	                name = "sequence",
	                value = "sequence"
	            )
	        })
	@Column(name="TD_ID")
	@GeneratedValue(generator = "sequence")
	private int id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	@Column(name = "PAID", nullable = false)
	private double paid;
	@Column(name = "OWN_SHARE", nullable = false)
	private double ownShare;
	@Column(name = "SPLIT_PCT", nullable = false)
	private double percentage;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="TRANSACTION_ID")
	@JsonBackReference
	private Transaction transaction;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public double getOwnShare() {
		return ownShare;
	}

	public void setOwnShare(double ownShare) {
		this.ownShare = ownShare;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
}
