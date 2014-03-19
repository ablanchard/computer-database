package com.excilys.dao;

import java.util.List;

public interface DAO<E> {
	
	
	public void create(E e) throws DaoException ;
	public List<E> retrieve(SearchWrapper sw) throws DaoException ;
	public E retrieveById(int id) throws DaoException ;
	public void update(E e) throws DaoException ;
	public void delete(E e) throws DaoException ;
	public void deleteById(int id) throws DaoException ;
	public int count(SearchWrapper sw) throws DaoException ;

}
