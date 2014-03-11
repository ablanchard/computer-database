package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public List<Computer> getAll(){
		List<Computer> c = new ArrayList<Computer>();
		Connection cn = DatabaseHandler.getInstance().getConnection();
		Statement st = null;
		ResultSet rs  = null;
		try {
			st = cn.createStatement();
			rs = st.executeQuery("SELECT * FROM " + TABLE_COMPUTER + " ;");
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
		Map<Integer,Company> companies = CompanyDAO.getInstance().getAll();
		try {
			//int company_id = rs.getInt(ATTR_COMPANY_ID);
			//Company company = (rs.wasNull())?null:CompanyDAO.getInstance().getById(company_id);
			c = new Computer(rs.getString(ATTR_NAME),rs.getTimestamp(ATTR_INTRODUCTION),rs.getTimestamp(ATTR_DISCONTINUED),companies.get(Integer.valueOf(rs.getInt(ATTR_COMPANY_ID))));
			c.setId(rs.getInt(ATTR_ID));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
	
	
}
