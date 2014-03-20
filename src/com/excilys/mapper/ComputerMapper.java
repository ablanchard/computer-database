package com.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOException;
import com.excilys.service.CompanyService;

public class ComputerMapper {
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static Computer toComputer(ComputerDTO dto) throws DTOException {
		Computer c = Computer.builder();
		
		c.setId(dto.getId());
		
		if(dto.getName() == null)
			throw new DTOException("No name setted");
		
		if(dto.getName().equals(""))
			throw new DTOException("Name can't be empty");
		
		c.setName(dto.getName());
								
		
		Date introducedDate = new Date(0);
		Date discontinuedDate = new Date(0);
		if(dto.getIntroducedDate() != null){
			if(!dto.getIntroducedDate().equals("")){
				try {	
					introducedDate = new SimpleDateFormat(DATE_PATTERN).parse(dto.getIntroducedDate());
				} catch (ParseException e) {
					throw new DTOException("Introduced Date format is invalid");
				}
			}
		}
		
		if(dto.getDiscontinuedDate() != null){
			if(!dto.getDiscontinuedDate().equals("")){
				try {
					discontinuedDate = new SimpleDateFormat(DATE_PATTERN).parse(dto.getDiscontinuedDate());
				} catch (ParseException e) {
					throw new DTOException("Discontinued Date format is invalid");
				}
			}
		}
		
		c.setIntroduction(introducedDate);
		c.setDiscontinued(discontinuedDate);
		
		
		SearchWrapper<Company> sw = new SearchWrapper<Company>(Company.build().setId(dto.getCompanyId()));
		CompanyService.getInstance().retrieve(sw);
		
		Company company = null;
		if(sw.getItems().size() == 1){
			company = sw.getItems().get(0);	
			System.out.println(company);
		}
		c.setCompany(company);
		
		return c;		
				
		
	}
	
	public static ComputerDTO toComputerDTO(Computer c){
		ComputerDTO dto = new ComputerDTO();
		dto.setName(c.getName());
		
		dto.setIntroducedDate(new SimpleDateFormat(DATE_PATTERN).format(c.getIntroduction()));
		dto.setDiscontinuedDate(new SimpleDateFormat(DATE_PATTERN).format(c.getDiscontinued()));
		
		dto.setId(c.getId());
		
		if(c.getCompany() == null)
			dto.setCompanyId(0);
		else
			dto.setCompanyId(c.getCompany().getId());
		
		return dto;
		
	}
}
