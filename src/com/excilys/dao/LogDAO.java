package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.data.Log;

public class LogDAO extends DAO<Log> {
	private static LogDAO INSTANCE = null;
	private static DatabaseHandler DB = null;
	private static final String TABLE = "log";
	private static final String ATTR_EXECUTED = "executed_on";
	private static final String ATTR_TARGET = "target";
	private static final String ATTR_OPERATION = "operation";
	private static final String ATTR_ID = "id";
	private static final String ATTR_COMMAND = "command";
	
	final Logger logger = LoggerFactory.getLogger(LogDAO.class);

	private LogDAO(){
		DB= DatabaseHandler.getInstance();
	}
	
	public static LogDAO getInstance(){
		if(INSTANCE == null){
			INSTANCE = new LogDAO();
		}
		return INSTANCE;
	}
	
	public void create(SearchWrapper<Log> sw) throws DaoException {
		Log l = sw.getItems().get(0);
		Connection cn = DB.getConnection();
		PreparedStatement ps =null;
		int rs ;
		String query = "INSERT INTO " + TABLE + " ("+ATTR_TARGET+" , "+ATTR_OPERATION+" , "+ATTR_COMMAND + " ) VALUES ( ? , ? , ?  )";
		try{
			ps = cn.prepareStatement(query);
			ps.setString(1,l.getTarget());
			
			ps.setInt(2,l.getOperation().toInt());
			ps.setString(3,l.getCommand());
		
			
			rs = ps.executeUpdate();
			//logger.info(ps.toString());
			if(rs != 0)
				logger.info(l.toString());
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			throw new DaoException();
			//e.printStackTrace();
		} finally{
			closeObjects(cn,ps,null);
		}
	}



	@Override
	protected Log entry(ResultSet rs) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retrieve(SearchWrapper<Log> sw) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SearchWrapper<Log> sw) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SearchWrapper<Log> sw) throws DaoException {
		// TODO Auto-generated method stub
		
	}
	
	


	
}
