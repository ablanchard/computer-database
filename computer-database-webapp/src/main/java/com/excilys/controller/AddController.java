package com.excilys.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.data.Computer;
import com.excilys.util.SearchWrapper;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;
import com.excilys.util.ComputerForm;
import com.excilys.validator.ComputerDTOValidator;

@Component
@Controller
@SessionAttributes("dto")
public class AddController extends ComputerController {
	public static final String SUCCESS_MESSAGE = "computer.success.add" ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AddController.class);

	@Autowired
	private ComputerDTOValidator computerDTOValidator;

	@RequestMapping(value="initAdd", method = RequestMethod.GET)
	public String init(Model model){
		model.addAttribute(ATTR_DTO,new ComputerDTO());
		return "redirect:create";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String doGet(Model model,
			@ModelAttribute(ATTR_DTO) ComputerDTO dto,
			BindingResult results,
    		RedirectAttributes redirectAttributes){
		try{			
			model.addAttribute(ATTR_COMPANIES, getCompanyService().getAll());		
			
			//On affecte les bons noms aux inputs
			model.addAttribute(FORM_ATTRS, ComputerForm.getInstance());
			return "addComputer";
			
		} catch (ServiceException e){
			redirectAttributes.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		return "redirect:return";
	}


	@RequestMapping(value = "create", method = RequestMethod.POST)
	protected String insert(Model model,
			@Valid @ModelAttribute(ATTR_DTO) ComputerDTO dto, 
			BindingResult results,
			RedirectAttributes redirectAttributes){
		Computer c;
		if(results.hasErrors()){
			LOGGER.debug("There is errors {}",results.toString());

			return doGet(model, dto, results, redirectAttributes);
		}
		try{
			LOGGER.debug("Will add {}",dto);
			c = getComputerMapper().toComputer(dto);
			SearchWrapper<Computer> sw = new SearchWrapper<Computer>(c);
			getComputerService().create(sw);
			redirectAttributes.addAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE);
		} catch (ServiceException e){
			redirectAttributes.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		return "redirect:return";
	}

	@ExceptionHandler(HttpSessionRequiredException.class)
	public String init(){
		LOGGER.debug("Handling exception");
		return "redirect:initAdd";
	}
	
	@InitBinder(ATTR_DTO)
	public void initBinderAll(WebDataBinder binder) {
		binder.setValidator(computerDTOValidator);
	}

	public ComputerDTOValidator getComputerDTOValidator() {
		return computerDTOValidator;
	}

	public void setComputerDTOValidator(ComputerDTOValidator computerDTOValidator) {
		this.computerDTOValidator = computerDTOValidator;
	}

}
