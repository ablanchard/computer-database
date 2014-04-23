package com.excilys.service;

import java.util.List;

import com.excilys.data.Log;
import com.excilys.repository.CompanyRepository;
import com.excilys.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.data.Company;

import javax.transaction.Transactional;

@Component
public class CompanyService {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyRepository companyRepository = null;

    @Autowired
    private LogRepository logRepository = null;

    public CompanyRepository getCompanyRepository() {
        return companyRepository;
    }

    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public LogRepository getLogRepository() {
            return logRepository;
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Transactional
    public List<Company> getAll() throws ServiceException {
		List<Company> companies= companyRepository.findAll();
        logRepository.save(Log.build().setTarget("Company").setOperation(Log.Operation.retrieve).setCommand("All"));
        //Ajout de la company d'id 0
		companies.add(0, Company.build().setName("--").setId(0L));
		return companies ;
	}

    @Transactional
	public Company retrieveById(Long id) throws ServiceException, NotExistException{
		Company company = companyRepository.findOne(id);
        logRepository.save(Log.build().setTarget("Company").setOperation(Log.Operation.retrieve).setCommand(company.toString()));
        if(company == null){
			LOGGER.debug("NotExistException !");
			throw new NotExistException();
		}
		else {
			return company;
		}
	}
	
	
	
	

}
