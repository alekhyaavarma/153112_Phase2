package com.capgemini.core.beans;

import java.math.BigDecimal;
import java.util.Date;

public class Transactions {
	String transactionType;
	BigDecimal amount;
	Date date;
	
	public Transactions() {
		super();
	}

	public Transactions(String transactionType, BigDecimal amount, Date date) {
		super();
		this.transactionType = transactionType;
		this.amount = amount;
		this.date = date;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	

}
