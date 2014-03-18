package com.excilys.service;

import java.util.List;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;

public interface Service {
	
	public List<Computer> retrieveComputers(SearchWrapper sw);
	public Computer retrieveComputerById(int id);
	public void createComputer(Computer c);
	public void updateComputer(Computer c);
	public void deleteComputer(int computerId);
	public int countComputers(SearchWrapper sw);
	public List<Company> retrieveCompanies();
	public Company retriveCompanyById(int id);
	

}
