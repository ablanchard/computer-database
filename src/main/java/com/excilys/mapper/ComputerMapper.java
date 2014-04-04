package com.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.excilys.controller.AddController;
import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.CompanyService;
import com.excilys.service.NotExistException;
import com.excilys.service.ServiceException;

@Component
public class ComputerMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
	
	@Autowired
	private CompanyService companyService ;
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	public Computer toComputer(ComputerDTO dto) throws ServiceException, NotExistException {
		Computer c = Computer.builder();
		
		c.setId(dto.getId());
		c.setName(dto.getName());
		
		c.setIntroduced(toDate(dto.getIntroducedDate()));
		c.setDiscontinued(toDate(dto.getDiscontinuedDate()));
		
		SearchWrapper<Company> sw = new SearchWrapper<Company>(Company.build().setId(dto.getCompanyId()));
		getCompanyService().retrieve(sw);
		
		Company company = null;
		if(sw.getItems().size() == 1){
			company = sw.getItems().get(0);	
		}
		c.setCompany(company);
		
		return c;		
	}
	
	public ComputerDTO toComputerDTO(Computer c){
		ComputerDTO dto = new ComputerDTO();
		dto.setName(c.getName());
		
		if(c.getIntroduced() == null)
			dto.setIntroducedDate("");
		else
			dto.setIntroducedDate(toString(c.getIntroduced()));
		
		if(c.getDiscontinued() == null)
			dto.setDiscontinuedDate("");
		else
			dto.setDiscontinuedDate(toString(c.getDiscontinued()));
		
		dto.setId(c.getId());
		
		if(c.getCompany() == null){
			dto.setCompanyId(0);
		}
		else{
			dto.setCompanyId(c.getCompany().getId());
			dto.setCompanyName(c.getCompany().getName());
		}
		
		return dto;
	}
	
	

	public  SearchWrapper<ComputerDTO> toComputerDTOWrapper(SearchWrapper<Computer> sw){
		SearchWrapper<ComputerDTO> swDTO = new SearchWrapper<ComputerDTO>();
		swDTO.setQuery(sw.getQuery());
		swDTO.setOrderCol(sw.getOrderCol());
		swDTO.setOrderDirection(sw.getOrderDirection());
		swDTO.setPage(sw.getPage());
		swDTO.setNbComputersPerPage(sw.getNbComputersPerPage());
		swDTO.setCount(sw.getCount());
		swDTO.setPageMax(sw.getPageMax());
		for(Computer c : sw.getItems()){
			swDTO.getItems().add(this.toComputerDTO(c));
		}
		return swDTO;
	}

	public Date toDate(String date) {
		try {
			return new SimpleDateFormat(messageSource.getMessage("date.pattern", null, LocaleContextHolder.getLocale())).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public String toString(Date date){
		return new SimpleDateFormat(messageSource.getMessage("date.pattern", null, LocaleContextHolder.getLocale())).format(date);
	}
	
	public  CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
