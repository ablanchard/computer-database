package com.excilys.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.CompanyDAO;
import com.excilys.dto.DTOException;
import com.excilys.mapper.ComputerMapper;

public class ComputerValidator {

	public static final String NO_MONTH = "This month doesn't exist.";
	public static final String NO_DAY = "This day doesn't exist.";
	public static final String NO_DAY_IN_MONTH = "This day doesn't exist for this month.";
	public static final String NO_DAY_IN_FEB = "Not so much days in Febuary.";
	public static final String INVALID_FORMAT = " format is invalid";
	public static final String DATE_ORDER = "Discontinued date is before the introduced date.";
	public static final String NOT_REAL_DATE = "Not a real date.";

	public static final String NULL_NAME = "No name setted";
	public static final String EMPTY_NAME = "Name can't be empty";
	public static final Pattern REGEX = Pattern.compile("^([\\d]{4})\\-(0[13578]|1[02])\\-(0[1-9]|[1-2][\\d]|3[0-1])$|"
			+ "^([\\d]{4})\\-(0[469]|11)\\-(0[1-9]|[1-2][\\d]|30)$h|"
			+ "^([\\d]{4})\\-(02)\\-(0[1-9]|1[\\d]|2[0-8])$|"
			+ "^(((18|19|20)(04|08|[2468][048]|[13579][26]))|2000)\\-(02)\\-(0[1-9]|1[\\d]|2[\\d])$");
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(ComputerValidator.class);
	
	
	
	public static Date date(String date,String name) throws DTOException {
		Date res = null;
		if(date != null){
			if(!date.equals("")){
				try {	
					res = new SimpleDateFormat(ComputerMapper.DATE_PATTERN).parse(date);
					realDate(date);
					
				} catch (ParseException e) {
					LOGGER.debug("Parse Exception throwed DTOException : INVALID_FORMAT");
					throw new DTOException(name + INVALID_FORMAT );
				} catch(DTOException e){
					LOGGER.debug("DTOException throwed DTOException : {}",e.getMessage());
					throw new DTOException(name +" : "+e.getMessage());
				}
			}
		}
		return res ;
	}
	
	public static boolean realDate(String date) throws DTOException{
		Matcher m = REGEX.matcher(date);
		if(!m.matches()){
			throw new DTOException(NOT_REAL_DATE);
			
		}
		return true;
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

	public static void dateOrder(Date introducedDate,Date discontinuedDate) throws DTOException {
		if(discontinuedDate != null && introducedDate != null){
			if(discontinuedDate.before(introducedDate)){
				throw new DTOException(DATE_ORDER);
			}
		}
	}
	
	public static void name(String name) throws DTOException {
		if(name == null){
			throw new DTOException(NULL_NAME);
		}
		
		if(name.equals("")){
			throw new DTOException(EMPTY_NAME);
		}
	}
}
