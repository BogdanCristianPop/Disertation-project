package com.hms.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.hms.domain.Room;
import com.hms.utils.DBUtils;

public class RoomDAO implements DAO<Room>{
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public Room modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		int roomNumber = resultSet.getInt("roomNumber");
		int floor = resultSet.getInt("floor");
		String size = resultSet.getString("size");
		int capacity = resultSet.getInt("capacity");
		double price = resultSet.getDouble("price");
		
		return new Room(id, roomNumber, floor, size, capacity, price);
	}

	/**
	 * Reads all guests from the database
	 * 
	 * @return A list of guests
	 */
	@Override
	public List<Room> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms");) {
			List<Room> rooms = new ArrayList<>();
			while (resultSet.next()) {
				rooms.add(modelFromResultSet(resultSet));
			}
			return rooms;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Room readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	
	/**
	 * Creates a guest in the database
	 * 
	 * @param guest - takes in a guest object. id will be ignored
	 */
	@Override
	public Room create(Room room) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO rooms(roomNumber, floor, size, capacity, price) VALUES (?, ?, ?, ?, ?)");) {
			statement.setInt(1, room.getRoomNumber());
			statement.setInt(2, room.getFloor());
			statement.setString(3, room.getSize());
			statement.setInt(4, room.getCapacity());
			statement.setDouble(5, room.getPrice());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Room read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE id = ?");) {
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
	public Room update(Room room) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE rooms SET roomNumber = ?, floor = ?, size = ?, capacity = ?, price = ? WHERE id = ?");) {
			statement.setInt(1, room.getRoomNumber());
			statement.setInt(2, room.getFloor());
			statement.setString(3, room.getSize());
			statement.setInt(4, room.getCapacity());
			statement.setDouble(5, room.getPrice());
			statement.setLong(6, room.getId());
			statement.executeUpdate();
			return read(room.getId());
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
				PreparedStatement statement = connection.prepareStatement("DELETE FROM rooms WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}
}