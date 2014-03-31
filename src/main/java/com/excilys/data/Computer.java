package com.excilys.data;

import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
	
	public Computer(String name, Date introduced, Date discontinued, Company company){
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	private Computer(){
		
	}
	
	public String toString(){
		String com = (company==null)?"null":company.getName();
		String intro = (introduced==null)?"null":introduced.toString();
		String dis = (discontinued==null)?"null":discontinued.toString();
		
		StringBuilder res = new StringBuilder(Integer.toString(getId()));
		res.append(" ");
		res.append(name );
		res.append(" introduced " );
		res.append(intro );
		res.append(" discontinues " );
		res.append(dis );
		res.append(" company : " );
		res.append(com);
		return res.toString();
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

	public Date getIntroduced() {
		return introduced;
	}

	public Computer setIntroduced(Date introduced) {
		this.introduced = introduced;
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
