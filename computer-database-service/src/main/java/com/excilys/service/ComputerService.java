package com.excilys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dao.ComputerDAO;
import com.excilys.data.Computer;
import com.excilys.util.SearchWrapper;

@Component
public class ComputerService extends Service<Computer>{


	public static final String NOT_EXIST = "computer.not.exist";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
	
	@Autowired
	private ComputerDAO dao = null;
	
	public ComputerService(){
		setLogger(LOGGER);
	}

	public ComputerDAO getDao() {
		return dao;
	}

	public void setDao(ComputerDAO dao) {
		this.dao = dao;
	}

	public Computer getById(int id) throws ServiceException, NotExistException{
		SearchWrapper<Computer> computerWrap = new SearchWrapper<Computer>(new Computer(id));
		retrieve(computerWrap);
		if(computerWrap.getItem() == null){
			LOGGER.debug("NotExistException !");
			throw new NotExistException();
		}
		else {
			return computerWrap.getItem();
		}
	}
	
	public void delete(Integer id) throws ServiceException, NotExistException{
		if(id == null){
			throw new NotExistException();
		}
		SearchWrapper<Computer> computerToDelete = new SearchWrapper<Computer>(new Computer(id));
		getById(id);
		delete(computerToDelete);
	}
	
}
