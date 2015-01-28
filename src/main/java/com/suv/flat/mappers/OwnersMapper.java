package com.suv.flat.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.suv.flat.model.Owners;

public class OwnersMapper implements RowMapper<Owners> {

	@Override
	public Owners mapRow(ResultSet rs, int rowNum) throws SQLException {
		Owners owner=new Owners();
		owner.setOwner_id(rs.getInt("owner_id"));
		owner.setOwner_name(rs.getString("owner_name"));
		owner.setFlat_number(rs.getString("flat_number"));
		owner.setFloor(rs.getString("floor"));
		owner.setIs_active(rs.getBoolean("is_active"));
		owner.setCreate_date(rs.getString("create_date"));
		return owner;
	}

}
