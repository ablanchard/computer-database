package com.excilys.servlet;

import java.util.Map;
import java.util.TreeMap;

import com.excilys.validator.ComputerValidator;

public class ComputerForm {
	
	public static final String ATTR_NAME = "name";
	public static final String ATTR_INTRO = "introducedDate";
	public static final String ATTR_DISC = "discontinuedDate";
	public static final String ATTR_COMPANY = "company";
	public static final String ATTR_ID = "id";
	

	public static final String TITLE_NAME = "Computer name";
	public static final String TITLE_INTRO = "Introduced date";
	public static final String TITLE_DISC = "Discontinued date";
	public static final String TITLE_COMPANY = "Company name";
	public static final String TITLE_ID = "id";
	
	public final static String ID_NO_MONTH = "noMonth";
	public final static String ID_NO_DAY = "noDay";
	public final static String ID_NO_DAY_IN_MONTH = "noDayInMonth";
	public final static String ID_NO_DAY_IN_FEB = "noDayInFeb";
	public final static String ID_INVALID_FORMAT = "invalidFormat";
	public final static String ID_DATE_ORDER = "dateOrder";

	public final static String ID_NULL_NAME = "nullName";
	public final static String ID_EMPTY_NAME = "emptyName";
	

	public FormInput name = new FormInput(ATTR_NAME, TITLE_NAME);
	public FormInput intro = new FormInput(ATTR_INTRO, TITLE_INTRO);
	public FormInput disc = new FormInput(ATTR_DISC, TITLE_DISC);
	public FormInput company = new FormInput(ATTR_COMPANY, TITLE_COMPANY);
	public FormInput id = new FormInput(ATTR_ID, TITLE_ID);
	
	private static ComputerForm INSTANCE = null;
	
	public static ComputerForm getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ComputerForm();
		}
		return INSTANCE;
			
	}
	
	private ComputerForm(){
		
	}
	
	
	public FormInput getName() {
		return name;
	}


	public FormInput getIntro() {
		return intro;
	}
	
	
	public FormInput getDisc() {
		return disc;
	}
	
	
	public FormInput getCompany() {
		return company;
	}
	

	public FormInput getId() {
		return id;
	}
	
	
	public static Map<String,String> getErrors(){
		Map<String,String> errors = new TreeMap<String,String>();
		errors.put(ID_NO_DAY,ComputerValidator.NO_DAY);
		errors.put(ID_NO_MONTH,ComputerValidator.NO_MONTH);
		errors.put(ID_NO_DAY_IN_MONTH,ComputerValidator.NO_DAY_IN_MONTH);
		errors.put(ID_NO_DAY_IN_FEB,ComputerValidator.NO_DAY_IN_FEB);
		errors.put(ID_INVALID_FORMAT ,ComputerValidator.INVALID_FORMAT);
		errors.put(ID_DATE_ORDER ,ComputerValidator.DATE_ORDER);
		errors.put(ID_NULL_NAME ,ComputerValidator.NULL_NAME);
		errors.put(ID_EMPTY_NAME ,ComputerValidator.EMPTY_NAME);
		return errors;
	}
	
	
	
	
	

}
