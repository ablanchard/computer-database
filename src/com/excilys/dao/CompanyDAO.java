package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.data.Log;
import com.excilys.servlet.EditComputerServlet;

//Singleton
public class CompanyDAO extends DAO<Company> {
	private static CompanyDAO INSTANCE = null;
	private static DatabaseHandler DB = null;
	public static final String TABLE = "company";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_ID = "id";

	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyDAO(){
		DB = DatabaseHandler.getInstance();
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
	
	public void retrieve(SearchWrapper<Company> sw) throws DaoException {
		List<Company> companies = new ArrayList<Company>();
		Connection cn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		String query = "SELECT * FROM " + TABLE ;
		if(sw.getItems().size() == 1)// get by id
			query += " WHERE "+ ATTR_ID +"=? ;";
		try {
			ps = cn.prepareStatement(query);
			if(sw.getItems().size() == 1)// get by id
				ps.setInt(1, sw.getItems().get(0).getId());
			rs = ps.executeQuery();

			logger.info(ps.toString());
			
			while(rs.next()){
				companies.add(entry(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,rs);
					
		}
		sw.setItems(companies);
	}
	
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
	public void create(SearchWrapper<Company> sw) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SearchWrapper<Company> sw) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SearchWrapper<Company> sw) throws DaoException {
		// TODO Auto-generated method stub
		
	}


	


	
}
