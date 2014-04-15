package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.excilys.data.Computer;

@Component
public class ComputerDAO extends DAO<Computer> {
	
	public static final String TABLE = "computer";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_INTRODUCTION = "introduced";
	public static final String ATTR_DISCONTINUED = "discontinued";
	private static final String ATTR_ID = "id";
	private static final String ATTR_COMPANY_ID = "company_id";
	private static final String TABLE_JOINTURE = TABLE+" C LEFT OUTER JOIN "+ CompanyDAO.TABLE+" COM ON C."+ATTR_COMPANY_ID+" = COM."+CompanyDAO.ATTR_ID;
	private static final String SELECT_QUERY = "SELECT * FROM "+TABLE_JOINTURE;
	private static final String COUNT_QUERY = "SELECT COUNT(*) FROM " + TABLE_JOINTURE;
	private static final String SEARCH_CLAUSE = " WHERE C." + ATTR_NAME + " LIKE ? OR COM." + CompanyDAO.ATTR_NAME + " LIKE ? ";
	//private static final String LIMIT_CLAUSE = " LIMIT ? OFFSET ? ";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	public ComputerDAO(){
		setTABLE(TABLE);
		setLogger(LOGGER);
	}	
		
	public String getCreateQuery() {
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append( TABLE );
		query.append( " (");
		query.append(ATTR_NAME);
		query.append(" , ");
		query.append(ATTR_INTRODUCTION);
		query.append(" , ");
		query.append(ATTR_DISCONTINUED);
		query.append(" , ");
		query.append(ATTR_COMPANY_ID);
		query.append(" ) VALUES ( ? , ? , ? , ? )");
		return query.toString();

	}

	@Override
	public void create(JdbcTemplate jdbcTemplate,SearchWrapper<Computer> sw)  {
		final Computer c = sw.getItems().get(0);
		Integer companyId = null;
		if(c.getCompany() != null){
			companyId = c.getCompany().getId();
		}
		//Timestamp introduced = (c.getIntroduced() == null)? null : new Timestamp(c.getIntroduced().toDate().getTime());
		//Timestamp discontinued = (c.getDiscontinued() == null)? null : new Timestamp(c.getDiscontinued().toDate().getTime());
		     	
       	PreparedStatementCreator psc = new PreparedStatementCreator() {
    		public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    			PreparedStatement ps = con.prepareStatement(getCreateQuery(),PreparedStatement.RETURN_GENERATED_KEYS);
    			ps.setString(1, c.getName());
    			if(c.getIntroduced() == null){
    				ps.setNull(2,0);
				}
				else{
					ps.setTimestamp(2, new Timestamp(c.getIntroduced().toDate().getTime()));
				}

				if(c.getDiscontinued() == null){
					ps.setNull(3,0);
				}
				else{
					ps.setTimestamp(3, new Timestamp(c.getDiscontinued().toDate().getTime()));
				}

				if(c.getCompany()==null){
					ps.setNull(4, 0);
				}
				else{
					ps.setInt(4, c.getCompany().getId());
				}
    			return ps;
    		}
    	};

    	KeyHolder keyHolder = new GeneratedKeyHolder();

    	jdbcTemplate.update(psc, keyHolder);
    	
    	sw.getItems().get(0).setId(keyHolder.getKey().intValue());
	}

	public String getRetrieveQuery(SearchWrapper<Computer> sw) {
		StringBuilder query = new StringBuilder(SELECT_QUERY);
		
		if(sw.getItems().size() == 1){
			query.append(" WHERE C."+ ATTR_ID +"=? ;");
		}
		else{
		
			if(sw.getQuery() != null)
				query.append(SEARCH_CLAUSE );
			
			query.append(getOrderClause(sw));
			query.append(getLimitClause(sw));
		}
		return query.toString();
	}

	@Override
	public void retrieve(JdbcTemplate jdbcTemplate, SearchWrapper<Computer> sw) {
				
		if(sw.getItems().size() == 1){//Retrieve by id
			sw.setItems(jdbcTemplate.query(getRetrieveQuery(sw), new ComputerRowMapper(), new Object[]{
				sw.getItems().get(0).getId()
			}));
		}
		else if(sw.getQuery() != null){//Retrieve by search
			sw.setItems(jdbcTemplate.query(getRetrieveQuery(sw), new ComputerRowMapper(), new Object[]{
				"%"+sw.getQuery()+"%","%"+sw.getQuery()+"%"
			}));
		}
		else{//Retrieve All
			sw.setItems(jdbcTemplate.query(getRetrieveQuery(sw), new ComputerRowMapper()));
		}
	}

	
	
	
	
	public String getCountQuery(SearchWrapper<Computer> sw) {
		StringBuilder query = new StringBuilder(COUNT_QUERY);
		
		if(sw.getQuery() != null)
			query.append(SEARCH_CLAUSE);
		return query.toString();
	}


	@Override
	public void count(JdbcTemplate jdbcTemplate, SearchWrapper<Computer> sw)
			 {
		
		sw.setCount(jdbcTemplate.queryForInt(getCountQuery(sw)));
		
	}

	
	public String getUpdateQuery() {
		StringBuilder query = new StringBuilder("UPDATE ");
		query.append( TABLE);
		query.append( " SET ");
		query.append(ATTR_NAME);
		query.append(" = ? , ");
		query.append( ATTR_INTRODUCTION);
		query.append(" = ? , ");
		query.append(ATTR_DISCONTINUED);
		query.append(" = ? , ");
		query.append(ATTR_COMPANY_ID);
		query.append(" = ? WHERE ");
		query.append( ATTR_ID );
		query.append(" = ? ");
		return query.toString();
	}


	@Override
	public void update(JdbcTemplate jdbcTemplate, SearchWrapper<Computer> sw)
			 {
		Computer c = sw.getItems().get(0);
		Integer companyId = null;
		if(c.getCompany() != null){
			companyId = c.getCompany().getId();
		}
		Timestamp introduced = (c.getIntroduced() == null)? null : new Timestamp(c.getIntroduced().toDate().getTime());
		Timestamp discontinued = (c.getDiscontinued() == null)? null : new Timestamp(c.getDiscontinued().toDate().getTime());
       	jdbcTemplate.update(getUpdateQuery(), 
        	new Object[] { 
        		c.getName(),
	            introduced,
	            discontinued,
	            companyId,
	            c.getId()
        });
	}

	
	public String getDeleteQuery() {
		StringBuilder query = new StringBuilder("DELETE FROM ");
		query.append(TABLE );
		query.append( " WHERE ");
		query.append(ATTR_ID);
		query.append("= ?");
		return query.toString();
	}

	@Override
	public void delete(JdbcTemplate jdbcTemplate, SearchWrapper<Computer> sw)
			 {
		Computer c = sw.getItems().get(0);

       jdbcTemplate.update(getDeleteQuery(), 
        	new Object[] {
	            c.getId()
        });
	}


}
