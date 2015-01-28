package com.suv.flat.dao;

import java.util.List;

import javax.sql.DataSource;

import com.suv.flat.model.Organisations;
import com.suv.flat.model.User;
import com.suv.flat.model.UserRole;
import com.suv.flat.model.UserView;

public interface UserDAO {
	/** 
	    * This is the method to be used to initialize
	    * database resources ie. connection.
	    */
	   public void setDataSource(DataSource ds);
	   /** 
	    * This is the method to be used to create
	    * a record in the Users table.
	    */
	   public String create(User user);
	   /** 
	    * This is the method to be used to list down
	    * a record from the Users table corresponding
	    * to a passed userLogin id.
	    */
	   public User getUser(String userLogin);
	   /** 
	    * This is the method to be used to list down
	    * a record from the Users table corresponding
	    * to a passed user_id.
	    */
	   public User getUser(int userId);
	   /** 
	    * This is the method to be used to list down
	    * all the records from the Users table.
	    */
	   public List<User> listUsers();
	   /** 
	    * This is the method to be used to list down
	    * all the records from the User_Type table.
	    */
	   public List<UserRole> listRoles();
	   /** 
	    * This is the method to be used to list down
	    * all the records from the Users joined with Role table.
	    */
	   public List<UserView> listUsersWithRole(int orgId);
	   /** 
	    * This is the method to be used to delete
	    * a record from the Users table corresponding
	    * to a passed user id.
	    */
	   public String delete(Integer userId);
	   /** 
	    * This is the method to be used to update
	    * a record into the Users table.
	    */
	   public String update(User user);
	   
	   /**
	    * This method will return boolean result if a user exists in the DB or not
	    */
	   public boolean authenticate(User user);
	   
	   /**
	    * This method will return boolean result whether user is Admin or not
	    */
	   public boolean IsAdmin(User user);
	   
	   public List<Organisations> getOrganisations();
}
