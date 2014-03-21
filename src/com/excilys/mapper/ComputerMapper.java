package com.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOException;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;

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
								
		
		Date introducedDate = null ;//new Date(0);
		Date discontinuedDate = null ; //new Date(0);
		
		if(dto.getIntroducedDate() != null){
			if(!dto.getIntroducedDate().equals("")){
				try {	
					introducedDate = new SimpleDateFormat(DATE_PATTERN).parse(dto.getIntroducedDate());
					dateVerification(dto.getIntroducedDate());
					
				} catch (ParseException e) {
					throw new DTOException("Introduced Date format is invalid");
				} catch(DTOException e){
					throw new DTOException("Introduced date : "+e.getMessage());
				}
			}
		}
		
		if(dto.getDiscontinuedDate() != null){
			if(!dto.getDiscontinuedDate().equals("")){
				try {
					discontinuedDate = new SimpleDateFormat(DATE_PATTERN).parse(dto.getDiscontinuedDate());
					dateVerification(dto.getDiscontinuedDate());
				} catch (ParseException e) {
					throw new DTOException("Discontinued Date format is invalid");
				} catch(DTOException e){
					throw new DTOException("Discontinued date : "+e.getMessage());
				}
			}
		}
		
		if(discontinuedDate != null && introducedDate != null){
			if(discontinuedDate.before(introducedDate))
				throw new DTOException("Discontinued date is before the introduced date.");
		}
		
		if(introducedDate == null)
			introducedDate = new Date(0);
		
		if(discontinuedDate == null)
			discontinuedDate = new Date(0);
		
		c.setIntroduction(introducedDate);
		c.setDiscontinued(discontinuedDate);
		
		try{
			SearchWrapper<Company> sw = new SearchWrapper<Company>(Company.build().setId(dto.getCompanyId()));
			CompanyService.getInstance().retrieve(sw);
			
			Company company = null;
			if(sw.getItems().size() == 1){
				company = sw.getItems().get(0);	
				System.out.println(company);
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
		
		if(c.getIntroduction() == null)
			dto.setIntroducedDate("");
		else
			dto.setIntroducedDate(new SimpleDateFormat(DATE_PATTERN).format(c.getIntroduction()));
		
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
	
	public static boolean dateVerification(String date) throws DTOException{
		String[] d = date.split("-");
		int year = Integer.valueOf(d[0]);
		int month = Integer.valueOf(d[1]);
		int day = Integer.valueOf(d[2]);
		
		if(month < 1 || month > 12)
			throw new DTOException("This month doesn't exist.");
		
		if(day < 1 || day > 31)
			throw new DTOException("This day doesn't exist.");
		
		if((month == 4 || month == 6 || month == 9 || month == 11) && day == 31)
			throw new DTOException("This day doesn't exist for this month.");
		
		if(month == 2){
			boolean isLeap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
			if(day > 29 || (day == 29 && !isLeap))
				throw new DTOException("Not so much days in Febuary.");
		}
		return true;
	}
	
}
