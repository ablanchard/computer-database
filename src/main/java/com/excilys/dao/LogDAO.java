package com.excilys.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.data.Log;

@Component
public class LogDAO extends DAO<Log> {
	private static final String TABLE = "log";
	private static final String ATTR_EXECUTED = "executed_on";
	private static final String ATTR_TARGET = "target";
	private static final String ATTR_OPERATION = "operation";
	private static final String ATTR_ID = "id";
	private static final String ATTR_COMMAND = "command";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogDAO.class);

	private LogDAO(){
		setTABLE(TABLE);
		setLogger(LOGGER);
	}
	
	




	@Override
	public String getCreateQuery() {
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append( TABLE );
		query.append( " (");
		query.append(ATTR_TARGET);
		query.append(" , ");
		query.append(ATTR_OPERATION);
		query.append(" , ");
		query.append(ATTR_COMMAND );
		query.append( " ) VALUES ( ? , ? , ?  )");
		return query.toString();		
	}

	@Override
	public void prepareCreateStatement(PreparedStatement ps,
			SearchWrapper<Log> sw) throws SQLException {
		Log l = sw.getItems().get(0);
		ps.setString(1,l.getTarget());
		
		ps.setInt(2,l.getOperation().toInt());
		ps.setString(3,l.getCommand());
	
		
	}

	@Override
	public void getCreateResult(ResultSet rs, SearchWrapper<Log> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRetrieveQuery(SearchWrapper<Log> sw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareRetrieveStatement(PreparedStatement ps,
			SearchWrapper<Log> sw) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getRetrieveResult(ResultSet rs, SearchWrapper<Log> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCountQuery(SearchWrapper<Log> sw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareCountStatement(PreparedStatement ps,
			SearchWrapper<Log> sw) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getCountResult(ResultSet rs, SearchWrapper<Log> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUpdateQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement ps,
			SearchWrapper<Log> sw) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getUpdateResult(ResultSet rs, SearchWrapper<Log> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDeleteQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement ps,
			SearchWrapper<Log> sw) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDeleteResult(ResultSet rs, SearchWrapper<Log> sw)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Log entry(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	


	
}
