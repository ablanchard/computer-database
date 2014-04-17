package com.excilys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.service.ComputerService;
import com.excilys.service.NotExistException;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;

@Component
@Controller
public class DeleteComputerController extends ComputerController {
	
	public static final String SUCCESS_MESSAGE = "computer.success.delete" ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteComputerController.class);

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String edit(Model model,
    		@RequestParam(value="id", required=true) Integer id,
    		RedirectAttributes redirectAttributes){
		try{
			getComputerService().delete(id);
			redirectAttributes.addAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE);
		} catch (NotExistException | NumberFormatException e){
			redirectAttributes.addAttribute(ATTR_ERROR, ComputerService.NOT_EXIST);
		} catch (ServiceException e){
			redirectAttributes.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		return "redirect:return";
	}
}
