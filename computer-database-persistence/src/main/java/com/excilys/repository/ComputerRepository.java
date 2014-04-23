package com.excilys.repository;

import com.excilys.data.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;



/**
 * Created by excilys on 18/04/14.
 */
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    public Page<Computer> findByNameContainingOrCompanyNameContaining(String name, String companyName,Pageable pageable);
}
