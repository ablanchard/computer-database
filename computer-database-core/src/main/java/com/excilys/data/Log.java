package com.excilys.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;



@Entity
@Table(name="log")
public class Log {
    public enum Operation{
        create,
        retrieve,
        update,
        delete,
        count;

        public int toInt(){
            switch(this){
                case create:
                    return 0;
                case retrieve:
                    return 1;
                case update:
                    return 2;
                case delete:
                    return 3;

                case count:
                    return 4;
            }
            return -1;
        }

        public Operation valueOf(int id){
            switch(id){
                case 0:
                    return create;
                case 1:
                    return retrieve;
                case 2:
                    return update;
                case 3:
                    return delete;
                case 4:
                    return count;
            }
            return null;
        }
    }
    @Id
	@GeneratedValue
	@Column(name="id",nullable=false)
	private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
