package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.DaoException;
import com.excilys.dao.DatabaseHandler;
import com.excilys.dao.LogDAO;
import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.data.Log;
import com.excilys.data.Operation;

public class ServiceBean implements Service {

	private static ServiceBean INSTANCE = null;


	
	private ServiceBean(){
		
	}
	
	public static ServiceBean getInstance(){
		if(INSTANCE == null)
			INSTANCE = new ServiceBean();
		
		return INSTANCE;
	}
	
	public List<Computer> retrieveComputers(SearchWrapper sw){
		Log log = Log.build().setTarget(ComputerDAO.TABLE_COMPUTER).setOperation(Operation.retrieve);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		List<Computer> computers = null;
		try{
			computers = ComputerDAO.getInstance(cn,log).retrieve(sw);
			LogDAO.getInstance(cn).create(log);
			cn.commit();
		} catch (DaoException | SQLException e){
			try {
				cn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
		return computers;
	}

	public Computer retrieveComputerById(int id){
		Log log = Log.build().setTarget(ComputerDAO.TABLE_COMPUTER).setOperation(Operation.retrieve);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		Computer c = null;
		try{
			c = ComputerDAO.getInstance(cn,log).retriveById(id);
			LogDAO.getInstance(cn).create(log);
			cn.commit();
		} catch (DaoException | SQLException e){
			try{
				cn.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
		return c;
	}
	
	public void createComputer(Computer c){
		Log log = Log.build().setTarget(ComputerDAO.TABLE_COMPUTER).setOperation(Operation.create);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			ComputerDAO.getInstance(cn,log).create(c);
			LogDAO.getInstance(cn).create(log);
			cn.commit();
		} catch (DaoException | SQLException e){
			try{
				cn.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
	}
	public void updateComputer(Computer c){
		Log log = Log.build().setTarget(ComputerDAO.TABLE_COMPUTER).setOperation(Operation.update);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			ComputerDAO.getInstance(cn,log).create(c);
			LogDAO.getInstance(cn).create(log);
			cn.commit();
		} catch (DaoException | SQLException e){
			try{
				cn.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
	}
	public void deleteComputer(int computerId){
		Log log = Log.build().setTarget(ComputerDAO.TABLE_COMPUTER).setOperation(Operation.delete);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			ComputerDAO.getInstance(cn,log).deleteById(computerId);
			LogDAO.getInstance(cn).create(log);
			cn.commit();
		} catch (DaoException | SQLException e){
			try{
				cn.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
	}
	public int countComputers(SearchWrapper sw){
		Log log = Log.build().setTarget(ComputerDAO.TABLE_COMPUTER).setOperation(Operation.retrieve);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		int result = 0;
		try{
			result = ComputerDAO.getInstance(cn,log).count(sw);
			LogDAO.getInstance(cn).create(log);
			cn.commit();
		} catch (DaoException | SQLException e){
			try{
				cn.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
		return result;
	}
	public List<Company> retrieveCompanies(){
		Log log = Log.build().setTarget(CompanyDAO.TABLE_COMPANY).setOperation(Operation.retrieve);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		List<Company> companies = null;
		try{
			companies = CompanyDAO.getInstance(cn,log).retrieveAll();
			LogDAO.getInstance(cn).create(log);
			cn.commit();
		} catch (DaoException | SQLException e){
			try{
				cn.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
		return companies;
	}

	public Company retriveCompanyById(int id){
		Log log = Log.build().setTarget(CompanyDAO.TABLE_COMPANY).setOperation(Operation.retrieve);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		Company c = null;
		try{
			c =  CompanyDAO.getInstance(cn,log).retrieveById(id);
			LogDAO.getInstance(cn).create(log);
			cn.commit();
		} catch (DaoException | SQLException e){
			try{
				cn.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
		return c;
	}
	
	public void closeConnection(Connection cn){
		if(cn!=null){
		try {
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	}

}
