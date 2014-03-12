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
	
	private Computer(){
		
	}
	
	public String toString(){
		String com = (company==null)?"null":company.getName();
		String intro = (introduction==null)?"null":introduction.toString();
		String dis = (discontinued==null)?"null":discontinued.toString();
		return name + " introduced " + intro + " discontinues " + dis + " company : " + com;
	}
	
	public static Computer builder(){
		return new Computer();
	}
	
	public Computer build(){
		return this;
	}

	public int getId() {
		return id;
	}

	public Computer setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Computer setName(String name) {
		this.name = name;
		return this;
	}

	public Date getIntroduction() {
		return introduction;
	}

	public Computer setIntroduction(Date introduction) {
		this.introduction = introduction;
		return this;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public Computer setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
		return this;
	}

	public Company getCompany() {
		return company;
	}

	public Computer setCompany(Company company) {
		this.company = company;
		return this;
	}
	
}
