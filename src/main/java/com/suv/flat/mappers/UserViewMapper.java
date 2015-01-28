package com.suv.flat.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.suv.flat.model.UserView;

public class UserViewMapper implements RowMapper<UserView> {

	@Override
	public UserView mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserView user=new UserView();
		user.setUserid(rs.getInt("user_id"));
		user.setUser_name(rs.getString("user_name"));
		user.setUser_login(rs.getString("user_login"));
		user.setPassword(rs.getString("user_password"));
		user.setUser_role_id(rs.getInt("user_role_id"));
		user.setUserRole(rs.getString("user_role"));
		user.setCreate_date(rs.getString("user_create_date"));
		return user;
	}

}
