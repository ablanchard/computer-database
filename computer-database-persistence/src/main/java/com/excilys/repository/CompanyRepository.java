package com.excilys.repository;

import com.excilys.data.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by excilys on 18/04/14.
 */
public interface CompanyRepository extends JpaRepository<Company, Long>{
}
