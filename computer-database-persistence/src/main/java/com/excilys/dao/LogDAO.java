package com.excilys.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
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

	public LogDAO(){
		setTABLE(TABLE);
		setLogger(LOGGER);
	}

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
	public void create(JdbcTemplate jdbcTemplate, SearchWrapper<Log> sw)
			 {
		Log l = sw.getItems().get(0);
		

       jdbcTemplate.update(getCreateQuery(), 
        	new Object[] { 
        		l.getTarget(),
	            l.getOperation().toInt(),
	            l.getCommand()
        });
       
     
		
	}

	@Override
	protected void retrieve(JdbcTemplate jbdcTemplate, SearchWrapper<Log> sw)
			 {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void count(JdbcTemplate jbdcTemplate, SearchWrapper<Log> sw)
			 {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update(JdbcTemplate jbdcTemplate, SearchWrapper<Log> sw)
			 {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete(JdbcTemplate jbdcTemplate, SearchWrapper<Log> sw)
			 {
		// TODO Auto-generated method stub
		
	}
}