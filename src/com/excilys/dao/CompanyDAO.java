package com.excilys.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.data.Company;

//Singleton
public class CompanyDAO extends DAO<Company> {
	private static CompanyDAO INSTANCE = null;
	public static final String TABLE = "company";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_ID = "id";

	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyDAO(){
		setTABLE(TABLE);
	}
	
	public static CompanyDAO getInstance(){
		if(INSTANCE == null){
			INSTANCE = new CompanyDAO();
			
		}
		return INSTANCE;
	}
	
	//Insertion
	
	//Suppresion
	
	//Modification
	
	//Selection
	
	
	protected Company entry(ResultSet rs){
		Company c = null;
		try {
			c = new Company(rs.getString(ATTR_NAME));
			c.setId(rs.getInt(ATTR_ID));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}


	@Override
	public String getCreateQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareCreateStatement(PreparedStatement ps,
			SearchWrapper<Company> sw) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getCreateResult(ResultSet rs, SearchWrapper<Company> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRetrieveQuery(SearchWrapper<Company> sw) {
		String query = "SELECT * FROM " + TABLE ;
		if(sw.getItems().size() == 1)// get by id
			query += " WHERE "+ ATTR_ID +"=? ;";
		return query ;
	}

	@Override
	public void prepareRetrieveStatement(PreparedStatement ps,
			SearchWrapper<Company> sw) throws SQLException {
		if(sw.getItems().size() == 1)// get by id
			ps.setInt(1, sw.getItems().get(0).getId());
		
	}

	@Override
	public void getRetrieveResult(ResultSet rs, SearchWrapper<Company> sw)
			throws SQLException {
		List<Company> companies = new ArrayList<Company>();
		while(rs.next())
			companies.add(entry(rs));
		
		sw.setItems(companies);
		
	}

	@Override
	public String getCountQuery(SearchWrapper<Company> sw) {
		// TODO Auto-generated method stub
		return "SELECT COUNT(*) FROM " + TABLE ;
	}

	@Override
	public void prepareCountStatement(PreparedStatement ps,
			SearchWrapper<Company> sw) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getCountResult(ResultSet rs, SearchWrapper<Company> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUpdateQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement ps,
			SearchWrapper<Company> sw) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getUpdateResult(ResultSet rs, SearchWrapper<Company> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDeleteQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement ps,
			SearchWrapper<Company> sw) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDeleteResult(ResultSet rs, SearchWrapper<Company> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}


	


	
}
