package com.suv.flat.dao.template;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.suv.flat.dao.UserDAO;
import com.suv.flat.mappers.OrganisationMapper;
import com.suv.flat.mappers.UserMapper;
import com.suv.flat.mappers.UserRoleMapper;
import com.suv.flat.mappers.UserViewMapper;
import com.suv.flat.model.Organisations;
import com.suv.flat.model.User;
import com.suv.flat.model.UserRole;
import com.suv.flat.model.UserView;

public class UsersJDBCTemplate implements UserDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public String create(User user) {
		String responseFlag="NOT_OK";
		try{
			String SQL = "insert into users(user_name,user_login,user_password,user_role_id,user_create_date,org_id) values (?,?,?,?,CURRENT_TIMESTAMP(),?)";
			jdbcTemplateObject.update(SQL, user.getUser_name(), user.getUser_login(), user.getPassword(),user.getUser_role_id(),user.getOrg_id());
			System.out.println("Created Record Name = " + user.getUser_name() + " Login = "	+ user.getUser_login());
			responseFlag="OK";
		}catch(Exception ex){
			System.out.println("Error in user creation. Nested error is : " + ex.getMessage());
			responseFlag="Error in user creation. Nested error is : " + ex.getMessage();
		}
		return responseFlag;
	}

	@Override
	public User getUser(String userLogin) {
		String SQL = "select u.*,o.org_name,o.org_mnemonic from users u,organisations o where u.org_id=o.org_id and user_login = ?";
		User user = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { userLogin }, new UserMapper());
		return user;
	}

	@Override
	public List<User> listUsers() {
		String SQL = "select u.*,o.org_name,o.org_mnemonic from users u,organisations o where u.org_id=o.org_id";
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	@Override
	public String delete(Integer userId) {
		String responseFlag="NOT_OK";
		try{
		String SQL = "delete from users where user_id = ?";
		jdbcTemplateObject.update(SQL, userId);
		System.out.println("Deleted Record with ID = " + userId);
		responseFlag="OK";
		}catch(Exception ex){
			System.out.println("Error in user deletion. Nested error is : " + ex.getMessage());
			responseFlag="Error in user deletion. Nested error is : " + ex.getMessage();
		}
		return responseFlag;
	}

	@Override
	public String update(User user) {
		String responseFlag="NOT_OK";
		try{
		String SQL = "update users set user_name = ?,user_login=?,user_password=?,user_role_id=? where user_id = ?";
		jdbcTemplateObject.update(SQL, user.getUser_name(), user.getUser_login(), user.getPassword(), user.getUser_role_id(), user.getUserid());
		System.out.println("Updated Record with ID = " + user.getUserid());
		responseFlag="OK";
		}catch(Exception ex){
			System.out.println("Error in user updation. Nested error is : " + ex.getMessage());
			responseFlag="Error in user updation. Nested error is : " + ex.getMessage();
		}
		return responseFlag;
	}

	@Override
	public boolean authenticate(User user) {
		String SQL = "select user_id from users where user_login = ? and user_password= ? and org_id=?";

		try {
			@SuppressWarnings("deprecation")
			int tempId = jdbcTemplateObject.queryForInt(SQL,
					user.getUser_login(), user.getPassword(),user.getOrg_id());
			if (tempId > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean IsAdmin(User user) {
		String SQL = "select user_id from users u,user_type ut where u.user_role_id=ut.user_type_id and UPPER(ut.user_type_name)='ADMIN' and user_id=? union select -1 LIMIT 1;";

		try {
			@SuppressWarnings("deprecation")
			int tempId = jdbcTemplateObject.queryForInt(SQL,
					user.getUserid());
			if (tempId > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println("Error getting User detail. Nested exception : " + ex.getMessage());
			return false;
		}
	}

	@Override
	public User getUser(int userId) {
		String SQL = "select u.*,o.org_name,o.org_mnemonic from users u,organisations o where u.org_id=o.org_id and user_id = ?";
		User user = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { userId }, new UserMapper());
		return user;
	}

	@Override
	public List<UserView> listUsersWithRole(int orgId) {
		String SQL = "select * from user_view where org_id=" + orgId;
		List<UserView> users = jdbcTemplateObject.query(SQL, new UserViewMapper());
		return users;
	}

	@Override
	public List<UserRole> listRoles() {
		String SQL = "select * from user_type";
		List<UserRole> userRoles = jdbcTemplateObject.query(SQL, new UserRoleMapper());
		return userRoles;
	}

	@Override
	public List<Organisations> getOrganisations() {
		String SQL = "select * from organisations";
		List<Organisations> organisations = jdbcTemplateObject.query(SQL, new OrganisationMapper());
		return organisations;
	}

}
