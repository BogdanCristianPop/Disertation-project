package com.hms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hms.dao.GuestDAO;
import com.hms.dao.ReservationDAO;
import com.hms.dao.RoomDAO;
import com.hms.domain.Guest;
import com.hms.domain.Reservation;
import com.hms.domain.Room;
import com.hms.utils.Utils;




public class ReservationController implements CrudController<Reservation>{
	public static final Logger LOGGER = LogManager.getLogger();

	private GuestDAO guestDAO = new GuestDAO();
	private ReservationDAO reservationDAO;
	private RoomDAO roomDAO = new RoomDAO();
	private Utils utils;

	public ReservationController(ReservationDAO reservationDAO, Utils utils) {
		super();
		this.reservationDAO = reservationDAO;
		this.utils = utils;
	}

	@Override
	public Reservation create() {
		LOGGER.info("Please enter one of the available customer id");
		List<Guest> guests = guestDAO.readAll();
		for (Guest guest : guests) {
			LOGGER.info(guest);
		}
		int customerId = utils.getInt();
		Reservation reservation = reservationDAO.create(new Reservation(customerId));
		LOGGER.info("Order created");
		return reservation;
	}

	@Override
	public List<Reservation> readAll() {
		List<Reservation> reservations = reservationDAO.readAll();
		for (Reservation reservation : reservations) {
			LOGGER.info(reservation);
		}
		return reservations;
	}
 
	@Override
	public Reservation update() {
		Reservation reservation = new Reservation();   
		LOGGER.info("Which order would you like to update?");
		List<Reservation> reservations = reservationDAO.readAll();
		for (Reservation reservation1 : reservations) {
			LOGGER.info(reservation1);
		}

		Long reservationId = utils.getLong();
		reservation.setId(reservationId);
		LOGGER.info("What would you like to do with the order selected:");
		LOGGER.info("1. Print total cost of all the items in the selected order");
		LOGGER.info("2. Add a new item on the selected order");
		LOGGER.info("3. Delete an item from the selected order");
		int orderAction = utils.getInt();
		switch (orderAction) {
		case 1:
			reservation = reservationDAO.update(reservation);
			break;
		case 2:
			LOGGER.info("Select an item which you want to add to the selected order");
			List<Room> rooms = roomDAO.readAll();
			for (Room room : rooms) {
				LOGGER.info(room.getId());
			}
			Long roomId = utils.getLong();
			reservationDAO.update(reservationId, roomId);
			LOGGER.info("Room " + roomId + " was added");
			break;
		case 3:
				LOGGER.info("Select an item which you want delete from the selected order");
				LOGGER.info(reservationDAO.readById(reservationId));
				Long roomId2 = utils.getLong();

				if (reservationDAO.readById(reservationId).contains(roomId2)) {
					reservationDAO.delete(reservationId, roomId2);
					LOGGER.info("Item " + roomId2 + " was removed");
				} else {
					LOGGER.info("The item you are trying to remove does not exist");
				}

			break;

		}
		return null;
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		List<Reservation> reservations = reservationDAO.readAll();
		for (Reservation reservation : reservations) {
			LOGGER.info(reservation);
		}
		Long id = utils.getLong();
		return reservationDAO.delete(id);

	}


}
