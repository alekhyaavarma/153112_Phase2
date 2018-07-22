package com.capgemini.core.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.IOP.TransactionService;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.beans.Transactions;
import com.capgemini.core.beans.Wallet;
import com.capgemini.core.exceptions.InvalidInputException;
import com.capgemini.core.util.DBUtil;

public class WalletRepoImpl implements WalletRepo{
	
	List<Transactions> transactions;
	
	public WalletRepoImpl() {
		transactions = new ArrayList<>();
	}

	@Override
	public boolean save(Customer customer) throws InvalidInputException {
		try(Connection con = DBUtil.getConnection())
		{
			PreparedStatement pstm = con.prepareStatement("insert into person values(?,?,?)");
			pstm.setString(1, customer.getName());
			pstm.setString(2, customer.getMobileNo());
			pstm.setBigDecimal(3, customer.getWallet().getBalance());
			
			pstm.execute();
			return true;
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new InvalidInputException("Already customer with this id is present");
		}
		return false;
	}

	@Override
	public Customer findOne(String mobileNo) throws InvalidInputException {
		try(Connection con = DBUtil.getConnection())
		{
			PreparedStatement pstm = con.prepareStatement("select * from person where mobileNo = ?");
			pstm.setString(1, mobileNo);
			ResultSet res = pstm.executeQuery();
			
			if(res.next() == false) throw new InvalidInputException("No customer with this mobile Number");
			
			Customer customer = new Customer();
			Wallet wallet = new Wallet();
			
			customer.setName(res.getString(1));
			customer.setMobileNo(res.getString(2));
			
			wallet.setBalance(res.getBigDecimal(3));
			customer.setWallet(wallet);
			
			return customer;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(Customer customer) {
		try(Connection con = DBUtil.getConnection())
		{
			PreparedStatement pstm = con.prepareStatement("update person set balance = ? where mobileNo = ?");
			pstm.setBigDecimal(1, customer.getWallet().getBalance());
			pstm.setString(2, customer.getMobileNo());
			pstm.execute();
			return;
			
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addTransaction(String mobileNo, Transactions t) {
		try(Connection con = DBUtil.getConnection())
		{
			PreparedStatement pstm = con.prepareStatement("insert into transaction values(?,?,?,?)");
			pstm.setString(1, mobileNo);
			pstm.setString(2, t.getTransactionType());
			pstm.setBigDecimal(3, t.getAmount());
			pstm.setDate(4, new java.sql.Date(t.getDate().getTime()));
			pstm.execute();
			return;
			
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Transactions> getTransaction(String mobileNo) {
		try(Connection con = DBUtil.getConnection()) 
		{
			PreparedStatement pstm = con.prepareStatement("select * from transaction where mobileNo = ?");
		
			pstm.setString(1, mobileNo);
			
			ResultSet rs = pstm.executeQuery();	
			
			int index = 0;
			
			while(rs.next()) {
				Transactions transaction = new Transactions();
				transaction.setTransactionType(rs.getString(2));
				transaction.setAmount(rs.getBigDecimal(3));
				transaction.setDate(rs.getDate(4));
				
				transactions.add(transaction);
			}
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactions;
	}
	
	

}
