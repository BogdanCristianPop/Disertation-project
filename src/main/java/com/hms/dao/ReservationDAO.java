package com.hms.dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.hms.domain.Reservation;
import com.hms.utils.DBUtils;


public class ReservationDAO implements DAO<Reservation>{
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Reservation modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		int customerId = resultSet.getInt("customer_id");
		return new Reservation(id, customerId);

	}

	@Override
	public List<Reservation> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations");) {
			List<Reservation> reservations = new ArrayList<>();
			while (resultSet.next()) {
				reservations.add(modelFromResultSet(resultSet));
			}
			return reservations;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Reservation readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Reservation create(Reservation reservation) {
		try (Connection connection = DBUtils.getInstance().getConnection();

				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO reservations(customer_id) VALUES (?)");) {
			statement.setInt(1, reservation.getGuestId());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}
 
	@Override
	public Reservation update(Reservation reservation) {
		try (Connection connection = DBUtils.getInstance().getConnection();

				PreparedStatement statement = connection.prepareStatement(
						"SELECT SUM(i.price) AS price FROM rooms i JOIN reservations_items o ON i.id=o.room_id WHERE reservation_id= ?;");) {
			statement.setLong(1, reservation.getId());

			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				System.out.println(resultSet.getFloat("price"));
				
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		
		}
		return null;
	}

	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.info(
					"The order is not empty, please remove the rooms from the reservation before deleting the entire reservation!");
			LOGGER.debug(e);
		}
		return 0;
	}

	public long update(Long reservationId, Long roomId) {
		try (Connection connection = DBUtils.getInstance().getConnection();

				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO reservations_items (reservation_id, room_id) VALUES (?,?)");) {
			statement.setLong(1, reservationId);
			statement.setLong(2, roomId);
			return statement.executeUpdate();
			

		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error("update: " + e.getMessage());
			
		}
		return roomId;
	}

	public ArrayList<Long> readById(long reservationId) {
		try (Connection connection = DBUtils.getInstance().getConnection();

				PreparedStatement statement = connection
						.prepareStatement("SELECT room_id FROM reservations_items WHERE reservation_id=?;");) {
			statement.setLong(1, reservationId);
			ResultSet rs = statement.executeQuery();
			return resultSetToArrayList(rs);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			return null;
		}

	}

	public ArrayList<Long> resultSetToArrayList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		ArrayList<Long> list = new ArrayList<>();
		while (rs.next()) {

			for (int i = 1; i <= columns; ++i) {
				list.add(rs.getLong(i));
			}
		}
 
		return list;
	}

	public int delete(Long reservationId, Long roomId2) {

		try (Connection connection = DBUtils.getInstance().getConnection();

				PreparedStatement statement = connection
						.prepareStatement("DELETE FROM reservations_items WHERE reservation_id= ? AND room_id = ?");) {
			statement.setLong(1, reservationId);
			statement.setLong(2, roomId2);
			return statement.executeUpdate();

		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public Reservation read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations WHERE id = ?");) {
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
	
}
