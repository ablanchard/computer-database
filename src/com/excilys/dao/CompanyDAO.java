package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.data.Company;
import com.excilys.data.Computer;

//Singleton
public class CompanyDAO {
	private static CompanyDAO INSTANCE = null;
	private static String TABLE_COMPANY = "company";
	private static String ATTR_NAME = "name";
	private static String ATTR_ID = "id";
	
	private CompanyDAO(){
		
	}
	
	public static synchronized CompanyDAO getInstance(){
		if(INSTANCE == null){
			INSTANCE = new CompanyDAO();
		}
		return INSTANCE;
	}
	
	//Insertion
	
	//Suppresion
	
	//Modification
	
	//Selection
	public Company getById(int id){
		Company c = null;
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		try {
			ps = cn.prepareStatement("SELECT * FROM " + TABLE_COMPANY + " WHERE "+ ATTR_ID +"=? ;");
			ps.setInt(1,id);
			rs = ps.executeQuery();
			if(rs.next())
				c = entryToCompany(rs);
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
	
	private Company entryToCompany(ResultSet rs){
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
}
