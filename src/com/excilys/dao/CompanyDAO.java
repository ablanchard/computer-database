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
import com.excilys.servlet.EditComputerServlet;

//Singleton
public class CompanyDAO {
	private static CompanyDAO INSTANCE = null;
	private static DatabaseHandler DB = null;
	public static final String TABLE_COMPANY = "company";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_ID = "id";

	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyDAO(){
		DB = DatabaseHandler.getInstance();
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
	public Company retrieveById(int id){
		Company c = null;
		Connection cn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		String query = "SELECT * FROM " + TABLE_COMPANY + " WHERE "+ ATTR_ID +"=? ;";
		try {
			ps = cn.prepareStatement(query);
			ps.setInt(1,id);
			rs = ps.executeQuery();
			if(rs.next())
				c = entryToCompany(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeObjects(cn,ps,rs);
		}
		return c;
	}
	
	public List<Company> retrieveAll(){
		List<Company> companies = new ArrayList<Company>();
		Connection cn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		String query = "SELECT * FROM " + TABLE_COMPANY + " ;";
		try {
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				companies.add(entryToCompany(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeObjects(cn,ps,rs);
					
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
