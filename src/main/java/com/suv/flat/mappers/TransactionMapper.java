package com.suv.flat.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.suv.flat.model.Transactions;

public class TransactionMapper implements RowMapper<Transactions> {

	@Override
	public Transactions mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transactions trtx=new Transactions();
		trtx.setTrtx_id(rs.getInt("trtx_id"));
		trtx.setOwner_id(rs.getInt("owner_id"));
		trtx.setTrtx_date(rs.getString("trtx_date"));
		trtx.setTrtx_amount(rs.getDouble("trtx_amount"));
		trtx.setTrtx_type(rs.getString("trtx_type"));
		trtx.setTrtx_reason(rs.getString("trtx_reason"));
		trtx.setTrtx_user(rs.getInt("trtx_user"));
		trtx.setTrtx_comment(rs.getString("trtx_comment"));
		trtx.setTrtx_mod_date(rs.getString("trtx_mod_date"));
		return trtx;
	}

}
