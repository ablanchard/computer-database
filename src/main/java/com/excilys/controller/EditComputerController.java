package com.excilys.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.ComputerService;
import com.excilys.service.NotExistException;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;
import com.excilys.servlet.ComputerForm;
import com.excilys.validator.ComputerDTOValidator;

@Component
@Controller
@SessionAttributes({"dto","form_attrs","companies"})
public class EditComputerController extends ComputerController {

	public static final String SUCCESS_MESSAGE = "computer.success.edit" ;

	private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);

	@Autowired
	private ComputerDTOValidator computerDTOValidator;
	
	@RequestMapping(value="initEdit", method = RequestMethod.GET)
	public String init(Model model,
			@RequestParam(value="id", required=true) Integer id) {
		ComputerDTO dto = null;
		try {
			dto = getComputerMapper().toComputerDTO(getComputerService().getById(id));
			LOGGER.debug(dto.toString());
			model.addAttribute("dto",dto);
			model.addAttribute(ATTR_COMPANIES, getCompanyService().getAll());
			model.addAttribute(FORM_ATTRS, ComputerForm.getInstance());
		} catch (NotExistException e) {
			model.addAttribute(ATTR_DTO, null);
		} catch (ServiceException e){
			model.addAttribute(ATTR_COMPANIES, null);
		}
		return "redirect:edit";
	
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String doGet(Model model,
			@ModelAttribute(ATTR_DTO) ComputerDTO dto,
			BindingResult results,
			@ModelAttribute(ATTR_COMPANIES) List<Company> companies,
    		RedirectAttributes redirectAttributes){
		if(dto == null){
			redirectAttributes.addAttribute(ATTR_ERROR, "computer.not.exist");
			return "redirect:return";
		}			
		if(companies == null){
			redirectAttributes.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
			return "redirect:return";
		}
		return "editComputer";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	protected String doPost(Model model,
			@Valid @ModelAttribute(ATTR_DTO) ComputerDTO dto, 
			BindingResult results,
			RedirectAttributes redirectAttributes){
		Computer c;
		if(results.hasErrors()){
			LOGGER.debug("There is errors {}",results.toString());
			return "editComputer";
		}
		try{
			c = getComputerMapper().toComputer(dto);
			SearchWrapper<Computer> sw = new SearchWrapper<Computer>(c);
			getComputerService().update(sw);
			redirectAttributes.addAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE);
		} catch (ServiceException e){
			redirectAttributes.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		} catch(NotExistException e){
			redirectAttributes.addAttribute(ATTR_ERROR, ComputerService.NOT_EXIST);
		}
		return "redirect:return";
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
