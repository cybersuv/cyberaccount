package com.suv.flat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.suv.flat.dao.template.TransactionJDBCTemplate;
import com.suv.flat.dao.template.UsersJDBCTemplate;
import com.suv.flat.model.AccountSummary;
import com.suv.flat.model.Organisations;
import com.suv.flat.model.Owners;
import com.suv.flat.model.Reasons;
import com.suv.flat.model.TransactionView;
import com.suv.flat.model.Transactions;
import com.suv.flat.model.User;
import com.suv.flat.model.UserRole;
import com.suv.flat.model.UserView;

public class CoreService {
	private static final Logger logger=Logger.getLogger(CoreService.class);
	
	public static boolean authenticateUser(User paramUser) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		logger.info("Calling JDBC Template--------");
		boolean result=userJDBCTemplate.authenticate(paramUser);
		context.close();
		return result;
	}
	
	public static User getUserFromLogin(User paramUser){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		System.out.println("------Getting user details--------");
		User user=userJDBCTemplate.getUser(paramUser.getUser_login());
		context.close();
		return user;
	}
	
	public static Map<Integer, String> getReasonMap(int orgId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Reasons--------");
		List<Reasons> reasons=transactionJDBCTemplate.getAllReasons(orgId);
		Reasons tempReason;
		Map<Integer,String> responseMap=new HashMap<Integer, String>();
		Iterator<Reasons> i=reasons.iterator();
		while(i.hasNext()){
			tempReason=i.next();
			responseMap.put(tempReason.getReason_id(), tempReason.getReason());
		}
		context.close();
		return responseMap;
	}
	
	
	public static List<Owners> getOwnersFromSearch(String searchStr){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Owners--------");
		List<Owners> owners=transactionJDBCTemplate.getOwner(searchStr);
		List<String> tempList=new ArrayList<String>();
		Owners tempOwners;
		Iterator<Owners> i=owners.iterator();
		while(i.hasNext()){
			tempOwners=i.next();
			tempList.add(tempOwners.getOwner_name()+"(" + tempOwners.getOwner_id()+")");
		}
		context.close();
		return owners;
	}
	
	public static List<Owners> getAllOwners(int orgId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Owners--------");
		List<Owners> owners=transactionJDBCTemplate.getAllOwners(orgId);
		List<String> tempList=new ArrayList<String>();
		Owners tempOwners;
		Iterator<Owners> i=owners.iterator();
		while(i.hasNext()){
			tempOwners=i.next();
			tempList.add(tempOwners.getOwner_name()+"(" + tempOwners.getOwner_id()+")");
		}
		context.close();
		return owners;
	}
	
	public static Owners getOwnerFromID(int ownerId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Owner from ID--------");
		Owners owner=transactionJDBCTemplate.getOwnerFromID(ownerId);
		context.close();
		return owner;
		
	}
	
	public static String getOwnersHtml(HttpServletRequest request){
		String htmlResp="";
		htmlResp+="<a href=\"javascript:addOwner()\">Add Account</a><br></br>";
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td colspan=\"6\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Accounts</font></td></tr>";
		htmlResp+="<tr><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Account ID</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Name</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Flat #</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Floor</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Active</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Create Date</td></tr>";
		List<Owners> ownerList=getAllOwners(((User)request.getSession().getAttribute("authuser")).getOrg_id());
		Iterator<Owners> i=ownerList.iterator();
		Owners tempOwner;
		while(i.hasNext()){
			tempOwner=(Owners)i.next();
			htmlResp+="<tr><td class=\"datacell\">" + tempOwner.getOwner_id() + "</td><td class=\"datacell\"><a href=\"javascript:fetchOwner("+tempOwner.getOwner_id() +")\">" + tempOwner.getOwner_name() + "</a></td><td class=\"datacell\">" + tempOwner.getFlat_number() + "</td><td class=\"datacell\">" + tempOwner.getFloor();
			htmlResp+="</td><td class=\"datacell\">";
			if(isAdmin((User)request.getSession().getAttribute("authuser"))){
				if(tempOwner.getIs_active()){
					htmlResp+="<a href=\"javascript:toggleOwner(0);\">Active</a>";
				}else{
					htmlResp+="<a href=\"javascript:toggleOwner(1);\">Inactive</a>";
				}	
			}else{
				if(tempOwner.getIs_active()){
					htmlResp+="Active";
				}else{
					htmlResp+="Inactive";
				}
			}
			htmlResp+="</td><td class=\"datacell\">"+ tempOwner.getCreate_date() + "</td></tr>";
		}
		
		htmlResp+="</table><br></br>";
		return htmlResp;
	}
	
	
	public static boolean isAdmin(User user){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		System.out.println("------Getting IsAdmin--------");
		boolean isadmin=userJDBCTemplate.IsAdmin(user);
		context.close();
		return isadmin;
	}
	
	
	public static String processDeposit(Transactions trtx,int orgId){
		String trtx_id="-1";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Processing deposit--------");
		trtx_id=Integer.toString(transactionJDBCTemplate.enterDeposit(trtx,orgId));
		if(!trtx_id.equals("-1")){
			trtx_id="true|"+String.format("%010d", Integer.parseInt(trtx_id));
		}else{
			trtx_id="false|-1";
		}
		context.close();
		return trtx_id;
	}
	
	public static String processExpense(Transactions trtx,int orgId){
		String trtx_id="-1";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		try{
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Processing expense--------");
		trtx_id=Integer.toString(transactionJDBCTemplate.enterExpence(trtx,orgId));
		if(!trtx_id.equals("-1")){
			trtx_id="true|"+String.format("%010d", Integer.parseInt(trtx_id));
		}else{
			trtx_id="false|-1";
		}
		}finally{
			context.close();
		}
		return trtx_id;
	}
	
	
	public static boolean isSessonActive(HttpServletRequest request){
		boolean isActive=false;
		try{
			int sessionUserId=((User)request.getSession().getAttribute("authuser")).getUserid();
			if(sessionUserId<-1){
				isActive=false;
			}else{
				isActive=true;
			}
		}catch(Exception ex){
			System.out.println("Error : " + ex.getMessage());
			isActive=false;
		}
		return isActive;
	}
	
	
	public static Map<String, String> getFloors(){
		Map<String,String> responseMap=new HashMap<String, String>();
		for(int i=0;i<=3;i++){
			responseMap.put(String.valueOf(i), String.valueOf(i));
		}
		return responseMap;
	}
	
	
	public static String saveOwner(Owners owner,int orgId){
		String trtx_id="-1";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		try{
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Processing owner save--------");
		if(owner.getOwner_id()==-1){
			trtx_id=Integer.toString(transactionJDBCTemplate.createOwner(owner,orgId));
		}else{
			trtx_id=Integer.toString(transactionJDBCTemplate.updateOwner(owner));
		}
		if(!trtx_id.equals("-1")){
			trtx_id="true|"+String.format("%04d", Integer.parseInt(trtx_id));
		}else{
			trtx_id="false|-1";
		}
		}finally{
			context.close();
		}
		return trtx_id;
	}
	
	/** User Operations **/
	
	public static User getUserFromID(int userId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		System.out.println("------Getting User from ID--------");
		User user=userJDBCTemplate.getUser(userId);
		context.close();
		return user;
	}
	
	public static List<User> getAllUsers(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		System.out.println("------Getting Users--------");
		List<User> users=userJDBCTemplate.listUsers();
		context.close();
		return users;
	}
	
	public static List<UserView> getAllUsersWithRole(int orgId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		System.out.println("------Getting Users with Roles--------");
		List<UserView> users=userJDBCTemplate.listUsersWithRole(orgId);
		context.close();
		return users;
	}
	
	public static String getUsersHtml(HttpServletRequest request){
		String htmlResp="";
		htmlResp+="<a href=\"javascript:addUser()\">Add User</a><br></br>";
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td colspan=\"6\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Users</font></td></tr>";
		htmlResp+="<tr><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">User ID</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Name</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Login ID</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Role</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Create Date</td></tr>";
		List<UserView> userList=getAllUsersWithRole(((User)request.getSession().getAttribute("authuser")).getOrg_id());
		Iterator<UserView> i=userList.iterator();
		UserView tempUser;
		while(i.hasNext()){
			tempUser=(UserView)i.next();
			htmlResp+="<tr><td class=\"datacell\">" + tempUser.getUserid() + "</td><td class=\"datacell\"><a href=\"javascript:fetchUser("+tempUser.getUserid() +")\">" + tempUser.getUser_name() + "</a></td><td class=\"datacell\">" + tempUser.getUser_login() + "</td><td class=\"datacell\">" + tempUser.getUserRole();
			htmlResp+="</td><td class=\"datacell\">" + tempUser.getCreate_date() + "</td></tr>";
		}
		
		htmlResp+="</table><br></br>";
		return htmlResp;
	}
	
	
	public static Map<Integer,String> getRolesMap(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		System.out.println("------Getting User Types--------");
		List<UserRole> roles=userJDBCTemplate.listRoles();
		UserRole tempRole;
		Map<Integer,String> responseMap=new HashMap<Integer, String>();
		Iterator<UserRole> i=roles.iterator();
		while(i.hasNext()){
			tempRole=i.next();
			responseMap.put(tempRole.getUser_type_id(), tempRole.getUser_type_name());
		}
		context.close();
		return responseMap;
	}
	
	public static String saveUser(User user){
		String trtx_id="-1";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		try{
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		System.out.println("------Processing user save--------");
		if(user.getUserid()==-1){
			trtx_id=userJDBCTemplate.create(user);
		}else{
			trtx_id=userJDBCTemplate.update(user);
		}
		if(trtx_id.equals("OK")){
			trtx_id="true|"+trtx_id;
		}else{
			trtx_id="false|" + trtx_id;
		}
		}finally{
			context.close();
		}
		return trtx_id;
	}
	
	/** End of User Operations **/
	
	/**  Reason Operations **/
	
	public static List<Reasons> getAllReasons(int orgId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Reasons--------");
		List<Reasons> reasons=transactionJDBCTemplate.getAllReasons(orgId);
		context.close();
		return reasons;
	}
	
	public static Reasons getReasonFromID(int reasonId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Reason from ID--------");
		Reasons reason=transactionJDBCTemplate.getReasonFromID(reasonId);
		context.close();
		return reason;
		
	}
	
	public static String getReasonsHtml(HttpServletRequest request){
		String htmlResp="";
		htmlResp+="<a href=\"javascript:addReason()\">Add Reason</a><br></br>";
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"6\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Reasons</font></td></tr>";
		htmlResp+="<tr><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reason ID</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reason Name</td></tr>";
		List<Reasons> reasonList=getAllReasons(((User)request.getSession().getAttribute("authuser")).getOrg_id());
		Iterator<Reasons> i=reasonList.iterator();
		Reasons tempReason;
		while(i.hasNext()){
			tempReason=(Reasons)i.next();
			htmlResp+="<tr><td class=\"datacell\">" + tempReason.getReason_id() + "</td><td class=\"datacell\"><a href=\"javascript:fetchReason("+tempReason.getReason_id() +")\">" + tempReason.getReason() + "</a></td></tr>";
		}
		htmlResp+="</table><br></br>";
		return htmlResp;
	}
	
	public static String saveReason(Reasons reason,int orgId){
		String trtx_id="-1";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		try{
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Processing reason save--------");
		if(reason.getReason_id()==-1){
			trtx_id=transactionJDBCTemplate.createReason(reason,orgId);
		}else{
			trtx_id=transactionJDBCTemplate.updateReason(reason);
		}
		if(trtx_id.equals("OK")){
			trtx_id="true|"+trtx_id;
		}else{
			trtx_id="false|" + trtx_id;
		}
		}finally{
			context.close();
		}
		return trtx_id;
	}
	
	/** End of Reason operations  **/
	
	/** Transactions   **/
	
	public static List<TransactionView> getCreditTransactions(String startDate,String endDate,int orgId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Users with Roles--------");
		List<TransactionView> transactions=transactionJDBCTemplate.getAllCreditTransactions(startDate, endDate,orgId);
		context.close();
		return transactions;
	}
	
	public static List<TransactionView> getDebitTransactions(String startDate,String endDate,int orgId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Users with Roles--------");
		List<TransactionView> transactions=transactionJDBCTemplate.getAllDebitTransactions(startDate, endDate,orgId);
		context.close();
		return transactions;
	}
	
	public static List<TransactionView> getOwnerTransactions(String startDate,String endDate,int ownerId,int orgId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Users with Roles--------");
		List<TransactionView> transactions=transactionJDBCTemplate.getAllTransactions(startDate, endDate, ownerId,orgId);
		context.close();
		return transactions;
	}
	
	public static String getTransactionHtml(HttpServletRequest request,String trtxType,String startDate,String endDate,int ownerId){
		String htmlResp="";
		double totalAmount=0;
		htmlResp+="<br></br>";
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"7\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Transactions</font></td></tr>";
		htmlResp+="<tr><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Transaction ID</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Account</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Transaction Date</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reason</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Comment</td><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Entry User</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Amount</td></tr>";
		List<TransactionView> transactionList;
		if(trtxType.equals("CR")){
			transactionList=getCreditTransactions(startDate, endDate,((User)request.getSession().getAttribute("authuser")).getOrg_id());
		}else if(trtxType.equals("DR")){
			transactionList=getDebitTransactions(startDate, endDate,((User)request.getSession().getAttribute("authuser")).getOrg_id());
		}else{
			transactionList=getOwnerTransactions(startDate, endDate, ownerId,((User)request.getSession().getAttribute("authuser")).getOrg_id());
		}
		Iterator<TransactionView> i=transactionList.iterator();
		TransactionView tempTx;
		while(i.hasNext()){
			tempTx=(TransactionView)i.next();
			htmlResp+="<tr><td class=\"datacell\">" + tempTx.getTrtx_id() + "</td><td class=\"datacell\">" + tempTx.getOwner_name() + "</td><td class=\"datacell\">" + tempTx.getTrtx_date() + "</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getReason() + "</td><td class=\"datacell\">" + tempTx.getTrtx_comment() +"</td><td class=\"datacell\">"+ tempTx.getUser_name() + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + tempTx.getTrtx_amount()+"</td></tr>";
			totalAmount+=tempTx.getTrtx_amount();
		}
		
		htmlResp+="<tr><td class=\"datacell\" colspan=\"6\" align=\"left\"><b>Total Amount</b></td><td class=\"datacell\" align=\"right\">" + totalAmount + "</td></tr>";
		htmlResp+="</table><br></br>";
		return htmlResp;
	}
	
	public static AccountSummary getAccountSummary(int orgId){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		TransactionJDBCTemplate transactionJDBCTemplate = (TransactionJDBCTemplate) context
				.getBean("transactionJDBCTemplate");
		System.out.println("------Getting Account Summary--------");
		AccountSummary summary=transactionJDBCTemplate.getAccountSummary(orgId);
		context.close();
		return summary;
	}
	
	public static Map<Integer, String> getOrganisationsMap(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcBeans.xml");
		UsersJDBCTemplate userJDBCTemplate = (UsersJDBCTemplate) context
				.getBean("userJDBCTemplate");
		System.out.println("------Getting Organisations--------");
		List<Organisations> orgs=userJDBCTemplate.getOrganisations();
		Organisations tempOrg;
		Map<Integer,String> responseMap=new HashMap<Integer, String>();
		Iterator<Organisations> i=orgs.iterator();
		while(i.hasNext()){
			tempOrg=i.next();
			responseMap.put(tempOrg.getOrg_id(), tempOrg.getOrg_mnemonic());
		}
		context.close();
		return responseMap;
	}
	
}
