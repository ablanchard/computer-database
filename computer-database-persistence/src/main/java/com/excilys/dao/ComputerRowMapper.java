package com.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.data.Company;
import com.excilys.data.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Computer c = null;
		//Map<Integer,Company> companies = CompanyDAO.getInstance().getAll();
		
			String companyName = rs.getString(7);
			Company company = (rs.wasNull())?null:new Company(companyName);
			if(company !=null)
				company.setId(rs.getInt(6));

			DateTime introduced = (rs.getTimestamp(ComputerDAO.ATTR_INTRODUCTION)==null)? null : new DateTime(rs.getTimestamp(ComputerDAO.ATTR_INTRODUCTION));
			DateTime discontinued = (rs.getTimestamp(ComputerDAO.ATTR_DISCONTINUED)==null)? null : new DateTime(rs.getTimestamp(ComputerDAO.ATTR_DISCONTINUED));
			
			c = new Computer(rs.getString(ComputerDAO.ATTR_NAME),introduced,discontinued,company);
			c.setId(rs.getInt(1));
		
		return c;
	}

}
