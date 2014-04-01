package com.excilys.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOException;
import com.excilys.service.ComputerService;
import com.excilys.service.NotExistException;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;
import com.excilys.servlet.ComputerForm;


@Controller
public class AddController extends ComputerController {
	public static final String SUCCESS_MESSAGE_ADD = "Computer successfully added." ;
	public static final String SUCCESS_MESSAGE_EDIT = "Computer successfully edited." ;
	public static final String SUCCESS_MESSAGE_DELETE = "Computer successfully deleted." ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AddController.class);

   

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(Model model,
			@ModelAttribute("dto") ComputerDTO dto,
    		@RequestParam(value="id", required=true) Integer id,
    		RedirectAttributes redirectAttributes){
		ComputerDTO computerDTO = new ComputerDTO();
		LOGGER.info(dto + " " + id);
		//Le champ dto n'est pas présent => premier access au formulaire
		try{
			//Edition
			if(dto != null){
				
				model.addAttribute("actionName","Edit");
				//1 ere access a la page on recupere les valeurs dans la base
				if(dto.getName() == null){
					int computerId = dto.getId();
					
					SearchWrapper<Computer> computerWrap = new SearchWrapper<Computer>(Computer.builder().setId(computerId));
					getComputerService().retrieve(computerWrap);
					
					if(computerWrap.getItems().size() == 0){
						throw new NotExistException(ComputerService.NOT_EXIST);
					}
					Computer computer = computerWrap.getItems().get(0);
					
					if(computer.getCompany() == null){
						computer.setCompany(Company.build().setId(0));
					}
				
					computerDTO = getComputerMapper().toComputerDTO(computer);
		
				}
				else {
					//Le champ est présent => nouvel access apres avoir fait une faute lors du doPost
					computerDTO = dto;
				}
			}
			
			//Recupération de la liste des companies
			SearchWrapper<Company> sw = new SearchWrapper<Company>();
			getCompanyService().retrieve(sw);	
			List<Company> companies = sw.getItems();
			
			//Ajout de la company d'id 0
			companies.add(0, Company.build().setName(NO_COMPANY_DEFAULT_NAME).setId(0));
			
			
			model.addAttribute(ATTR_COMPANIES, companies);		
			model.addAttribute(ATTR_DTO, computerDTO);
			
			//On affecte les bons noms aux inputs
			model.addAttribute(FORM_ATTRS, ComputerForm.getInstance());
			model.addAttribute(FORM_ERRORS, ComputerForm.getErrors());
			
			return "editComputer";
			
		} catch(NotExistException | NumberFormatException e){
			redirectAttributes.addAttribute(ATTR_ERROR, e.getMessage());
		} catch (ServiceException e){
			redirectAttributes.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		return "redirect:return";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model){
		return edit(model,null, -1,null);
	}
	
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	protected String insert(@ModelAttribute("dto") ComputerDTO dto, 
			Model model,
			RedirectAttributes redirectAttributes){
		Computer c;
		try{
			c = getComputerMapper().toComputer(dto);
			SearchWrapper<Computer> sw = new SearchWrapper<Computer>(c);
			if(dto.getId() == -1){
				getComputerService().create(sw);
				redirectAttributes.addAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE_ADD);
			} else{
				getComputerService().update(sw);
				redirectAttributes.addAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE_EDIT);
			}
		} catch (DTOException e){
			
			//Erreur dans le formulaire. On renvoi la meme page avec une erreur et les champs déjà remplis.
			model.addAttribute(ATTR_ERROR, e.getMessage());
			model.addAttribute(ATTR_DTO, dto);
			return edit(model,dto,dto.getId(),null);
			
		} catch (ServiceException e){
			redirectAttributes.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		} catch(NotExistException e){
			redirectAttributes.addAttribute(ATTR_ERROR, ComputerService.NOT_EXIST);
		}
		return "redirect:return";
		
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String edit(Model model,
    		@RequestParam(value="id", required=true) Integer id,
    		RedirectAttributes redirectAttributes){
		try{
			if(id == null){
				throw new NotExistException();
			}
			
			SearchWrapper<Computer> computerToDelete = new SearchWrapper<Computer>(Computer.builder().setId(id));
		
		
			getComputerService().delete(computerToDelete);
			redirectAttributes.addAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE_DELETE);
		} catch (NotExistException | NumberFormatException e){
			redirectAttributes.addAttribute(ATTR_ERROR, ComputerService.NOT_EXIST);
		} catch (ServiceException e){
			redirectAttributes.addAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		
		return "redirect:return";
	}
}
