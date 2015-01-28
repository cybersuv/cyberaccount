package com.suv.flat.dao;

import java.util.List;

import javax.sql.DataSource;

import com.suv.flat.model.AccountSummary;
import com.suv.flat.model.Owners;
import com.suv.flat.model.Reasons;
import com.suv.flat.model.TransactionView;
import com.suv.flat.model.Transactions;


public interface TransactionDAO {
	/** 
	    * This is the method to be used to initialize
	    * database resources ie. connection.
	    */
	   public void setDataSource(DataSource ds);
	   
	   public int enterDeposit(Transactions trtx,int orgId);
	   
	   public List<Transactions> getAllTransactions(int ownerId);
	   
	   public List<Transactions> getAllTransactions(String startDate,String endDate,int orgId);
	   
	   public List<TransactionView> getAllTransactions(String startDate,String endDate,int ownerId,int orgId);
	   
	   public List<TransactionView> getAllCreditTransactions(String startDate,String endDate, int orgId);
	   
	   public List<TransactionView> getAllDebitTransactions(String startDate,String endDate, int orgId);
	   
	   public Transactions getTransaction(int trtxId);
	   
	   public int enterExpence(Transactions trtx,int orgId);
	   
	   public List<Reasons> getAllReasons(int orgId);
	   
	   public Reasons getReasonFromID(int reasonId);
	   
	   public String createReason(Reasons reason,int orgId);
	   
	   public String updateReason(Reasons reason);
	   
	   public String deleteReason(int reasonId);
	   
	   public List<Owners> getOwner(String ownerName);
	   
	   public List<Owners> getAllOwners(int orgId);
	   
	   public Owners getOwnerFromID(int ownerId);
	   
	   public int createOwner(Owners owner,int orgId);
	   
	   public int updateOwner(Owners owner);
	   
	   public AccountSummary getAccountSummary(int orgId);
	   
	   
}
