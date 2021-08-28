package com.hms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



public interface DAO<T> {

	List<T> readAll();
	
	T read(Long id);

	T create(T t);

	T update(T t);

	int delete(long id);

	T modelFromResultSet(ResultSet resultSet) throws SQLException;

	/**
	 * Reads all guests from the database
	 * 
	 * @return A list of guests
	 */
	//List<Guest> readInactive();
}