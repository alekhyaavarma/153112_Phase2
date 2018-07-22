package com.capgemini.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.beans.Transactions;
import com.capgemini.core.beans.Wallet;
import com.capgemini.core.exceptions.InsufficientBalanceException;
import com.capgemini.core.exceptions.InvalidInputException;
import com.capgemini.core.repo.WalletRepo;
import com.capgemini.core.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{

	private WalletRepo repo;

	public WalletServiceImpl() {
		repo = new WalletRepoImpl();
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		if(isValidPhoneNumber(mobileNo) && isValidName(name) && amount.compareTo(new BigDecimal(0)) > 0) {
			Wallet wallet = new Wallet();
			Customer customer = new Customer();
		
			wallet.setBalance(amount);
			customer.setName(name);
			customer.setMobileNo(mobileNo);
			customer.setWallet(wallet);
		
			repo.save(customer);

			return customer;
		}
		else throw new InvalidInputException("Invalid Input Details");

	}

	public Customer showBalance(String mobileNo) throws InvalidInputException {
		if(mobileNo.length()<10) 
			throw new InvalidInputException("InvalidPhone Number");
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException,InsufficientBalanceException
	{
		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValidPhoneNumber(mobileNo)) 
		{
			Customer customer = repo.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().add(amount));
		
			Transactions transaction = new Transactions();
			transaction.setTransactionType("Deposit ");
			transaction.setAmount(amount);
			Date date = new Date();
			transaction.setDate(date);
			
			repo.addTransaction(mobileNo, transaction);
			
			repo.update(customer);
			
			return customer;
	
		}
		else throw new InvalidInputException("Please enter valid mobile number");
	}
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException 
	{	
		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValidPhoneNumber(mobileNo)) 
		{
			Customer customer = repo.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
		
			if(amount.compareTo(wallet.getBalance()) > 0) 
				throw new InsufficientBalanceException("Amount is not sufficient in your account");
		
			wallet.setBalance(wallet.getBalance().subtract(amount));
		    customer.setWallet(wallet);
		    
			Transactions transaction = new Transactions();
			transaction.setTransactionType("Withdraw");
			transaction.setAmount(amount);
			Date date = new Date();
			transaction.setDate(date);
			
			repo.addTransaction(mobileNo, transaction);
			
			repo.update(customer);
			
			return customer;
		}
		else throw new InvalidInputException("Enter valid mobile number");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException {
		if(isValidPhoneNumber(sourceMobileNo) == false || isValidPhoneNumber(targetMobileNo) == false) throw new InvalidInputException("Enter valid Mobile Number");
		Customer customer = withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		return customer;
	}

	

	public boolean isValidPhoneNumber(String mobileNo) {
		if(mobileNo.matches("[1-9][0-9]{9}")) 
		{
			return true;
		}		
		else 
			return false;
	}

	private boolean isValidName(String name) {
		if( name == null || name.trim().isEmpty() )
			return false;
		return true;
	}
	
	

	@Override
	public List<Transactions> getAllTransactions(String mobileNumber) {
		return repo.getTransaction(mobileNumber);
	}
}
