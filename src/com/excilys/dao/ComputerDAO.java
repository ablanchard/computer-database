package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.data.Log;

//Singleton
public class ComputerDAO extends DAO<Computer> {
	
	private static ComputerDAO INSTANCE = null;
	private static DatabaseHandler DB = null;
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
	
	final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerDAO(){
		DB= DatabaseHandler.getInstance();
		setTABLE(TABLE);
	}
	
	public static ComputerDAO getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ComputerDAO();
		}
		return INSTANCE;
	}
	
	//Insertion
	public void create(SearchWrapper<Computer> sw) throws DaoException {
		Connection cn = DB.getConnection();
		Computer c = sw.getItems().get(0);
		PreparedStatement ps =null;
		int rs ;
		ResultSet generatedKeys;
		String query = "INSERT INTO " + TABLE + " ("+ATTR_NAME+" , "+ATTR_INTRODUCTION+" , "+ATTR_DISCONTINUED+" , "+ATTR_COMPANY_ID+" ) VALUES ( ? , ? , ? , ? )";
		try{
			ps = cn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1,c.getName());
			
			if(c.getIntroduction() == null)
				ps.setNull(2, 0);
			else
				ps.setTimestamp(2, new Timestamp(c.getIntroduction().getTime()));
			
			if(c.getDiscontinued() == null)
				ps.setNull(3,0);
			else
				ps.setTimestamp(3, new Timestamp(c.getDiscontinued().getTime()));
			
			if(c.getCompany()==null)
				ps.setNull(4, 0);
			else
				ps.setInt(4, c.getCompany().getId());
			
			
			rs = ps.executeUpdate();
		    generatedKeys = ps.getGeneratedKeys();
		    if (generatedKeys.next()) {
	            sw.getItems().get(0).setId(generatedKeys.getInt(1));
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
			logger.info(ps.toString());
			
			if(rs != 0)
				logger.info("Insertion succeed");
			else
				throw new DaoException();
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
			throw new DaoException() ;
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	//Suppresion
	public void delete(SearchWrapper<Computer> sw)  throws DaoException  {
		Connection cn = DB.getConnection();
		PreparedStatement ps =null;
		int rs ;
		String query = "DELETE FROM " + TABLE + " WHERE "+ATTR_ID+"= ?";
		try{
			ps = cn.prepareStatement(query);
			ps.setInt(1,sw.getItems().get(0).getId());
			
			rs = ps.executeUpdate();

			logger.info(ps.toString());
			
			if(rs != 0)
				logger.info("Deletion succeed");
			else
				throw new DaoException();
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	//Modification
	public void update(SearchWrapper<Computer> sw)  throws DaoException  {
		Connection cn = DB.getConnection();
		Computer c = sw.getItems().get(0);
		PreparedStatement ps =null;
		int rs ;
		String query = "UPDATE " + TABLE + " SET "+ATTR_NAME+" = ? , "+ ATTR_INTRODUCTION+" = ? , "+ATTR_DISCONTINUED+" = ? , "+ATTR_COMPANY_ID+" = ? WHERE "+ ATTR_ID +" = ? ";
		try{
			ps = cn.prepareStatement(query);
			ps.setString(1,c.getName());
			
			if(c.getIntroduction() == null)
				ps.setNull(2, 0);
			else
				ps.setTimestamp(2, new Timestamp(c.getIntroduction().getTime()));
			
			if(c.getDiscontinued() == null)
				ps.setNull(3,0);
			else
				ps.setTimestamp(3, new Timestamp(c.getDiscontinued().getTime()));
			
			if(c.getCompany()==null)
				ps.setNull(4, 0);
			else
				ps.setInt(4, c.getCompany().getId());
			
			ps.setInt(5, c.getId());
			
			rs = ps.executeUpdate();

			logger.info("Update {}",c);
			
			/*if(rs != 0)
				logger.info("Update of {} succeed",c);
			else
				throw new SQLException();*/
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	
	//Selection
	
		
	public void retrieve(SearchWrapper<Computer> sw)  throws DaoException  {
		List<Computer> computers = new ArrayList<Computer>();
		String query = SELECT_QUERY;
		
		if(sw.getItems().size() == 1)
			query += " WHERE C."+ ATTR_ID +"=? ;";
		else{
		
			if(sw.getQuery() != null)
				query += SEARCH_CLAUSE ;
			
			query += getOrderClause(sw);
			query += getLimitClause(sw);
		}
		
		Connection cn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		
		try {				
			ps = cn.prepareStatement(query);
			
			if(sw.getItems().size() == 1){
				ps.setInt(1,sw.getItems().get(0).getId());
			}
			else{
			
				if(sw.getQuery() != null){
					ps.setString(1,"%"+sw.getQuery()+"%");
					ps.setString(2,"%"+sw.getQuery()+"%");
				}
			
			}
			rs = ps.executeQuery();

			logger.info(ps.toString());
			
			while(rs.next())
				computers.add(entry(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,rs);
					
		}
		sw.setItems(computers);
		sw.setCount(count(sw));
	}
	
	public int count(SearchWrapper<Computer> sw)  throws DaoException  {
		int nbComputers = 0;
		String query = COUNT_QUERY;
		
		if(sw.getQuery() != null)
			query += SEARCH_CLAUSE ;
		
		//query += getOrderClause(sw);
		
		Connection cn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		
		try {				
			ps = cn.prepareStatement(query);
			
			if(sw.getQuery() != null){
				ps.setString(1,"%"+sw.getQuery()+"%");
				ps.setString(2,"%"+sw.getQuery()+"%");
			}
			
			rs = ps.executeQuery();

			logger.info(ps.toString());
			if(rs.next())
				nbComputers = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,rs);
					
		}
		return nbComputers;
	}
	
	
	protected Computer entry(ResultSet rs)  throws DaoException  {
		Computer c = null;
		//Map<Integer,Company> companies = CompanyDAO.getInstance().getAll();
		try {
			String companyName = rs.getString(7);
			Company company = (rs.wasNull())?null:new Company(companyName);
			if(company !=null)
				company.setId(rs.getInt(6));
			
			c = new Computer(rs.getString(ATTR_NAME),rs.getTimestamp(ATTR_INTRODUCTION),rs.getTimestamp(ATTR_DISCONTINUED),company);
			c.setId(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
		
		return c;
	}
	/* 
	private String getOrderClause(SearchWrapper sw){
		String orderClause ;
		
		if(sw.getOrderCol() == null)
			orderClause = " ";
		else {
			orderClause = " ORDER BY " ;
			switch(sw.getOrderCol()){
				case intro:
					orderClause += "C."+ATTR_INTRODUCTION;
					break;
				case disc:
					orderClause += "C."+ATTR_DISCONTINUED;
					break;
				case company:
					orderClause += "COM."+CompanyDAO.ATTR_NAME;
					break;
				default://case "name"
					orderClause += "C."+ATTR_NAME;
					break;
			}
						
			if(sw.getOrderDirection() !=  null){
				if(sw.getOrderDirection() == OrderDirection.desc)
					orderClause += " DESC";
			}
		
		}
		return orderClause;
		
	}
	
	private String getLimitClause(SearchWrapper sw){
		String limitClause = "" ;
		if(sw.getPage() != 0){
			int offset = (sw.getPage()-1)*sw.getNbComputersPerPage();
			limitClause += " LIMIT " + sw.getNbComputersPerPage() + " OFFSET " + offset ;
		}
		return limitClause;
	}

	private void closeObjects(Connection cn,PreparedStatement ps, ResultSet rs){
		if(cn!=null){
				try {
					cn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}}
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}}
	}*/


	
	
}
