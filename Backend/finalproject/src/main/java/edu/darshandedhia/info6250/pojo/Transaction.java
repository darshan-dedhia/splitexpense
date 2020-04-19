package edu.darshandedhia.info6250.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction implements Serializable{
	@Id @GeneratedValue
	@Column(name = "TRANSACTION_ID")
	private int transactionId;
	@Column(name = "PAYMENT_IND_GRP_ID")
	private int paymentIndividualOrGroupId;
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	@Column(name = "CATEGORY", nullable = false)
	private String Category;
	@Column(name = "TRANSACTION_DATE", nullable = false)
	private Date transactionDate;
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private double totalAmount;
	@Column(name = "SPLIT_METHOD", nullable = false)
	private int splitMethod;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<TransactionDetails> transactionDetails = new ArrayList<TransactionDetails>();

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getPaymentIndividualOrGroupId() {
		return paymentIndividualOrGroupId;
	}

	public void setPaymentIndividualOrGroupId(int paymentIndividualOrGroupId) {
		this.paymentIndividualOrGroupId = paymentIndividualOrGroupId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public List<TransactionDetails> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getSplitMethod() {
		return splitMethod;
	}

	public void setSplitMethod(int splitMethod) {
		this.splitMethod = splitMethod;
	}
	
	
	
}
