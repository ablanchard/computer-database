package com.excilys.service;

import com.excilys.data.Log;
import com.excilys.repository.ComputerRepository;
import com.excilys.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.excilys.data.Computer;

import javax.transaction.Transactional;

@Component
public class ComputerService{


	public static final String NOT_EXIST = "computer.not.exist";

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    @Autowired
    private ComputerRepository computerRepository = null;

    @Autowired
    private LogRepository logRepository = null;

    public ComputerRepository getComputerRepository() {
        return computerRepository;
    }

    public void setComputerRepository(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    public LogRepository getLogRepository() {
        return logRepository;
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Transactional
    public PageWrapper<Computer> getPage(PageWrapper<Computer> page){
        Page<Computer> pageComputer = page.getPage();
        PageRequest pageRequest = new PageRequest(pageComputer.getNumber(),pageComputer.getSize(),pageComputer.getSort());

        LOGGER.debug("Request to proceed: Page: {} Search: '{}' Sort: '{}'", pageComputer.getNumber(),page.getSearch(),pageComputer.getSort());
        if(page.getSearch() == null){
            page.setSearch("");
        }
        if(page.getSearch().equals("")){
            pageComputer = computerRepository.findAll(pageRequest);
        }
        else {
            String search = "%"+page.getSearch()+"%";
            pageComputer = computerRepository.findByNameContainingOrCompanyNameContaining(search,search,pageRequest);
            if(pageComputer.getNumber() > pageComputer.getTotalPages()){
                pageRequest = new PageRequest(0,pageComputer.getSize(),pageComputer.getSort());
                pageComputer = computerRepository.findByNameContainingOrCompanyNameContaining(search,search,pageRequest);
            }
        }
        logRepository.save(Log.build().setTarget("Computer").setOperation(Log.Operation.retrieve).setCommand(pageComputer.toString()));
        return new PageWrapper<>(pageComputer,page.getSearch());
    }


    @Transactional
	public Computer getById(Long id) throws NotExistException{
        Computer computer = computerRepository.findOne(id);
        logRepository.save(Log.build().setTarget("Computer").setOperation(Log.Operation.retrieve).setCommand(computer.toString()));
        if(computer == null){
			LOGGER.debug("NotExistException !");
			throw new NotExistException();
		}
		else {
			return computer;
		}
	}

    @Transactional
	public void delete(Long id) throws NotExistException{
		if(id == null){
			throw new NotExistException();
		}
		Computer computerToDelete = getById(id);
		computerRepository.delete(computerToDelete);
        logRepository.save(Log.build().setTarget("Computer").setOperation(Log.Operation.delete).setCommand(computerToDelete.toString()));
    }

    @Transactional
    public void update(Computer c) throws NotExistException{
        if(c == null){
            throw new NotExistException();
        }
        if(!computerRepository.exists(c.getId())){
            throw new NotExistException();
        }
        computerRepository.save(c);
        logRepository.save(Log.build().setTarget("Computer").setOperation(Log.Operation.update).setCommand(c.toString()));
    }

    @Transactional
    public void create(Computer c){
        computerRepository.save(c);
        logRepository.save(Log.build().setTarget("Computer").setOperation(Log.Operation.create).setCommand(c.toString()));
    }
}
