package com.excilys.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.dto.DTOException;
import com.excilys.mapper.ComputerMapper;

public class ComputerValidator {

	public final static String NO_MONTH = "This month doesn't exist.";
	public final static String NO_DAY = "This day doesn't exist.";
	public final static String NO_DAY_IN_MONTH = "This day doesn't exist for this month.";
	public final static String NO_DAY_IN_FEB = "Not so much days in Febuary.";
	public final static String INVALID_FORMAT = " format is invalid";
	public final static String DATE_ORDER = "Discontinued date is before the introduced date.";

	public final static String NULL_NAME = "No name setted";
	public final static String EMPTY_NAME = "Name can't be empty";
	
	
	public static Date date(String date,String name) throws DTOException {
		Date res = null;
		if(date != null){
			if(!date.equals("")){
				try {	
					res = new SimpleDateFormat(ComputerMapper.DATE_PATTERN).parse(date);
					realDate(date);
					
				} catch (ParseException e) {
					throw new DTOException(name + INVALID_FORMAT );
				} catch(DTOException e){
					throw new DTOException(name +" : "+e.getMessage());
				}
			}
		}
		return res ;
	}
	
	public static boolean realDate(String date) throws DTOException{
		String[] d = date.split("-");
		int year = Integer.valueOf(d[0]);
		int month = Integer.valueOf(d[1]);
		int day = Integer.valueOf(d[2]);
		
		if(month < 1 || month > 12)
			throw new DTOException(NO_MONTH);
		
		if(day < 1 || day > 31)
			throw new DTOException(NO_DAY);
		
		if((month == 4 || month == 6 || month == 9 || month == 11) && day == 31)
			throw new DTOException(NO_DAY_IN_MONTH);
		
		if(month == 2){
			boolean isLeap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
			if(day > 29 || (day == 29 && !isLeap))
				throw new DTOException(NO_DAY_IN_FEB);
		}
		return true;
	}

	public static void dateOrder(Date introducedDate,Date discontinuedDate) throws DTOException {
		if(discontinuedDate != null && introducedDate != null){
			if(discontinuedDate.before(introducedDate))
				throw new DTOException(DATE_ORDER);
		}
	}
	
	public static void name(String name) throws DTOException {
		if(name == null)
			throw new DTOException(NULL_NAME);
		
		if(name.equals(""))
			throw new DTOException(EMPTY_NAME);
	}
}
