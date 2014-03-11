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

import com.excilys.data.Company;
import com.excilys.data.Computer;

//Singleton
public class CompanyDAO {
	private static CompanyDAO INSTANCE = null;
	private Map<Integer,Company> companies = null; 
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
	
	public Map<Integer,Company> getAll(){
		if(companies == null){
		companies = new HashMap<Integer,Company>();
		Connection cn = DatabaseHandler.getInstance().getConnection();
		Statement st = null;
		ResultSet rs  = null;
		try {
			st = cn.createStatement();
			rs = st.executeQuery("SELECT * FROM " + TABLE_COMPANY + " ;");
			while(rs.next()){
				Company company = entryToCompany(rs);
				companies.put(Integer.valueOf(company.getId()),company);
			}
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
		}	
		return companies;
		
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
