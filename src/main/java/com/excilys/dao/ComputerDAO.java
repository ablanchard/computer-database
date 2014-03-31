package com.excilys.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.data.Company;
import com.excilys.data.Computer;

@Component
public class ComputerDAO extends DAO<Computer> {
	
	public static final String TABLE = "computer";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_INTRODUCTION = "introduced";
	public static final String ATTR_DISCONTINUED = "discontinued";
	private static final String ATTR_ID = "id";
	private static final String ATTR_COMPANY_ID = "company_id";
	private static final String TABLE_JOINTURE = TABLE+" C LEFT OUTER JOIN "+ CompanyDAO.TABLE+" COM ON C."+ATTR_COMPANY_ID+" = COM."+CompanyDAO.ATTR_ID;
	private static final String SELECT_QUERY = "SELECT * FROM "+TABLE_JOINTURE;
	private static final String COUNT_QUERY = "SELECT COUNT(*) FROM " + TABLE_JOINTURE;
	private static final String SEARCH_CLAUSE = " WHERE C." + ATTR_NAME + " LIKE ? OR COM." + CompanyDAO.ATTR_NAME + " LIKE ? ";
	//private static final String LIMIT_CLAUSE = " LIMIT ? OFFSET ? ";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerDAO(){
		setTABLE(TABLE);
		setLogger(LOGGER);
	}
	
	
	
		
	protected Computer entry(ResultSet rs)  throws SQLException  {
		Computer c = null;
		//Map<Integer,Company> companies = CompanyDAO.getInstance().getAll();
		
			String companyName = rs.getString(7);
			Company company = (rs.wasNull())?null:new Company(companyName);
			if(company !=null)
				company.setId(rs.getInt(6));
			
			c = new Computer(rs.getString(ATTR_NAME),rs.getTimestamp(ATTR_INTRODUCTION),rs.getTimestamp(ATTR_DISCONTINUED),company);
			c.setId(rs.getInt(1));
		
		return c;
	}
	

	@Override
	public String getCreateQuery() {
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append( TABLE );
		query.append( " (");
		query.append(ATTR_NAME);
		query.append(" , ");
		query.append(ATTR_INTRODUCTION);
		query.append(" , ");
		query.append(ATTR_DISCONTINUED);
		query.append(" , ");
		query.append(ATTR_COMPANY_ID);
		query.append(" ) VALUES ( ? , ? , ? , ? )");
		return query.toString();

	}

	@Override
	public void prepareCreateStatement(PreparedStatement ps, SearchWrapper<Computer> sw)throws SQLException {
		Computer c = sw.getItems().get(0);
		
			ps.setString(1,c.getName());
			
			if(c.getIntroduced() == null){
				//ps.setTimestamp(2, new Timestamp(0));//MySQL compatibility
				ps.setNull(2,0);
			}
			else{
				ps.setTimestamp(2, new Timestamp(c.getIntroduced().getTime()));
			}
			
			if(c.getDiscontinued() == null){
				//ps.setTimestamp(3, new Timestamp(0));//MySQL compatibility
				ps.setNull(3,0);
			}
			else{
				ps.setTimestamp(3, new Timestamp(c.getDiscontinued().getTime()));
			}
			
			if(c.getCompany()==null){
				ps.setNull(4, 0);
			}
			else{
				ps.setInt(4, c.getCompany().getId());
			}
	
		
	}

	@Override
	public void getCreateResult(ResultSet gk,SearchWrapper<Computer> sw) throws SQLException {
		 if (gk.next()) {
	            sw.getItems().get(0).setId(gk.getInt(1));
	            LOGGER.debug("Generated id {}",gk.getInt(1));
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
	}

	@Override
	public String getRetrieveQuery(SearchWrapper<Computer> sw) {
		StringBuilder query = new StringBuilder(SELECT_QUERY);
		
		if(sw.getItems().size() == 1){
			query.append(" WHERE C."+ ATTR_ID +"=? ;");
		}
		else{
		
			if(sw.getQuery() != null)
				query.append(SEARCH_CLAUSE );
			
			query.append(getOrderClause(sw));
			query.append(getLimitClause(sw));
		}
		return query.toString();
	}

	@Override
	public void prepareRetrieveStatement(PreparedStatement ps,
			SearchWrapper<Computer> sw) throws SQLException {
		if(sw.getItems().size() == 1){
			ps.setInt(1,sw.getItems().get(0).getId());
		}
		else{
		
			if(sw.getQuery() != null){
				ps.setString(1,"%"+sw.getQuery()+"%");
				ps.setString(2,"%"+sw.getQuery()+"%");
			}
		
		}
		
	}

	@Override
	public void getRetrieveResult(ResultSet rs, SearchWrapper<Computer> sw)throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();
		while(rs.next())
			computers.add(entry(rs));

		sw.setItems(computers);
	}
	
	@Override
	public String getCountQuery(SearchWrapper<Computer> sw) {
		StringBuilder query = new StringBuilder(COUNT_QUERY);
		
		if(sw.getQuery() != null)
			query.append(SEARCH_CLAUSE);
		return query.toString();
	}

	@Override
	public void prepareCountStatement(PreparedStatement ps,
			SearchWrapper<Computer> sw) throws SQLException {
		if(sw.getQuery() != null){
				ps.setString(1,"%"+sw.getQuery()+"%");
				ps.setString(2,"%"+sw.getQuery()+"%");
			}
		
	}

	@Override
	public void getCountResult(ResultSet rs, SearchWrapper<Computer> sw)
			throws SQLException {
		if(rs.next()){
				sw.setCount(rs.getInt(1));
		}
	}

	@Override
	public String getUpdateQuery() {
		StringBuilder query = new StringBuilder("UPDATE ");
		query.append( TABLE);
		query.append( " SET ");
		query.append(ATTR_NAME);
		query.append(" = ? , ");
		query.append( ATTR_INTRODUCTION);
		query.append(" = ? , ");
		query.append(ATTR_DISCONTINUED);
		query.append(" = ? , ");
		query.append(ATTR_COMPANY_ID);
		query.append(" = ? WHERE ");
		query.append( ATTR_ID );
		query.append(" = ? ");
		return query.toString();
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement ps,
			SearchWrapper<Computer> sw) throws SQLException {
		Computer c = sw.getItems().get(0);
		ps.setString(1,c.getName());
			
			if(c.getIntroduced() == null)
				ps.setTimestamp(2, new Timestamp(0));//MySQL compatibility
			else
				ps.setTimestamp(2, new Timestamp(c.getIntroduced().getTime()));
			
			if(c.getDiscontinued() == null)
				ps.setTimestamp(3, new Timestamp(0));//MySQL compatibility
			else
				ps.setTimestamp(3, new Timestamp(c.getDiscontinued().getTime()));
			
			if(c.getCompany()==null)
				ps.setNull(4, 0);
			else
				ps.setInt(4, c.getCompany().getId());
			
			ps.setInt(5, c.getId());
		
	}

	@Override
	public void getUpdateResult(ResultSet rs, SearchWrapper<Computer> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDeleteQuery() {
		StringBuilder query = new StringBuilder("DELETE FROM ");
		query.append(TABLE );
		query.append( " WHERE ");
		query.append(ATTR_ID);
		query.append("= ?");
		return query.toString();
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement ps,
			SearchWrapper<Computer> sw) throws SQLException {
		ps.setInt(1,sw.getItems().get(0).getId());
		
	}

	@Override
	public void getDeleteResult(ResultSet rs, SearchWrapper<Computer> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
