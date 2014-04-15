package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Operation;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public abstract class DAO<E> {
	
	public String TABLE ;
	
	Logger LOGGER ;
	
	@Autowired
	private DataSource ds ;

	
	public void create(SearchWrapper<E> sw) throws DaoException {
		operation(sw, Operation.create);
	}
	
	public void retrieve(SearchWrapper<E> sw) throws DaoException {
		operation(sw, Operation.retrieve);
	}
	
	public void count(SearchWrapper<E> sw)  throws DaoException  {
		operation(sw, Operation.count);
	}
	
	public void update(SearchWrapper<E> sw) throws DaoException {
		operation(sw, Operation.update);
	}
	public void delete(SearchWrapper<E> sw) throws DaoException {
		operation(sw, Operation.delete);
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	private void operation(SearchWrapper<E> sw,Operation op)  {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDs());
		
		getLogger().debug("Try {} \"{}\"",op,sw);
		operation(jdbcTemplate,sw,op);
		getLogger().debug("{} suceed",op);
		if(op == Operation.retrieve){
			operation(sw,Operation.count);
		}
			
		
		
	}
	
	
	private void operation(JdbcTemplate jbdcTemplate,SearchWrapper<E> sw,Operation op) {
		switch(op){
			case create :
				create(jbdcTemplate,sw);
				break;
			case retrieve:
				retrieve(jbdcTemplate,sw);
				break;
			case update:
				update(jbdcTemplate,sw);
				break;
			case delete:
				delete(jbdcTemplate,sw);
				break;
			case count:
				count(jbdcTemplate,sw);
				break;
		}
	}
	
	
	protected abstract void create(JdbcTemplate jbdcTemplate,SearchWrapper<E> sw);
	protected abstract void retrieve(JdbcTemplate jbdcTemplate,SearchWrapper<E> sw);
	protected abstract void count(JdbcTemplate jbdcTemplate,SearchWrapper<E> sw);
	protected abstract void update(JdbcTemplate jbdcTemplate,SearchWrapper<E> sw);
	protected abstract void delete(JdbcTemplate jbdcTemplate,SearchWrapper<E> sw);
	
		
	protected String getOrderClause(SearchWrapper<E> sw){
		StringBuilder orderClause = new StringBuilder(" ");
		
		if(sw.getOrderCol() != null){
			orderClause.append("ORDER BY ") ;
			switch(sw.getOrderCol()){
				case intro:
					orderClause.append("C.");
					orderClause.append(ComputerDAO.ATTR_INTRODUCTION);
					break;
				case disc:
					orderClause.append("C.");
					orderClause.append(ComputerDAO.ATTR_DISCONTINUED);
					break;
				case company:
					orderClause.append("COM.");
					orderClause.append(CompanyDAO.ATTR_NAME);
					break;
				default://case "name"
					orderClause.append("C.");
					orderClause.append(ComputerDAO.ATTR_NAME);
					break;
			}
						
			if(sw.getOrderDirection() !=  null){
				if(sw.getOrderDirection() == OrderDirection.desc)
					orderClause.append(" DESC");
			}
		
		}
		return orderClause.toString();
		
	}
	
	protected String getLimitClause(SearchWrapper<E> sw){
		StringBuilder limitClause = new StringBuilder("");
		if(sw.getPage() != 0){
			int offset = (sw.getPage()-1)*sw.getNbComputersPerPage();
			limitClause.append(" LIMIT " );
			limitClause.append(sw.getNbComputersPerPage());
			limitClause.append(" OFFSET ");
			limitClause.append(offset);
		}
		return limitClause.toString();
	}

	public String getTABLE() {
		return TABLE;
	}

	protected void setTABLE(String tABLE) {
		TABLE = tABLE;
	}
	
	public Logger getLogger(){
		return LOGGER;
	}
	
	protected void setLogger(Logger LOGGER){
		this.LOGGER = LOGGER;
	}

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	
	

}
