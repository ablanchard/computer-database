package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.excilys.data.Company;
import com.excilys.data.Computer;

//Singleton
public class ComputerDAO {
	
	private static ComputerDAO INSTANCE = null;
	private static String TABLE_COMPUTER = "computer";
	private static String ATTR_NAME = "name";
	private static String ATTR_INTRODUCTION = "introduced";
	private static String ATTR_DISCONTINUED = "discontinued";
	private static String ATTR_ID = "id";
	private static String ATTR_COMPANY_ID = "company_id";
	private static String JOINTURE_QUERY = "SELECT * FROM "+TABLE_COMPUTER+" C LEFT OUTER JOIN " + CompanyDAO.TABLE_COMPANY+" COM ON C."+ATTR_COMPANY_ID+" = COM."+CompanyDAO.ATTR_ID;
	
	private ComputerDAO(){
		
	}
	
	public static synchronized ComputerDAO getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ComputerDAO();
		}
		return INSTANCE;
	}
	
	//Insertion
	public void insert(Computer c){
		Connection cn = null;
		int rs ;
		PreparedStatement ps =null;
		String query = "INSERT INTO " + TABLE_COMPUTER + " ("+ATTR_NAME+" , "+ATTR_INTRODUCTION+" , "+ATTR_DISCONTINUED+" , "+ATTR_COMPANY_ID+" ) VALUES ( ? , ? , ? , ? )";
		try{
		
			cn = DatabaseHandler.getInstance().getConnection();
			
			ps = cn.prepareStatement(query);
			ps.setString(1,c.getName());
			ps.setTimestamp(2, Timestamp.valueOf(c.getIntroduction().toString()));
			ps.setTimestamp(3, Timestamp.valueOf(c.getDiscontinued().toString()));
			if(c.getCompany()==null)
				ps.setNull(4, 0);
			else
				ps.setFloat(4, c.getCompany().getId());
			
			
			rs = ps.executeUpdate();
			if(rs != 0)
				System.out.println("Insertion succeed");
		
		} 
		catch (SQLException e){
			System.out.println("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				if(ps != null)
					ps.close();
				if(cn !=null)
					cn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	//Suppresion
	
	//Modification
	
	//Selection
	public Computer getById(int id){
		Computer c = null;
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		try {
			ps = cn.prepareStatement(JOINTURE_QUERY + " WHERE C."+ ATTR_ID +"=? ;");
			ps.setInt(1,id);
			rs = ps.executeQuery();
			if(rs.next())
				c = entryToComputer(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
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
		return c;
		
	}
	
	public List<Computer> getAll(){
		List<Computer> c = new ArrayList<Computer>();
		Connection cn = DatabaseHandler.getInstance().getConnection();
		Statement st = null;
		ResultSet rs  = null;
		try {
			st = cn.createStatement();
			rs = st.executeQuery(JOINTURE_QUERY);
			while(rs.next())
				c.add(entryToComputer(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(cn!=null){
				try {
					cn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}}
			if(st!=null){
				try {
					st.close();
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
	
	
	
}
