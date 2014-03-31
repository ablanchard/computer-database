package com.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOException;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;
import com.excilys.servlet.ComputerForm;
import com.excilys.validator.ComputerValidator;

@Component
public class ComputerMapper {
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	@Autowired
	private static CompanyService companyService = null;

	public static Computer toComputer(ComputerDTO dto) throws DTOException {
		Computer c = Computer.builder();
		
		c.setId(dto.getId());
		ComputerValidator.name(dto.getName());
		c.setName(dto.getName());
								
		
		Date introducedDate = ComputerValidator.date(dto.getIntroducedDate(), ComputerForm.TITLE_INTRO) ;//new Date(0);
		Date discontinuedDate = ComputerValidator.date(dto.getDiscontinuedDate(), ComputerForm.TITLE_DISC);
				
		c.setIntroduced(introducedDate);
		c.setDiscontinued(discontinuedDate);
		
		try{
			SearchWrapper<Company> sw = new SearchWrapper<Company>(Company.build().setId(dto.getCompanyId()));
			companyService.retrieve(sw);
			
			Company company = null;
			if(sw.getItems().size() == 1){
				company = sw.getItems().get(0);	
			}
			c.setCompany(company);
		} catch (ServiceException e){
			throw new DTOException(Service.SERVICE_ERROR);
		}
		
		return c;		
				
		
	}
	
	public static ComputerDTO toComputerDTO(Computer c){
		ComputerDTO dto = new ComputerDTO();
		dto.setName(c.getName());
		
		if(c.getIntroduced() == null)
			dto.setIntroducedDate("");
		else
			dto.setIntroducedDate(new SimpleDateFormat(DATE_PATTERN).format(c.getIntroduced()));
		
		if(c.getDiscontinued() == null)
			dto.setDiscontinuedDate("");
		else
			dto.setDiscontinuedDate(new SimpleDateFormat(DATE_PATTERN).format(c.getDiscontinued()));
		
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

	public static SearchWrapper<ComputerDTO> toComputerDTOWrapper(SearchWrapper<Computer> sw){
		SearchWrapper<ComputerDTO> swDTO = new SearchWrapper<ComputerDTO>();
			
		swDTO.setQuery(sw.getQuery());
		swDTO.setOrderCol(sw.getOrderCol());
		swDTO.setOrderDirection(sw.getOrderDirection());
		swDTO.setPage(sw.getPage());
		swDTO.setNbComputersPerPage(sw.getNbComputersPerPage());
		swDTO.setCount(sw.getCount());
		swDTO.setPageMax(sw.getPageMax());
		for(Computer c : sw.getItems()){
			swDTO.getItems().add(ComputerMapper.toComputerDTO(c));
		}
		return swDTO;
	}

	public static CompanyService getCompanyService() {
		return companyService;
	}

	@Autowired
	public static void setCompanyService(CompanyService companyService) {
		ComputerMapper.companyService = companyService;
	}


	
	
	
}
