package com.excilys.validator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.servlet.ComputerForm;

@Component
public class ComputerDTOValidator  implements Validator {

	public static final String NO_MONTH = "This month doesn't exist.";
	public static final String NO_DAY = "This day doesn't exist.";
	public static final String NO_DAY_IN_MONTH = "This day doesn't exist for this month.";
	public static final String NO_DAY_IN_FEB = "Not so much days in Febuary.";
	public static final String INVALID_FORMAT = "Format is invalid";
	public static final String DATE_ORDER = "Discontinued date is before the introduced date.";
	public static final String NOT_REAL_DATE = "Not a real date.";

	public static final String NULL_NAME = "No name setted";
	public static final String EMPTY_NAME = "Name can't be empty";
	public static final Pattern REGEX = Pattern.compile("^([\\d]{4})\\-(0[13578]|1[02])\\-(0[1-9]|[1-2][\\d]|3[0-1])$|"
			+ "^([\\d]{4})\\-(0[469]|11)\\-(0[1-9]|[1-2][\\d]|30)$h|"
			+ "^([\\d]{4})\\-(02)\\-(0[1-9]|1[\\d]|2[0-8])$|"
			+ "^(((18|19|20)(04|08|[2468][048]|[13579][26]))|2000)\\-(02)\\-(0[1-9]|1[\\d]|2[\\d])$");
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(ComputerDTOValidator.class);
	
	
	
	
	
	public static boolean realDate(String date) {
		if(date == null){
			return true;
		}
		if(date.equals("")){
			return true;
		}
		Matcher m = REGEX.matcher(date);
		return m.matches();
		/*if(m.matches()){
			
		int year = Integer.valueOf(m.group(0));
		int month = Integer.valueOf(m.group(1));
		int day = Integer.valueOf(m.group(2));
		
		if(month < 1 || month > 12){
			throw new DTOException(NO_MONTH);
		}
		
		if(day < 1 || day > 31){
			throw new DTOException(NO_DAY);
		}
		
		if((month == 4 || month == 6 || month == 9 || month == 11) && day == 31){
			throw new DTOException(NO_DAY_IN_MONTH);
		}
		
		if(month == 2){
			boolean isLeap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
			if(day > 29 || (day == 29 && !isLeap))
				throw new DTOException(NO_DAY_IN_FEB);
		}
		
		return true;
		}
		return false;*/
	}

	public static boolean dateOrder(Date introducedDate,Date discontinuedDate) {
		if(discontinuedDate != null && introducedDate != null){
			if(discontinuedDate.before(introducedDate)){
				return false;
			}
		}
		return true;
	}
	
	public static void name(String name) throws DTOException {
		if(name == null){
			throw new DTOException(NULL_NAME);
		}
		
		if(name.equals("")){
			throw new DTOException(EMPTY_NAME);
		}
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, ComputerForm.ATTR_NAME, "required", "Field is required.");	
        ComputerDTO dto = (ComputerDTO) obj;
        LOGGER.debug("Start validation");
        if(!realDate(dto.getIntroducedDate())){
        	errors.rejectValue(ComputerForm.ATTR_INTRO,"invalid",INVALID_FORMAT);
        	
        }

        if(!realDate(dto.getDiscontinuedDate())){
        	errors.rejectValue(ComputerForm.ATTR_DISC,"invalid", INVALID_FORMAT);
        }
        
        if(!dateOrder(ComputerMapper.toDate(dto.getIntroducedDate()),ComputerMapper.toDate(dto.getDiscontinuedDate()))){
        	errors.rejectValue(ComputerForm.ATTR_DISC,"order", DATE_ORDER);
        }
        	
	}
}
