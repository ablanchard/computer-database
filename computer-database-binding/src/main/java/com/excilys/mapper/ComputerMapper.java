package com.excilys.mapper;

import com.excilys.service.PageWrapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.CompanyService;
import com.excilys.service.NotExistException;
import com.excilys.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComputerMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
	
	@Autowired
	private CompanyService companyService ;
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	public Computer toComputer(ComputerDTO dto) {
		Computer c = Computer.builder();
		
		c.setId(new Long(dto.getId()));
		c.setName(dto.getName());
		c.setIntroduced(toDate(dto.getIntroducedDate()));
		c.setDiscontinued(toDate(dto.getDiscontinuedDate()));
		Company company = null;
		if(dto.getCompanyId() != 0){
			try {
				company = getCompanyService().retrieveById(new Long(dto.getCompanyId()));
			} catch (ServiceException | NotExistException e) {
			}
		}
		c.setCompany(company);
		
		return c;		
	}
	
	public ComputerDTO toComputerDTO(Computer c){
		ComputerDTO dto = new ComputerDTO();
		dto.setName(c.getName());
		
		if(c.getIntroduced() == null){
			dto.setIntroducedDate("");
		}
		else{
			dto.setIntroducedDate(toString(c.getIntroduced()));
		}
		
		if(c.getDiscontinued() == null){
			dto.setDiscontinuedDate("");
		}
		else {
			dto.setDiscontinuedDate(toString(c.getDiscontinued()));
		}
		dto.setId(c.getId().intValue());
		
		if(c.getCompany() == null){
			dto.setCompanyId(0);
		}
		else{
			dto.setCompanyId(c.getCompany().getId().intValue());
			dto.setCompanyName(c.getCompany().getName());
		}
		
		return dto;
	}

    public List<Computer> toComputer(List<ComputerDTO> dtos){
        List<Computer> computers = new ArrayList<Computer>();
        for(ComputerDTO dto : dtos){
            computers.add(toComputer(dto));
        }
        return computers;
    }
    public List<ComputerDTO> toComputerDTO(List<Computer> computers){
        List<ComputerDTO> dtos = new ArrayList<ComputerDTO>();
        for(Computer computer : computers){
            dtos.add(toComputerDTO(computer));
        }
        return dtos;
    }



    public PageWrapper<ComputerDTO> toComputerDTO(PageWrapper<Computer> page){
        Page<Computer> pageComputer = page.getPage();
		return new PageWrapper<>(new PageImpl<ComputerDTO>(
                toComputerDTO(pageComputer.getContent()),
                new PageRequest(pageComputer.getNumber(),
                                pageComputer.getSize(),
                                pageComputer.getSort()),
                pageComputer.getTotalElements()),page.getSearch());
	}
	
	public  PageWrapper<Computer> toComputer(PageWrapper<ComputerDTO> pageDTO){
        Page<ComputerDTO> page = pageDTO.getPage();
        return new PageWrapper<>(new PageImpl<Computer>(
                toComputer(page.getContent()),
                new PageRequest(page.getNumber(),
                        page.getSize(),
                        page.getSort()),
                page.getTotalElements()),pageDTO.getSearch());
	}

	public DateTime toDate(String date) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(messageSource.getMessage("date.pattern", null, LocaleContextHolder.getLocale()));
		try {
			return fmt.parseDateTime(date);
		} catch(IllegalArgumentException e){
			return null;
		}
	}
	
	public String toString(DateTime date){
		DateTimeFormatter fmt = DateTimeFormat.forPattern(messageSource.getMessage("date.pattern", null, LocaleContextHolder.getLocale()));
		return fmt.print(date);	
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
