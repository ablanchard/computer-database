package com.excilys.data;

import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Date introduction;
	private Date discontinued;
	private Company company;
	
	public Computer(String name, Date introduction, Date discontinued, Company company){
		this.name = name;
		this.introduction = introduction;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public String toString(){
		String com = (company==null)?"null":company.getName();
		String intro = (introduction==null)?"null":introduction.toString();
		String dis = (discontinued==null)?"null":discontinued.toString();
		return name + " introduced " + intro + " discontinues " + dis + " company : " + com;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroduction() {
		return introduction;
	}

	public void setIntroduction(Date introduction) {
		this.introduction = introduction;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
