package com.suv.flat.dao.template;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import sun.security.acl.OwnerImpl;

import com.suv.flat.dao.TransactionDAO;
import com.suv.flat.mappers.AccountSummaryMapper;
import com.suv.flat.mappers.OwnersMapper;
import com.suv.flat.mappers.ReasonsMapper;
import com.suv.flat.mappers.TransactionMapper;
import com.suv.flat.mappers.TransactionViewMapper;
import com.suv.flat.model.AccountSummary;
import com.suv.flat.model.Owners;
import com.suv.flat.model.Reasons;
import com.suv.flat.model.TransactionView;
import com.suv.flat.model.Transactions;

public class TransactionJDBCTemplate implements TransactionDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource=ds;
		this.jdbcTemplateObject=new JdbcTemplate(dataSource);
	}

	@Override
	public int enterDeposit(Transactions trtx,int orgId) {
		int trtx_id=-1;
		try{
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final String SQL = "insert into transactions(owner_id,trtx_date,trtx_amount,trtx_type,trtx_reason,trtx_user,trtx_comment,trtx_mod_date,org_id) values (?,STR_TO_DATE(?,'%d-%b-%Y'),?,'CR',?,?,?,CURRENT_TIMESTAMP(),?)";
			/*jdbcTemplateObject.update(SQL, trtx.getOwner_id(),(-1*trtx.getTrtx_amount()),trtx.getTrtx_reason(),trtx.getTrtx_user(),trtx.getTrtx_comment(),keyHolder);*/
			final Transactions f_trtx=trtx;
			final int f_orgId=orgId;
			jdbcTemplateObject.update(new PreparedStatementCreator() {           
                @Override
                public PreparedStatement createPreparedStatement(Connection connection)
                        throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, f_trtx.getOwner_id());
                    ps.setString(2, f_trtx.getTrtx_date());
                    ps.setDouble(3, (-1*f_trtx.getTrtx_amount()));
                    ps.setString(4, f_trtx.getTrtx_reason());
                    ps.setInt(5, f_trtx.getTrtx_user());
                    ps.setString(6, f_trtx.getTrtx_comment());
                    ps.setInt(7, f_orgId);
                    System.out.println("SQL : " + ps.toString());
                    return ps;
                    
                }
            }, keyHolder);
			trtx_id=keyHolder.getKey().intValue();
			System.out.println("Created Deposit Record : " + trtx_id);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		return trtx_id;
		
	}

	@Override
	public List<Transactions> getAllTransactions(int ownerId) {
		String SQL = "select * from transactions where owner_id=" + ownerId;
		List<Transactions> transactions = jdbcTemplateObject.query(SQL, new TransactionMapper());
		return transactions;
	}

	@Override
	public List<Transactions> getAllTransactions(String startDate,
			String endDate,int orgId) {
		String SQL = "select * from transactions where trtx_date>='" + startDate + "' and trtx_date<='" + endDate + "' and org_id=" + orgId;
		List<Transactions> transactions = jdbcTemplateObject.query(SQL, new TransactionMapper());
		return transactions;
	}

	@Override
	public Transactions getTransaction(int trtxId) {
		String SQL = "select * from transactions where trtx_id=" + trtxId;
		Transactions transaction = jdbcTemplateObject.queryForObject(SQL, new TransactionMapper());
		return transaction;
	}

	@Override
	public int enterExpence(Transactions trtx,int orgId) {
		int trtx_id=-1;
		try{
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final String SQL = "insert into transactions(owner_id,trtx_date,trtx_amount,trtx_type,trtx_reason,trtx_user,trtx_comment,trtx_mod_date,org_id) values (?,STR_TO_DATE(?,'%d-%b-%Y'),?,'DR',?,?,?,CURRENT_TIMESTAMP(),?)";
			/*jdbcTemplateObject.update(SQL, trtx.getOwner_id(),(-1*trtx.getTrtx_amount()),trtx.getTrtx_reason(),trtx.getTrtx_user(),trtx.getTrtx_comment(),keyHolder);*/
			final Transactions f_trtx=trtx;
			final int f_orgId=orgId;
			jdbcTemplateObject.update(new PreparedStatementCreator() {           
                @Override
                public PreparedStatement createPreparedStatement(Connection connection)
                        throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, f_trtx.getOwner_id());
                    ps.setString(2, f_trtx.getTrtx_date());
                    ps.setDouble(3, (+1*f_trtx.getTrtx_amount()));
                    ps.setString(4, f_trtx.getTrtx_reason());
                    ps.setInt(5, f_trtx.getTrtx_user());
                    ps.setString(6, f_trtx.getTrtx_comment());
                    ps.setInt(7, f_orgId);
                    System.out.println("SQL : " + ps.toString());
                    return ps;
                    
                }
            }, keyHolder);
			trtx_id=keyHolder.getKey().intValue();
			System.out.println("Created Expense Record : " + trtx_id);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		return trtx_id;
	}

	@Override
	public List<Reasons> getAllReasons(int orgId) {
		String SQL = "select * from reasons where org_id="+orgId;
		List<Reasons> reasons = jdbcTemplateObject.query(SQL, new ReasonsMapper());
		return reasons;
	}

	@Override
	public List<Owners> getOwner(String ownerName) {
		String SQL = "select * from accounts where ucase(owner_name) like '%" + ownerName.toUpperCase() + "%'";
		List<Owners> owners = jdbcTemplateObject.query(SQL, new OwnersMapper());
		return owners;
	}
	
	@Override
	public List<Owners> getAllOwners(int orgId) {
		String SQL = "select * from accounts where floor<>'-1' and org_id="+ orgId;
		List<Owners> owners = jdbcTemplateObject.query(SQL, new OwnersMapper());
		return owners;
	}

	@Override
	public Owners getOwnerFromID(int ownerId) {
		String SQL = "select * from accounts where owner_id=" + ownerId;
		Owners owner = jdbcTemplateObject.queryForObject(SQL, new OwnersMapper());
		return owner;
	}

	@Override
	public int createOwner(Owners owner,int orgId) {
		int trtx_id=-1;
		try{
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final String SQL = "insert into accounts(owner_name,flat_number,floor,is_active,create_date,org_id) values (?,?,?,?,CURRENT_TIMESTAMP(),?)";
			/*jdbcTemplateObject.update(SQL, trtx.getOwner_id(),(-1*trtx.getTrtx_amount()),trtx.getTrtx_reason(),trtx.getTrtx_user(),trtx.getTrtx_comment(),keyHolder);*/
			final Owners f_owner=owner;
			final int f_orgId=orgId;
			jdbcTemplateObject.update(new PreparedStatementCreator() {           
                @Override
                public PreparedStatement createPreparedStatement(Connection connection)
                        throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, f_owner.getOwner_name());
                    ps.setString(2, f_owner.getFlat_number());
                    ps.setString(3, f_owner.getFloor());
                    ps.setBoolean(4, f_owner.getIs_active());
                    ps.setInt(5, f_orgId);
                    System.out.println("SQL : " + ps.toString());
                    return ps;
                    
                }
            }, keyHolder);
			trtx_id=keyHolder.getKey().intValue();
			System.out.println("Created Owner Record : " + trtx_id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return trtx_id;
	}

	@Override
	public int updateOwner(Owners owner) {
		int trtx_id=owner.getOwner_id();
		try{
			
			final String SQL = "update accounts set owner_name=?,flat_number=?,floor=?,is_active=?,mod_date=CURRENT_TIMESTAMP() where owner_id=?";
			//jdbcTemplateObject.update(SQL, owner.getOwner_name(),owner.getFlat_number(),owner.getFloor(),owner.getIs_active(),owner.getOwner_id());
			final Owners f_owner=owner;
			jdbcTemplateObject.update(new PreparedStatementCreator() {           
                @Override
                public PreparedStatement createPreparedStatement(Connection connection)
                        throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(SQL);
                    ps.setString(1, f_owner.getOwner_name());
                    ps.setString(2, f_owner.getFlat_number());
                    ps.setString(3, f_owner.getFloor());
                    ps.setBoolean(4, f_owner.getIs_active());
                    ps.setInt(5, f_owner.getOwner_id());
                    System.out.println("SQL : " + ps.toString());
                    return ps;
                }
            });
			
			System.out.println("Updated Owner Record : " + trtx_id);
		}catch(Exception ex){
			trtx_id=-1;
			ex.printStackTrace();
		}
		return trtx_id;
	}

	@Override
	public String createReason(Reasons reason,int orgId) {
		int trtx_id=-1;
		String responseFlag="OK";
		try{
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final String SQL = "insert into reasons(reason,create_date,org_id) values (?,CURRENT_TIMESTAMP(),?)";
			/*jdbcTemplateObject.update(SQL, trtx.getOwner_id(),(-1*trtx.getTrtx_amount()),trtx.getTrtx_reason(),trtx.getTrtx_user(),trtx.getTrtx_comment(),keyHolder);*/
			final Reasons f_reason=reason;
			final int f_orgId=orgId;
			jdbcTemplateObject.update(new PreparedStatementCreator() {           
                @Override
                public PreparedStatement createPreparedStatement(Connection connection)
                        throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, f_reason.getReason());
                    ps.setInt(2, f_orgId);
                    System.out.println("SQL : " + ps.toString());
                    return ps;
                }
            }, keyHolder);
			trtx_id=keyHolder.getKey().intValue();
			System.out.println("Created Owner Record : " + trtx_id);
		}catch(Exception ex){
			ex.printStackTrace();
			responseFlag="Error in reason creation. "+ ex.getMessage();
		}
		
		return responseFlag;
	}

	@Override
	public String updateReason(Reasons reason) {
		
		String responseFlag="OK";
		try{
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final String SQL = "update reasons set reason=? where reason_id=?";
			/*jdbcTemplateObject.update(SQL, trtx.getOwner_id(),(-1*trtx.getTrtx_amount()),trtx.getTrtx_reason(),trtx.getTrtx_user(),trtx.getTrtx_comment(),keyHolder);*/
			final Reasons f_reason=reason;
			jdbcTemplateObject.update(new PreparedStatementCreator() {           
                @Override
                public PreparedStatement createPreparedStatement(Connection connection)
                        throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, f_reason.getReason());
                    ps.setInt(2, f_reason.getReason_id());
                    System.out.println("SQL : " + ps.toString());
                    return ps;
                }
            }, keyHolder);
			System.out.println("Updated Reason Record ");
		}catch(Exception ex){
			ex.printStackTrace();
			responseFlag="Error in reason creation. "+ ex.getMessage();
		}
		return responseFlag;
	}

	@Override
	public String deleteReason(int reasonId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reasons getReasonFromID(int reasonId) {
		String SQL = "select * from reasons where reason_id=" + reasonId;
		Reasons reason = jdbcTemplateObject.queryForObject(SQL, new ReasonsMapper());
		return reason;
	}

	@Override
	public List<TransactionView> getAllTransactions(String startDate,
			String endDate, int ownerId,int orgId) {
		String SQL = "select * from transaction_view where tx_date>=STR_TO_DATE('" + startDate + "','%d-%b-%Y') and tx_date<=STR_TO_DATE('" + endDate + "','%d-%b-%Y') and org_id="+ orgId;
		if(ownerId!=-1){
			SQL+=" and owner_id=" + ownerId;
		}
		List<TransactionView> transactions = jdbcTemplateObject.query(SQL, new TransactionViewMapper());
		return transactions;
	}

	@Override
	public List<TransactionView> getAllCreditTransactions(String startDate,
			String endDate,int orgId) {
		String SQL = "select * from transaction_view where trtx_type='CR' and tx_date>=STR_TO_DATE('" + startDate + "','%d-%b-%Y') and tx_date<=STR_TO_DATE('" + endDate + "','%d-%b-%Y') and org_id=" + orgId;
		System.out.println("Query >>>> " + SQL);
		List<TransactionView> transactions = jdbcTemplateObject.query(SQL, new TransactionViewMapper());
		return transactions;
	}

	@Override
	public List<TransactionView> getAllDebitTransactions(String startDate,
			String endDate, int orgId) {
		String SQL = "select * from transaction_view where trtx_type='DR' and tx_date>=STR_TO_DATE('" + startDate + "','%d-%b-%Y') and tx_date<=STR_TO_DATE('" + endDate + "','%d-%b-%Y') and org_id="+ orgId;
		List<TransactionView> transactions = jdbcTemplateObject.query(SQL, new TransactionViewMapper());
		return transactions;

	}

	@Override
	public AccountSummary getAccountSummary(int orgId) {
		String SQL = "select * from account_summary where org_id="+ orgId;
		AccountSummary summary = jdbcTemplateObject.queryForObject(SQL, new AccountSummaryMapper());
		return summary;
	}

}
