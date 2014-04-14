package com.excilys.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.data.ComputerForm;

@Component
public class ComputerDTOValidator  implements Validator {

	@Autowired
	private MessageSource messageSource ;
	
	@Autowired
	private ComputerMapper computerMapper ;
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(ComputerDTOValidator.class);
	
	public boolean realDate(String date) {
		if(date == null){
			return true;
		}
		if(date.equals("")){
			return true;
		}
		Pattern regex = Pattern.compile(messageSource.getMessage("date.regex", null, LocaleContextHolder.getLocale()));
		Matcher m = regex.matcher(date);
		return m.matches();
	}

	public static boolean dateOrder(DateTime introducedDate,DateTime discontinuedDate) {
		if(discontinuedDate != null && introducedDate != null){
			if(discontinuedDate.isBefore(introducedDate)){
				return false;
			}
		}
		return true;
	}
	
	/*
	 * This validator validates only ComputerDTO
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class clazz) {
		return ComputerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, ComputerForm.ATTR_NAME, "error.required");	
        ComputerDTO dto = (ComputerDTO) obj;
        LOGGER.debug("Start validation");
        if(!realDate(dto.getIntroducedDate())){
        	errors.rejectValue(ComputerForm.ATTR_INTRO,"error.invalid");
        } 
        
        if(!realDate(dto.getDiscontinuedDate())){
        	errors.rejectValue(ComputerForm.ATTR_DISC,"error.invalid");
        } 
        
        if(realDate(dto.getDiscontinuedDate()) && realDate(dto.getIntroducedDate())){
	        if(!dateOrder(computerMapper.toDate(dto.getIntroducedDate()),computerMapper.toDate(dto.getDiscontinuedDate()))){
	        	errors.rejectValue(ComputerForm.ATTR_DISC,"error.order");
	        }
        }
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ComputerMapper getComputerMapper() {
		return computerMapper;
	}

	public void setComputerMapper(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	}
}
