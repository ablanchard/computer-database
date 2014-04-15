package com.excilys.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.data.Company;

@Component
public class CompanyDAO extends DAO<Company> {
	public static final String TABLE = "company";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_ID = "id";

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	
	public CompanyDAO(){
		setTABLE(TABLE);
		setLogger(LOGGER);
	}
	
	
	
	//Insertion
	
	//Suppresion
	
	//Modification
	
	//Selection

	
	public String getRetrieveQuery(SearchWrapper<Company> sw) {
		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append(TABLE) ;
		if(sw.getItems().size() == 1) {// get by id
			query.append(" WHERE ");
			query.append(ATTR_ID);
			query.append("=? ;");
		}
		return query.toString() ;
	}

	
	@Override
	public void retrieve(JdbcTemplate jdbcTemplate, SearchWrapper<Company> sw)
			 {
		
		if(sw.getItems().size() == 1){//by id
			sw.setItems(jdbcTemplate.query(getRetrieveQuery(sw), new CompanyRowMapper(), new Object[]{
				sw.getItems().get(0).getId()
			}));
		}
		else{
			sw.setItems(jdbcTemplate.query(getRetrieveQuery(sw), new CompanyRowMapper()));
		}
		
	}



	@Override
	protected void create(JdbcTemplate jbdcTemplate, SearchWrapper<Company> sw)
			 {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected void count(JdbcTemplate jbdcTemplate, SearchWrapper<Company> sw)
			 {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected void update(JdbcTemplate jbdcTemplate, SearchWrapper<Company> sw)
			 {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected void delete(JdbcTemplate jbdcTemplate, SearchWrapper<Company> sw)
			 {
		// TODO Auto-generated method stub
		
	}

	


	
}
