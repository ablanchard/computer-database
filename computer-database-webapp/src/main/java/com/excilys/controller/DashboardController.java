package com.excilys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.NotExistException;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;
import com.excilys.data.Header;

@Component
@Controller
@SessionAttributes("sw")
public class DashboardController extends ComputerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	private int nbComputersPerPage= 15;

	public static final String ATTR_WRAPPER = "sw";
	public static final String ATTR_TABLE_HEADERS = "tableHeaders";
	
	@RequestMapping(value ="/", 
			//params = {"page","search","order","direction"},
			method = RequestMethod.GET)
    public String request(Model model,
    		@RequestParam(value="page", required=false, defaultValue="1") Integer page, 
    		@RequestParam(value="search", required=false, defaultValue="") String search, 
    		@RequestParam(value="order", required=false, defaultValue="") String order, 
    		@RequestParam(value="direction", required=false, defaultValue="") String direction,
    		@ModelAttribute(value=ATTR_WRAPPER) SearchWrapper<ComputerDTO> sw) {
		sw.update(page,search,order,direction);
		model.addAttribute(ATTR_WRAPPER,sw);
		return "redirect:index";
    }
	
	@RequestMapping(value ="reset",
			method = RequestMethod.GET)
	public String reset(Model model){
		model.addAttribute(ATTR_WRAPPER, new SearchWrapper<ComputerDTO>(nbComputersPerPage));
		return "redirect:index";
	}
	
	@RequestMapping(value="index",
			method = RequestMethod.GET)
	public String show(Model model,
			@ModelAttribute(value=ATTR_WRAPPER) SearchWrapper<ComputerDTO> sw){
		try {
			SearchWrapper<Computer> swComputer = new SearchWrapper<Computer>(sw);
			getComputerService().retrieve(swComputer);
			swComputer.updatePageMax();
			model.addAttribute(ATTR_WRAPPER,getComputerMapper().toComputerDTOWrapper(swComputer));
		} catch (ServiceException | NotExistException e){
			model.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		//Table headers
		model.addAttribute(ATTR_TABLE_HEADERS, Header.getArray());
		
		return "dashboard";
	}
	
	@RequestMapping(value="return")
	public String show(Model model,
			@ModelAttribute(ATTR_DTO) SearchWrapper<ComputerDTO> sw,
			@RequestParam(value=ATTR_SUCCESS,required=false) String success,
			@RequestParam(value=ATTR_ERROR,required=false) String error){
		if(success != null){
			model.addAttribute(ATTR_SUCCESS, success);
		}
		if(error != null){
			model.addAttribute(ATTR_ERROR, error);
		}
		return "redirect:index";
	}
	

	@ExceptionHandler(HttpSessionRequiredException.class)
	public String init(){
		LOGGER.debug("Handling exception");
		return "redirect:reset";
	}
}
