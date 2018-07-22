package com.capgemini.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.beans.Transactions;
import com.capgemini.core.exceptions.InsufficientBalanceException;
import com.capgemini.core.exceptions.InvalidInputException;

public interface WalletService {
	public Customer createAccount(String name ,String mobileno, BigDecimal amount) throws InvalidInputException;
	public Customer showBalance (String mobileno) throws InvalidInputException;
	public Customer fundTransfer (String sourceMobileNo,String targetMobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException;
	public Customer depositAmount (String mobileNo,BigDecimal amount ) throws InvalidInputException, InsufficientBalanceException;
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException;
	public List<Transactions> getAllTransactions(String mobileNumber);

}
