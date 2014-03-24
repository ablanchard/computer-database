package com.excilys.servlet;

import java.util.ArrayList;
import java.util.List;

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
	
	
	
	
	
	
	
	

}
