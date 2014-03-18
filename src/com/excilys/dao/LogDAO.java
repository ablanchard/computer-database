package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.data.Log;

public class LogDAO {
	private static LogDAO INSTANCE = null;
	private static DatabaseHandler DB = null;
	private static final String TABLE_LOG = "log";
	private static final String ATTR_EXECUTED = "executed_on";
	private static final String ATTR_TARGET = "target";
	private static final String ATTR_OPERATION = "operation";
	private static final String ATTR_ID = "id";
	private static final String ATTR_COMMAND = "command";
	
	final Logger logger = LoggerFactory.getLogger(LogDAO.class);
	private static Connection cn;
	
	private LogDAO(){
		DB= DatabaseHandler.getInstance();
	}
	
	public static LogDAO getInstance(Connection con){
		if(INSTANCE == null){
			INSTANCE = new LogDAO();
		}
		cn = con;
		return INSTANCE;
	}
	
	public void create(Log l){
		//Connection cn = DB.getConnection();
		PreparedStatement ps =null;
		int rs ;
		String query = "INSERT INTO " + TABLE_LOG + " ("+ATTR_TARGET+" , "+ATTR_OPERATION+" , "+ATTR_COMMAND + " ) VALUES ( ? , ? , ?  )";
		try{
			ps = cn.prepareStatement(query);
			ps.setString(1,l.getTarget());
			
			ps.setInt(2,l.getOperation().toInt());
			ps.setString(3,l.getCommand());
		
			
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
	
	private void closeObjects(Connection cn,PreparedStatement ps, ResultSet rs){
		/*if(cn!=null){
				try {
					cn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}}*/
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
