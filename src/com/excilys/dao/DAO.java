package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
				getCreateResult(sw, generatedKeys);
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
	
	public abstract void retrieve(SearchWrapper<E> sw) throws DaoException ;
	public abstract void update(SearchWrapper<E> sw) throws DaoException ;
	public abstract void delete(SearchWrapper<E> sw) throws DaoException ;
	
	protected abstract E entry(ResultSet rs)  throws DaoException;
	
	public abstract String getCreateQuery();
	public abstract void prepareCreateStatement(PreparedStatement ps,SearchWrapper<E> sw);
	public abstract void getCreateResult(SearchWrapper<E> sw, ResultSet gk);
	
	public void executeQuery(){
		
	}
	
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
