package com.excilys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.CompanyDAO;
import com.excilys.data.Company;

public class CompanyService extends Service<Company> {
	
private static CompanyService INSTANCE = null;
	
	final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	private CompanyService(){
		setDao(CompanyDAO.getInstance());
	}
	
	public static CompanyService getInstance(){
		if(INSTANCE == null){
			INSTANCE = new CompanyService();
		}
		return INSTANCE;
	}

}
