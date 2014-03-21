package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.data.Log;

public abstract class DAO<E> {
	
	public String TABLE ;
	
	final Logger logger = LoggerFactory.getLogger(DAO.class);
	
	public static DAO getInstance() {
		return null;
	}
	
	public void create(SearchWrapper<E> sw) throws DaoException {
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps =null;
		int rs ;
		ResultSet generatedKeys;
		try{
			ps = cn.prepareStatement(getCreateQuery(),PreparedStatement.RETURN_GENERATED_KEYS);
			prepareCreateStatement(ps, sw);
			
			rs = ps.executeUpdate();
		    generatedKeys = ps.getGeneratedKeys();
			
			if(rs != 0){
				logger.info("Insertion succeed");
				getCreateResult(generatedKeys,sw);
			}
			else
				throw new DaoException();
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
			throw new DaoException() ;
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	
	public void retrieve(SearchWrapper<E> sw) throws DaoException {
				
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		
		try {				
			ps = cn.prepareStatement(getRetrieveQuery(sw));
			
			prepareRetrieveStatement(ps, sw);
			rs = ps.executeQuery();

			getRetrieveResult(rs,sw);
			
			count(sw);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,rs);
					
		}
	}
	
	public void count(SearchWrapper<E> sw)  throws DaoException  {
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs  = null;
		
		try {				
			ps = cn.prepareStatement(getCountQuery(sw));
			
			prepareCountStatement(ps, sw);
			
			rs = ps.executeQuery();

			getCountResult(rs,sw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,rs);
					
		}
	}
	
	public void update(SearchWrapper<E> sw) throws DaoException {
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps =null;
		int rs ;
		try{
			ps = cn.prepareStatement(getUpdateQuery());
			
			prepareUpdateStatement(ps,sw);
			
			rs = ps.executeUpdate();

			
			
			if(rs != 0)
				logger.info("Successful update");
			else
				throw new SQLException();
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	public void delete(SearchWrapper<E> sw) throws DaoException {
		Connection cn = DatabaseHandler.getInstance().getConnection();
		PreparedStatement ps =null;
		int rs ;
		try{
			ps = cn.prepareStatement(getDeleteQuery());
			prepareDeleteStatement(ps, sw);
			
			rs = ps.executeUpdate();

			logger.info(ps.toString());
			
			if(rs != 0)
				logger.info("Deletion succeed");
			else
				throw new DaoException();
		
		} 
		catch (SQLException e){
			logger.error("Exception lors de l'insertion : " + e.getMessage());
			e.printStackTrace();
			throw new DaoException();
		} finally{
			closeObjects(cn,ps,null);
		}
	}
	
	protected abstract E entry(ResultSet rs)  throws SQLException;
	
	public abstract String getCreateQuery();
	public abstract void prepareCreateStatement(PreparedStatement ps,SearchWrapper<E> sw) throws SQLException;
	public abstract void getCreateResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	
	public abstract String getRetrieveQuery(SearchWrapper<E> sw);
	public abstract void prepareRetrieveStatement(PreparedStatement ps, SearchWrapper<E> sw) throws SQLException;
	public abstract void getRetrieveResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	
	public abstract String getCountQuery(SearchWrapper<E> sw);
	public abstract void prepareCountStatement(PreparedStatement ps, SearchWrapper<E> sw) throws SQLException;
	public abstract void getCountResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	

	public abstract String getUpdateQuery();
	public abstract void prepareUpdateStatement(PreparedStatement ps, SearchWrapper<E> sw) throws SQLException;
	public abstract void getUpdateResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	

	public abstract String getDeleteQuery();
	public abstract void prepareDeleteStatement(PreparedStatement ps, SearchWrapper<E> sw) throws SQLException;
	public abstract void getDeleteResult(ResultSet rs,SearchWrapper<E> sw)throws SQLException;
	
		
	protected String getOrderClause(SearchWrapper<E> sw){
		String orderClause ;
		
		if(sw.getOrderCol() == null)
			orderClause = " ";
		else {
			orderClause = " ORDER BY " ;
			switch(sw.getOrderCol()){
				case intro:
					orderClause += "C."+ComputerDAO.ATTR_INTRODUCTION;
					break;
				case disc:
					orderClause += "C."+ComputerDAO.ATTR_DISCONTINUED;
					break;
				case company:
					orderClause += "COM."+CompanyDAO.ATTR_NAME;
					break;
				default://case "name"
					orderClause += "C."+ComputerDAO.ATTR_NAME;
					break;
			}
						
			if(sw.getOrderDirection() !=  null){
				if(sw.getOrderDirection() == OrderDirection.desc)
					orderClause += " DESC";
			}
		
		}
		return orderClause;
		
	}
	
	protected String getLimitClause(SearchWrapper<E> sw){
		String limitClause = "" ;
		if(sw.getPage() != 0){
			int offset = (sw.getPage()-1)*sw.getNbComputersPerPage();
			limitClause += " LIMIT " + sw.getNbComputersPerPage() + " OFFSET " + offset ;
		}
		return limitClause;
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

	public String getTABLE() {
		return TABLE;
	}

	public void setTABLE(String tABLE) {
		TABLE = tABLE;
	}
	

	
	

}
