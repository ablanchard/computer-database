package com.excilys.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.excilys.dao.OrderComputerCol;
import com.excilys.dao.OrderDirection;
import com.excilys.dao.SearchWrapper;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.ServiceException;
import com.excilys.servlet.Header;

@Component
@Controller
@SessionAttributes("sw")
public class DashboardController extends ComputerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);


	private int nbComputersPerPage= 15;
	
	public static final String TABLE_HEADER_NAME = "Computer name";
	public static final String TABLE_HEADER_INTRO = "Introduced Date";
	public static final String TABLE_HEADER_DISC = "Discontinued Date";
	public static final String TABLE_HEADER_COMPANY = "Company";
	public static final String TABLE_HEADER_ACTIONS  = "Actions";

	public static final String ATTR_WRAPPER = "sw";
	public static final String ATTR_TABLE_HEADERS = "tableHeaders";
	

	@RequestMapping(value = "index", 
			//params = {"page","search","order","direction"},
			method = RequestMethod.GET)
    public String show(Model model,
    		@RequestParam(value="page", required=false, defaultValue="1") Integer page, 
    		@RequestParam(value="search", required=false, defaultValue="") String search, 
    		@RequestParam(value="order", required=false, defaultValue="") String order, 
    		@RequestParam(value="direction", required=false, defaultValue="") String direction) {
		
		SearchWrapper<Computer> sw = new SearchWrapper<Computer>(nbComputersPerPage);
		//LOGGER.debug("Request parameters : Search : {}, Order : {}, Direction : {}",search,order,direction);
		
		if(search != null){
			if(!search.equals("")){
				sw.setQuery(search);
			}
		}
		
		if(order != null){
			try {
				sw.setOrderCol(OrderComputerCol.valueOf(order));
			} catch(IllegalArgumentException e){
				
			}
		}
		
		if(direction !=null){
			try {
				sw.setOrderDirection(OrderDirection.valueOf(direction));
			}catch(IllegalArgumentException e){
				
			}
		}
		
		if(page != null){
			if(page <= 0){
				page = 1;
			}
			sw.setPage(page);
		}
		
		model.addAttribute(sw);
		LOGGER.info(sw.toString());
		try {
			
			getComputerService().retrieve(sw);
					
			int pageMax = (int)Math.floor(sw.getCount()/nbComputersPerPage);
			
			if (sw.getCount()%nbComputersPerPage != 0)
				pageMax++;
			
			sw.setPageMax(pageMax);
			
			
			model.addAttribute(ATTR_WRAPPER,getComputerMapper().toComputerDTOWrapper(sw));
		} catch (ServiceException e){
			//request.setAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		//Headers
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header(TABLE_HEADER_NAME,OrderComputerCol.name.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_INTRO).setOrderName(OrderComputerCol.intro.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_DISC).setOrderName(OrderComputerCol.disc.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_COMPANY).setOrderName(OrderComputerCol.company.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_ACTIONS));
		
		model.addAttribute(ATTR_TABLE_HEADERS, headers);
		
		
		return "dashboard";
    }
/*
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String show(Model model,
			@MatrixVariable(required=false, defaultValue="") String search, 
			@MatrixVariable(required=false, defaultValue="") String order, 
			@MatrixVariable(required=false, defaultValue="") String direction) {
		return show(model, 1, search, order, direction);
	}*/
	
	
	
	@RequestMapping(value="return")
	public String show(Model model,
			@ModelAttribute("sw") SearchWrapper<ComputerDTO> sw,
			@RequestParam(value="success",required=false) String success,
			@RequestParam(value="error",required=false) String error){
		
		
		UriComponents uriComponents =
		        UriComponentsBuilder.newInstance()
		            .path("index")
		            .queryParam("page", sw.getPage())
		            .queryParam("search",sw.getQuery())
		            .queryParam("order",sw.getOrderCol())
		            .queryParam("direction",sw.getOrderDirection())
		            .build();
		
		if(success != null){
			model.addAttribute("success", success);
		}
		
		if(error != null){
			model.addAttribute("error", error);
		}
		
		return "redirect:"+uriComponents;
	}
	
	
	
	
}
