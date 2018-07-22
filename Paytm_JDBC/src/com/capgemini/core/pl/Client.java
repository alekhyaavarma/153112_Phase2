package com.capgemini.core.pl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.beans.Transactions;
import com.capgemini.core.beans.Wallet;
import com.capgemini.core.exceptions.InsufficientBalanceException;
import com.capgemini.core.exceptions.InvalidInputException;
import com.capgemini.core.service.WalletService;
import com.capgemini.core.service.WalletServiceImpl;

public class Client 
{
	WalletService service;
	
	Client() {
		service = new WalletServiceImpl();
	}
	
	
	public void menu() {
		System.out.println("1. Create Account");
		System.out.println("2. Show Balance");
		System.out.println("3. Deposit Amount");
		System.out.println("4. Withdraw Amount");
		System.out.println("5. Fund Transfer");
		System.out.println("6. Print Transactions");
		System.out.println("7. Exit Application");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter your choice");
		int choice = sc.nextInt();
		
		switch(choice) {
			case 1:
				Customer customer = new Customer();
				Wallet wallet = new Wallet();
				
				System.out.print("Enter name: ");
				String name = sc.next();
				
				System.out.print("Enter mobileNumber: ");
				String mobileNumber = sc.next();
				
				System.out.print("Enter Amount: ");
				BigDecimal amount = sc.nextBigDecimal();
				
				try 
				{
					customer = service.createAccount(name, mobileNumber, amount);
					System.out.println("Account Creation was Successful");
					System.out.println("with name = " + customer.getName());
				} 
				catch (InvalidInputException e) {
					System.out.println("Enter valid details");
				}
				break;
				
			case 2:
				System.out.println("Enter mobile number");
				mobileNumber = sc.next();
				
			try {
				customer = service.showBalance(mobileNumber);
				System.out.print("Available balance in your account " + customer.getName());
				System.out.println(" is " + customer.getWallet().getBalance());
			} catch (InvalidInputException e3) {
				System.out.println("Invalid Mobile Number");
			}
				
				break;
			
			case 3:
				System.out.println("Enter mobile number");
				mobileNumber = sc.next();
				
				System.out.println("Enter amount to be deposited");
				amount = sc.nextBigDecimal();
				
				try 
				{
					customer = service.depositAmount(mobileNumber, amount);
					System.out.println("Amount was Successfully deposited");
					System.out.println("Available balance in your Account is: " + customer.getWallet().getBalance());
				} 
				catch (InvalidInputException e2) 
				{
					System.out.println("Enter valid Mobile Number");
				}
				catch(InsufficientBalanceException e) {
					System.out.println("Insufficient Balance");
				}
				
				break;
			
			case 4:
				System.out.println("Enter mobile number");
				mobileNumber = sc.next();
				
				System.out.println("Enter amount to be withdrawn");
				amount = sc.nextBigDecimal();
				
				try 
				{
					customer = service.withdrawAmount(mobileNumber, amount);
					System.out.println("Amount was Successfully withdrawn");
					System.out.println("Available balance in your Account is: " + customer.getWallet().getBalance());
				}
				catch (InvalidInputException e1) 
				{
					System.out.println("Enter Valid Mobile Number");
				} catch (InsufficientBalanceException e) {
					System.out.println("Insufficient Balance");
				}
				break;
			
			case 5:
				System.out.print("Enter source mobile number: ");
				String sourceMobile = sc.next();
				
				System.out.print("Enter target mobile number: ");
				String targetMobile = sc.next();
				
				System.out.println("Enter amount to be transferred");
				amount = sc.nextBigDecimal();
				
				try 
				{
					customer = service.fundTransfer(sourceMobile, targetMobile, amount);
					System.out.println("Amount has successfully transferred from account " + customer.getName());
					System.out.println("Available balance in your Account is: " + customer.getWallet().getBalance());

				} 
				catch (InvalidInputException e) {
					System.out.println("Enter Valid Mobile Number");
				} catch (InsufficientBalanceException e) {
					System.out.println("Insufficient Balance");
				}
				
				
				break;
			case 6:
				System.out.println("Enter mobile Number");
				mobileNumber = sc.next();
				List<Transactions> transactions = null;
				
				transactions = service.getAllTransactions(mobileNumber);
				
				Iterator<Transactions> it = transactions.iterator();
				
				System.out.println("Type \t\tAmount \t\tDate");
				
				while(it.hasNext()) 
				{
					Transactions transaction = it.next();
					System.out.println(transaction.getTransactionType() + "\t" + 
					transaction.getAmount() + "\t" + 
							transaction.getDate() + "\t");
				}
				break;
				
			
			case 7:
				System.out.println("Thank you for using our services");
				System.out.println("Good Bye");
				System.exit(0);
			
			default:
				System.out.println("Please enter valid choice");
				break;
		}
		
		
	}
	
	public static void main(String[] args) 
	{
		Client client = new Client();
		while(true) {
			client.menu();
		}
	}
	
	
}
