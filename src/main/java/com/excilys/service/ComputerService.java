package com.excilys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.dao.ComputerDAO;
import com.excilys.data.Computer;

public class ComputerService extends Service<Computer>{


	public static final String NOT_EXIST = "This computer doesn't exist.";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
	
	private ComputerDAO dao = null;
	
	private ComputerService(){
		setLogger(LOGGER);
	}

	public ComputerDAO getDao() {
		return dao;
	}

	public void setDao(ComputerDAO dao) {
		this.dao = dao;
	}

	
}
