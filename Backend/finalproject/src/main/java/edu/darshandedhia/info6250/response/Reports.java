package edu.darshandedhia.info6250.response;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import edu.darshandedhia.info6250.pojo.Transaction;
import edu.darshandedhia.info6250.pojo.User;

public class Reports {
	

	private BigInteger id;
	private int user;
	private double paid;
	private double ownShare;
	private BigInteger percentage;
	private BigInteger transaction;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
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
	public BigInteger getPercentage() {
		return percentage;
	}
	public void setPercentage(BigInteger percentage) {
		this.percentage = percentage;
	}
	public BigInteger getTransaction() {
		return transaction;
	}
	public void setTransaction(BigInteger transaction) {
		this.transaction = transaction;
	}

}
