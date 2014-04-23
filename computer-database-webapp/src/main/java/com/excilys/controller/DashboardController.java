package com.excilys.controller;

import com.excilys.service.PageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.util.Header;

import java.util.ArrayList;
import java.util.List;

@Component
@Controller
@SessionAttributes("computerPage")
public class DashboardController extends ComputerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	private int nbComputersPerPage= 15;

	public static final String ATTR_WRAPPER = "computerPage";
	public static final String ATTR_TABLE_HEADERS = "tableHeaders";
	
	@RequestMapping(value ="/", 
			//params = {"page","search","order","direction"},
			method = RequestMethod.GET)
    public String request(Model model,
    		@RequestParam(value="page", required=false, defaultValue="0") Integer page,
    		@RequestParam(value="search", required=false, defaultValue="") String search,
    		@RequestParam(value="order", required=false, defaultValue="id") String order,
    		@RequestParam(value="direction", required=false, defaultValue="ASC") String direction) {
		//sw.update(page,search,order,direction);
        LOGGER.debug("Dashboard inputs : page: {} search: '{}' order: '{}' direction: '{}'",page,search,order,direction);

        Sort sort = new Sort(Sort.Direction.valueOf(direction), order);
        PageRequest pageRequest = new PageRequest(page,nbComputersPerPage,sort);
		List<Computer> computers= new ArrayList<Computer>();
        model.addAttribute(ATTR_WRAPPER,new PageWrapper<Computer>(new PageImpl<Computer>(computers,pageRequest,0L),search));
		return "redirect:index";
    }
	
	@RequestMapping(value ="reset",
			method = RequestMethod.GET)
	public String reset(Model model){
		model.addAttribute(ATTR_WRAPPER, new PageWrapper<>(new PageImpl<ComputerDTO>(new ArrayList<ComputerDTO>(),new PageRequest(0,nbComputersPerPage),0),""));
        return "redirect:index";
	}
	
	@RequestMapping(value="index",
			method = RequestMethod.GET)
	public String show(Model model,
			@ModelAttribute(value=ATTR_WRAPPER) PageWrapper<ComputerDTO> pageWrapper){
        PageWrapper<Computer> computerPage = getComputerMapper().toComputer(pageWrapper);

        LOGGER.debug("Search: {}", pageWrapper.getPage());
        computerPage = getComputerService().getPage(computerPage);
        //swComputer.updatePageMax();
        model.addAttribute(ATTR_WRAPPER,getComputerMapper().toComputerDTO(computerPage));

		//Table headers
		model.addAttribute(ATTR_TABLE_HEADERS, Header.getArray());
		
		return "dashboard";
	}
	
	@RequestMapping(value="return")
	public String show(Model model,
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
