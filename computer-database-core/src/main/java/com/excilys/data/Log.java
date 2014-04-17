package com.excilys.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.excilys.util.Operation;


@Entity
@Table(name="log")
public class Log {

	@Id
	@GeneratedValue
	@Column(name="id",nullable=false)
	private int id;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name="executed_on")
	private DateTime executedOn;
	
	@Column(name="target")
	private String target;

	@Column(name="command")
	private String command = "";
	
	@Column(name="operation")
	private Operation operation;
	
	public String toString(){
		return getTarget() +  " " + getOperation();
	}
	public int getId() {
		return id;
	}
	public Log setId(int id) {
		this.id = id;
		return this;
	}
	public String getTarget() {
		return target;
	}
	public Log setTarget(String target) {
		this.target = target;
		return this;
	}
	public String getCommand() {
		return command;
	}
	public Log setCommand(String command) {
		this.command = command;
		return this;
	}
	public Operation getOperation() {
		return operation;
	}
	public Log setOperation(Operation operation) {
		this.operation = operation;
		return this;
	}
	
	public static Log build(){
		return new Log();
	}
	public DateTime getExecutedOn() {
		return executedOn;
	}
	public void setExecutedOn(DateTime executedOn) {
		this.executedOn = executedOn;
	}
}
