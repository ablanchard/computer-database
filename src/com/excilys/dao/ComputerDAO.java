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

//Singleton
public class ComputerDAO {
	
	private static ComputerDAO INSTANCE = null;
	private static DatabaseHandler DB = null;
	private static final String TABLE_COMPUTER = "computer";
	private static final String ATTR_NAME = "name";
	private static final String ATTR_INTRODUCTION = "introduced";
	private static final String ATTR_DISCONTINUED = "discontinued";
	private static final String ATTR_ID = "id";
	private static final String ATTR_COMPANY_ID = "company_id";
	private static final String JOINTURE_QUERY = "SELECT * FROM "+TABLE_COMPUTER+" C LEFT OUTER JOIN "+ CompanyDAO.TABLE_COMPANY+" COM ON C."+ATTR_COMPANY_ID+" = COM."+CompanyDAO.ATTR_ID;
	private static final String SEARCH_QUERY = JOINTURE_QUERY + " WHERE C." + ATTR_NAME + " LIKE ? OR COM." + CompanyDAO.ATTR_NAME + " LIKE ? ";
	final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	
	private ComputerDAO(){
		DB= DatabaseHandler.getInstance();
	}
	
	public static ComputerDAO getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ComputerDAO();
		}
		return INSTANCE;
	}
	
	//Insertion
	public void create(Computer c){
		Connection cn = DB.getConnection();
		PreparedStatement ps =null;
		int rs ;
		String query = "INSERT INTO " + TABLE_COMPUTER + " ("+ATTR_NAME+" , "+ATTR_INTRODUCTION+" , "+ATTR_DISCONTINUED+" , "+ATTR_COMPANY_ID+" ) VALUES ( ? , ? , ? , ? )";
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
				ps.setFloat(4, c.getCompany().getId());
			
			
			rs = ps.executeUpdate();
			logger.info(ps.toString());
			if(rs != 0)
				logger.info("Insertion succeed");
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	//Suppresion
	public void deleteById(int id){
		Connection cn = DB.getConnection();
		PreparedStatement ps =null;
		int rs ;
		String query = "DELETE FROM " + TABLE_COMPUTER + " WHERE "+ATTR_ID+"= ?";
		try{
			ps = cn.prepareStatement(query);
			ps.setInt(1,id);
			
			rs = ps.executeUpdate();

			logger.info(ps.toString());
			if(rs != 0)
				logger.info("Deletion succeed");
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	//Modification
	public void update(Computer c){
		Connection cn = DB.getConnection();
		PreparedStatement ps =null;
		int rs ;
		String query = "UPDATE " + TABLE_COMPUTER + " SET "+ATTR_NAME+" = ? , "+ ATTR_INTRODUCTION+" = ? , "+ATTR_DISCONTINUED+" = ? , "+ATTR_COMPANY_ID+" = ? WHERE "+ ATTR_ID +" = ? ";
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
				ps.setFloat(4, c.getCompany().getId());
			
			ps.setInt(5, c.getId());
			
			rs = ps.executeUpdate();

			logger.info(ps.toString());
			if(rs != 0)
				logger.info("Update succeed");
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	
	//Selection
	public Computer retriveById(int id){
		Computer c = null;
		Connection cn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		String query = JOINTURE_QUERY + " WHERE C."+ ATTR_ID +"=? ;";
		try {
			ps = cn.prepareStatement(query);
			ps.setInt(1,id);
			rs = ps.executeQuery();

			logger.info(ps.toString());
			if(rs.next())
				c = entryToComputer(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeObjects(cn,ps,rs);
		}
		return c;
		
	}
	
		
	public List<Computer> retrieveAll(String order,String sens){
		List<Computer> c = new ArrayList<Computer>();
		Connection cn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		
		try {
			if(order == null)
				ps = cn.prepareStatement(JOINTURE_QUERY);
			else
				ps = cn.prepareStatement(JOINTURE_QUERY + " ORDER BY " + getOrderByQuery(order, sens));
			rs = ps.executeQuery();

			logger.info(ps.toString());
			while(rs.next())
				c.add(entryToComputer(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeObjects(cn,ps,rs);
					
		}
		return c;
	}
	
		
	public List<Computer> searchByName(String search,String order,String sens){
		List<Computer> c = new ArrayList<Computer>();
		Connection cn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		
		try {
			if(order == null)
				ps = cn.prepareStatement(SEARCH_QUERY);
			else
				ps = cn.prepareStatement(SEARCH_QUERY + " ORDER BY " + getOrderByQuery(order, sens));
			
			ps.setString(1,"%"+search+"%");
			ps.setString(2,"%"+search+"%");
			
						
			rs = ps.executeQuery();

			logger.info(ps.toString());
			while(rs.next())
				c.add(entryToComputer(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeObjects(cn,ps,rs);
					
		}
		return c;
	}
	
	
	private Computer entryToComputer(ResultSet rs){
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
		}
		
		return c;
	}
	
	private String getOrderByQuery(String order,String sens){
		String orderCol ;
		switch(order){
			case "intro":
				orderCol = "C."+ATTR_INTRODUCTION;
				break;
			case "disc":
				orderCol = "C."+ATTR_DISCONTINUED;
				break;
			case "company":
				orderCol = "COM."+CompanyDAO.ATTR_NAME;
				break;
			default://case "name"
				orderCol = "C."+ATTR_NAME;
				break;
		}
					
		if(sens !=null){
			if(sens.equals("desc"))
				orderCol += " DESC";
		}
		return orderCol;
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
	}
	
	
	
}
