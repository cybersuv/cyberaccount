package com.suv.flat.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.suv.flat.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user=new User();
		user.setUserid(rs.getInt("user_id"));
		user.setUser_name(rs.getString("user_name"));
		user.setUser_login(rs.getString("user_login"));
		user.setPassword(rs.getString("user_password"));
		user.setUser_role_id(rs.getInt("user_role_id"));
		user.setCreate_date(rs.getString("user_create_date"));
		user.setOrg_id(rs.getInt("org_id"));
		user.setOrg_name(rs.getString("org_name"));
		user.setOrg_mnemonic(rs.getString("org_mnemonic"));
		return user;
	}

}
