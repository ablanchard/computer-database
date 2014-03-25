package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;

import com.excilys.data.Operation;

public abstract class DAO<E> {
	
	public String TABLE ;
	
	Logger LOGGER ;
	
	public static DAO<?> getInstance() {
		return null;
	}
	
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
	
	private void operation(SearchWrapper<E> sw,Operation op) throws DaoException {
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps =null;
		ResultSet rs = null;
		
		try{
			
			ps = cn.prepareStatement(getQuery(sw,op),PreparedStatement.RETURN_GENERATED_KEYS);
			prepareStatement(ps, sw ,op);
			
			rs = execute(ps,op);
			getResult(rs,sw,op);
			getLogger().info("{} succeed",op);
			if(op == Operation.retrieve){
				operation(sw,Operation.count);
			}
			
		} 
		catch (SQLException e){
			getLogger().error("Exception when {} : {}",op,e.getMessage());
			
			getLogger().error(e.getMessage(), e.getCause());
			throw new DaoException() ;
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	
	private ResultSet execute(PreparedStatement ps,Operation op) throws SQLException {
		if(op ==  Operation.retrieve || op == Operation.count){
			return ps.executeQuery();
		} else{
			if( ps.executeUpdate() == 0){
				throw new SQLException("Failed to update");
			}
		    return  ps.getGeneratedKeys();
		}
	}
	
	private String getQuery(SearchWrapper<E> sw,Operation op){
		switch(op){
			case create :
				return getCreateQuery();
			case retrieve:
				return getRetrieveQuery(sw);
			case update:
				return getUpdateQuery();
			case delete:
				return getDeleteQuery();
			case count:
				return getCountQuery(sw);
			default:
				return "";
		}
	}
	
	private void prepareStatement(PreparedStatement ps,SearchWrapper<E> sw,Operation op) throws SQLException {
		switch(op){
			case create :
				prepareCreateStatement(ps, sw);
				break;
			case retrieve:
				prepareRetrieveStatement(ps, sw);
				break;
			case update:
				prepareUpdateStatement(ps, sw);
				break;
			case delete:
				prepareDeleteStatement(ps, sw);
				break;
			case count:
				prepareCountStatement(ps, sw);
				break;
		}
	}
	
	private void getResult(ResultSet rs,SearchWrapper<E> sw,Operation op)throws SQLException {
		switch(op){
			case create :
				getCreateResult(rs,sw);
				break;
			case retrieve:
				getRetrieveResult(rs,sw);
				break;
			case update:
				getUpdateResult(rs,sw);
				break;
			case delete:
				getDeleteResult(rs,sw);
				break;
			case count:
				getCountResult(rs,sw);
				break;
		}
	}
	
	protected abstract E entry(ResultSet rs)  throws SQLException;
	
	protected abstract String getCreateQuery();
	protected abstract void prepareCreateStatement(PreparedStatement ps,SearchWrapper<E> sw) throws SQLException;
	protected abstract void getCreateResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	
	protected abstract String getRetrieveQuery(SearchWrapper<E> sw);
	protected abstract void prepareRetrieveStatement(PreparedStatement ps, SearchWrapper<E> sw) throws SQLException;
	protected abstract void getRetrieveResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	
	protected abstract String getCountQuery(SearchWrapper<E> sw);
	protected abstract void prepareCountStatement(PreparedStatement ps, SearchWrapper<E> sw) throws SQLException;
	protected abstract void getCountResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	

	protected abstract String getUpdateQuery();
	protected abstract void prepareUpdateStatement(PreparedStatement ps, SearchWrapper<E> sw) throws SQLException;
	protected abstract void getUpdateResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	

	protected abstract String getDeleteQuery();
	protected abstract void prepareDeleteStatement(PreparedStatement ps, SearchWrapper<E> sw) throws SQLException;
	protected abstract void getDeleteResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	
		
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

	protected void closeObjects(Connection cn,PreparedStatement ps, ResultSet rs){
		try {
			if(cn!=null && cn.getAutoCommit()){
				cn.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					getLogger().error(e.getMessage(), e.getCause());
			}}
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					getLogger().error(e.getMessage(), e.getCause());
			}}
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

	
	

}
