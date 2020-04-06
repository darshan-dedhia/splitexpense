package edu.darshandedhia.info6250.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
	@Id
	@Column(name = "TRANSACTION_ID")
	private int transactionId;
	@Column(name = "PAYMENT_IND_GRP_ID")
	private int paymentIndividualOrGroupId;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CATEGORY")
	private String Category;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "transaction")
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
	
}
