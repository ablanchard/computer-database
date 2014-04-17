package com.excilys.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.data.Log;
import com.excilys.util.SearchWrapper;

@Repository
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
	public void create(SearchWrapper<Log> sw)
			 {
		getSessionFactory().getCurrentSession().save(sw.getItems().get(0));
		
     
		
	}

}