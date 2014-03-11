package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.data.Company;
import com.excilys.data.Computer;

//Singleton
public class ComputerDAO {
	
	private static ComputerDAO INSTANCE = null;
	private static String TABLE_COMPUTER = "computer";
	private static String ATTR_NAME = "name";
	private static String ATTR_INTRODUCTION = "introduction";
	private static String ATTR_DISCONTINUED = "discontinued";
	private static String ATTR_ID = "id";
	private static String ATTR_COMPANY_ID = "company_id";
	
	private ComputerDAO(){
		
	}
	
	public static synchronized ComputerDAO getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ComputerDAO();
		}
		return INSTANCE;
	}
	
	//Insertion
	
	//Suppresion
	
	//Modification
	
	//Selection
	public Computer getById(int id){
		Computer c = null;
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		try {
			ps = cn.prepareStatement("SELECT * FROM " + TABLE_COMPUTER + " WHERE "+ ATTR_ID +"=? ;");
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
	
	private Computer entryToComputer(ResultSet rs){
		Computer c = null;
		try {
			int company_id = rs.getInt(ATTR_COMPANY_ID);
			Company company = (rs.wasNull())?null:CompanyDAO.getInstance().getById(company_id);
			c = new Computer(rs.getString(ATTR_NAME),rs.getDate(ATTR_INTRODUCTION),rs.getDate(ATTR_DISCONTINUED),company);
			c.setId(rs.getInt(ATTR_ID));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
	
	
}
