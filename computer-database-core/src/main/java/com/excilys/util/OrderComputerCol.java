package com.excilys.util;

import com.excilys.data.QCompany;
import com.excilys.data.QComputer;
import com.mysema.query.types.expr.ComparableExpressionBase;

public enum OrderComputerCol {
	name,
	intro,
	disc,
	company;

    public ComparableExpressionBase<?> toQuery(){
        switch(this){
            case name :
                return QComputer.computer.name;
            case intro:
                return QComputer.computer.introduced;
            case disc:
                return QComputer.computer.discontinued;
            case company:
                return QCompany.company.name;
        }
        return null;
    }
}
