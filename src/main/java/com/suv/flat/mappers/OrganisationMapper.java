package com.suv.flat.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.suv.flat.model.Organisations;

public class OrganisationMapper implements RowMapper<Organisations> {

	@Override
	public Organisations mapRow(ResultSet rs, int rowNum) throws SQLException {
		Organisations organisation=new Organisations();
		organisation.setOrg_id(rs.getInt("org_id"));
		organisation.setOrg_name(rs.getString("org_name"));
		organisation.setOrg_mnemonic(rs.getString("org_mnemonic"));
		return organisation;
	}

}
