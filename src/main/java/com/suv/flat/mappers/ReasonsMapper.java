package com.suv.flat.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.suv.flat.model.Reasons;

public class ReasonsMapper implements RowMapper<Reasons> {

	@Override
	public Reasons mapRow(ResultSet rs, int rowNum) throws SQLException {
		Reasons reason=new Reasons();
		reason.setReason_id(rs.getInt("reason_id"));
		reason.setReason(rs.getString("reason"));
		return reason;
	}

}
