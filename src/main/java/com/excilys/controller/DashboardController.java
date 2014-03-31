package com.excilys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.dao.OrderComputerCol;
import com.excilys.dao.OrderDirection;
import com.excilys.dao.SearchWrapper;
import com.excilys.data.Computer;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;
import com.excilys.servlet.Header;

@Component
@Controller
public class DashboardController extends ComputerController {


	private int nbComputersPerPage= 15;
	
	public static final String TABLE_HEADER_NAME = "Computer name";
	public static final String TABLE_HEADER_INTRO = "Introduced Date";
	public static final String TABLE_HEADER_DISC = "Discontinued Date";
	public static final String TABLE_HEADER_COMPANY = "Company";
	public static final String TABLE_HEADER_ACTIONS  = "Actions";

	public static final String ATTR_WRAPPER = "sw";
	public static final String ATTR_TABLE_HEADERS = "tableHeaders";
	

	@RequestMapping(value = "/index/{page}", method = RequestMethod.GET)
    public String helloWorld(Model model,
    		@PathVariable int page, 
    		@MatrixVariable(required=true, defaultValue="") String search, 
    		@MatrixVariable(required=true, defaultValue="") String order, 
    		@MatrixVariable(required=true, defaultValue="") String direction) {
		
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
			sw.setPage(page);
		
		try {
			
		
			getComputerService().retrieve(sw);
					
			int pageMax = (int)Math.floor(sw.getCount()/nbComputersPerPage);
			
			if (sw.getCount()%nbComputersPerPage != 0)
				pageMax++;
			
			sw.setPageMax(pageMax);
			
			
			model.addAttribute(ATTR_WRAPPER,ComputerMapper.toComputerDTOWrapper(sw));
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
		
		
       // model.addAttribute("message", "Hello World!");
        return "dashboard";
    }
}
