package com.capgemini.core.repo;

import java.util.List;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.beans.Transactions;
import com.capgemini.core.exceptions.InvalidInputException;

public interface WalletRepo {

	public boolean save(Customer customer) throws InvalidInputException;

	public Customer findOne(String mobileNo) throws InvalidInputException;

	public void update(Customer customer);
	
	public void addTransaction(String mobileNo, Transactions t);
	
	public List<Transactions> getTransaction(String mobileNo);
}
