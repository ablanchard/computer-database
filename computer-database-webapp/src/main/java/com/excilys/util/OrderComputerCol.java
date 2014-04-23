package com.excilys.util;

public enum OrderComputerCol {
	name,
	intro,
	disc,
	company,
    noOrder;

    public String toProperty(){
        switch(this){
            case name :
                return "name";
            case intro:
                return "introduced";
            case disc:
                return "discontinued";
            case company:
                return "company.name";
            case noOrder:
                return "id";
        }
        return null;
    }
}
