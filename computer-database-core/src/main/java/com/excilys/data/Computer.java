package com.excilys.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


@Entity
@Table(name="computer")
public class Computer implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="id",nullable=false)
	private int id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name="introduced",columnDefinition="TIMESTAMP NULL")
	private DateTime introduced;
	
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name="discontinued")
	private DateTime discontinued;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="company_id",nullable=true)
    @Fetch(FetchMode.JOIN)
	private Company company;
	
	
	public Computer(String name, DateTime introduced, DateTime discontinued, Company company){
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	private Computer(){
		
	}
	
	public Computer(int id){
		this.id = id;
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

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public DateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(DateTime introduced) {
		this.introduced = introduced;
	}

	public DateTime getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(DateTime discontinued) {
		this.discontinued = discontinued;
	}
	
}
