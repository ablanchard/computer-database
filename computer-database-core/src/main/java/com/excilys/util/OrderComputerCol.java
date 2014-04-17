package com.excilys.util;

public enum OrderComputerCol {
	name,
	intro,
	disc,
	company;

    public String toQuery(){
        switch(this){
            case name :
                return "name";
            case intro:
                return "introduced";
            case disc:
                return "discontinued";
            case company:
                return "com.name";
        }
        return null;
    }
}
