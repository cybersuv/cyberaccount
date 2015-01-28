package com.suv.flat.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.suv.flat.model.UserRole;

public class UserRoleMapper implements RowMapper<UserRole> {

	@Override
	public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserRole role=new UserRole();
		role.setUser_type_id(rs.getInt("user_type_id"));
		role.setUser_type_name(rs.getString("user_type_name"));
		return role;
	}

}
