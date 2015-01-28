package com.suv.flat.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.suv.flat.model.AccountSummary;

public class AccountSummaryMapper implements RowMapper<AccountSummary> {

	@Override
	public AccountSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccountSummary summary=new AccountSummary();
		summary.setCurrentBalance(rs.getDouble("current_balance"));
		summary.setCurrentMonthDeposit(rs.getDouble("current_month_deposit"));
		summary.setCurrentMonthExpense(rs.getDouble("current_month_expense"));
		summary.setPreviousBalance(rs.getDouble("previous_balance"));
		return summary;
	}

}
