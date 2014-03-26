package com.excilys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.ComputerDAO;
import com.excilys.data.Computer;

public class ComputerService extends Service<Computer>{

	private static ComputerService INSTANCE = null;

	public static final String NOT_EXIST = "This computer doesn't exist.";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
	
	private ComputerService(){
		setDao(ComputerDAO.getInstance());
		setLogger(LOGGER);
	}
	
	public static ComputerService getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ComputerService();
		}
		return INSTANCE;
	}
	
}
