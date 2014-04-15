package com.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.data.Company;

public class CompanyRowMapper implements RowMapper<Company>{

	@Override
	public Company mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Company c = null;
		c = new Company(rs.getString(CompanyDAO.ATTR_NAME));
		c.setId(rs.getInt(CompanyDAO.ATTR_ID));
		
		
		return c;
	}

}
