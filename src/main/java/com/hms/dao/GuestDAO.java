package com.hms.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Long.parseLong;
import static java.lang.Boolean.parseBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hms.domain.Guest;
import com.hms.utils.DBUtils;


public class GuestDAO implements DAO<Guest>{
	public static final Logger LOGGER = LogManager.getLogger();

	String path ="Guests";
	String csvFilePath = "C:\\Users\\bogda\\Desktop\\Project disertation\\"+path+".csv";
	int batchSize = 200;
	Connection connection = null;
	@Override
	public Guest modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String firstName = resultSet.getString("first_name");
		String lastname = resultSet.getString("last_name");
		String DOB = resultSet.getString("DOB");
		String passportNumber = resultSet.getString("passport_number");
		String membershipNumber = resultSet.getString("membership_number");
		Boolean isActive = resultSet.getBoolean("is_active");
		return new Guest(id, firstName, lastname, DOB, passportNumber, membershipNumber, isActive);
	}

	/**
	 * Reads all guests from the database
	 * 
	 * @return A list of guests
	 */
	
	
//	@Override
//	public List<Guest> readInactive() {
//		try (Connection connection = DBUtils.getInstance().getConnection();
//				Statement statement = connection.createStatement();
//				ResultSet resultSet = statement.executeQuery("SELECT * FROM guests WHERE is_active = 0");) {
//			List<Guest> customers = new ArrayList<>();
//			while (resultSet.next()) {
//				customers.add(modelFromResultSet(resultSet));
//			}
//			return customers;
//		} catch (SQLException e) {
//			LOGGER.debug(e);
//			LOGGER.error(e.getMessage());
//		}
//		return new ArrayList<>();
//	}
	
	
	
	@Override
	public List<Guest> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM guests");) {
			List<Guest> customers = new ArrayList<>();
			while (resultSet.next()) {
				customers.add(modelFromResultSet(resultSet));
			}
			return customers;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Guest readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM guests ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	
	
	public Guest changeActive(Guest guest) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE guests SET is_active = ? WHERE id = ?");){
			statement.setBoolean(1, guest.getIsActive());
			statement.setLong(2, guest.getId());
			statement.executeUpdate();
			return read(guest.getId());
			
		}
	catch (Exception e) {
        e.printStackTrace();
			}
		return null;
	}
	
	public void uploadData() {
		try (Connection connection = DBUtils.getInstance().getConnection();){
			connection.setAutoCommit(false);
			String sql = "INSERT INTO guests (id, first_name, last_name, DOB, passport_number, membership_number, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
			System.out.println("Upload completed!");
	        PreparedStatement statement = connection.prepareStatement(sql);
	        BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
		    String lineText = null;
		    int count = 0;

		    lineReader.readLine(); // skip header line
		    
		    while ((lineText = lineReader.readLine()) != null) {
		        String[] data = lineText.split(",");
		        String id = data[0];
		        String first_name = data[1];
		        String last_name = data[2];
		        String DOB = data[3];
		        String passport_number = data[4];
		        String membership_number = data[5];
		        String isActive = data[6];

		        statement.setLong(1,parseLong(id));
	            statement.setString(2, first_name);
	            statement.setString(3, last_name);
	            statement.setString(4, DOB);
	            statement.setString(5, passport_number);
	            statement.setString(6, membership_number);
	            statement.setBoolean(7, parseBoolean(isActive));
	            
	            statement.addBatch();

	            if (count % batchSize == 0) {
	                statement.executeBatch();
	            }
		    }

		    lineReader.close();

	        // execute the remaining queries
	        statement.executeBatch();

	        connection.commit();
	        connection.close();
		}
		catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Creates a guest in the database
	 * 
	 * @param guest - takes in a guest object. id will be ignored
	 */
	@Override
	public Guest create(Guest guest) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO guests(first_name, last_name, DOB, passport_number, membership_number, is_active) VALUES (?, ?, ?, ?, ?, ?)");) {
			statement.setString(1, guest.getFirstName());
			statement.setString(2, guest.getLastname());
			statement.setString(3, guest.getDOB());
			statement.setString(4, guest.getPassportNumber());
			statement.setString(5, guest.getMembershipNumber());
			statement.setBoolean(6, guest.getIsActive());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Guest read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM guests WHERE id = ?");) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Updates a guest in the database
	 * 
	 * @param guest - takes in a guest object, the id field will be used to
	 *                 update that guest in the database
	 * @return
	 */
	@Override
	public Guest update(Guest guest) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE guests SET first_name = ?, last_name = ?, DOB = ?, passport_number = ?, membership_number = ?, is_active = ? WHERE id = ?");) {
			statement.setString(1, guest.getFirstName());
			statement.setString(2, guest.getLastname());
			statement.setString(3, guest.getDOB());
			statement.setString(4, guest.getPassportNumber());
			statement.setString(5, guest.getMembershipNumber());
			statement.setBoolean(6, guest.getIsActive());
			statement.setLong(7, guest.getId());
			statement.executeUpdate();
			return read(guest.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	} 

	/**
	 * Deletes a guest in the database
	 * 
	 * @param id - id of the guest
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM guests WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}
