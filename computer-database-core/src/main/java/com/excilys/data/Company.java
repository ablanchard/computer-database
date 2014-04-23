package com.excilys.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="company")
public class Company implements Serializable {

    @Id
	@GeneratedValue
	@Column(name="id",nullable=false)
	private Long id;

	@Column(name="name",nullable=false)
	private String name;

	public Company(String name){
		this.name = name;
	}


	public Company(Long id){
		this.id = id;
	}

	private Company(){

	}

    public Long getId() {
        return id;
    }

	public String getName() {
		return name;
	}



    public Company setId(Long id) {
        this.id = id;
        return this;
    }

	public Company setName(String name) {
		this.name = name;
		return this;
	}
	
	public static Company build(){
		return new Company();
	}
	
	public String toString(){
		StringBuilder res = new StringBuilder(getId().toString());
		res.append(" ");
		res.append(getName());
		return res.toString();
	}

	

}
