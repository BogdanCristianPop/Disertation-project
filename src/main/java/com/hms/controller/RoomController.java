package com.hms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.hms.dao.RoomDAO;
import com.hms.domain.Room;
import com.hms.utils.Utils;

public class RoomController implements CrudController<Room>{
	public static final Logger LOGGER = LogManager.getLogger();


	private RoomDAO roomDAO;
	private Utils utils;

 
	public RoomController(RoomDAO roomDAO, Utils utils) {
		super();
		this.roomDAO = roomDAO;
		this.utils = utils;
	} 

	/**
	 * Reads all guests to the logger
	 */
	@Override
	public List<Room> readAll() {
		List<Room> rooms = roomDAO.readAll();
		for (Room room : rooms) {
			LOGGER.info(room);
		}
		return rooms;
	}

	/** 
	 * Creates a customer by taking in user input
	 */
	@Override
	public Room create() {
		LOGGER.info("Please enter a room number");
		int roomNumber = utils.getInt();
		LOGGER.info("Please enter a floor number");
		int floor = utils.getInt();
		LOGGER.info("Please enter the size(type) of the room");
		String size = utils.getString();
		LOGGER.info("Please enter room capacity");
		int capacity = utils.getInt();
		LOGGER.info("Please enter room price");
		double price = utils.getDouble();
		Room Room = roomDAO.create(new Room(roomNumber, floor, size, capacity, price));
		LOGGER.info("Room created");
		return Room;
	}

	/**
	 * Updates an existing customer by taking in user input
	 */
	@Override
	public Room update() {
		LOGGER.info("Please enter the id of the room you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a room number");
		int roomNumber = utils.getInt();
		LOGGER.info("Please enter a floor number");
		int floor = utils.getInt();
		LOGGER.info("Please enter the size(type) of the room");
		String size = utils.getString();
		LOGGER.info("Please enter room capacity");
		int capacity = utils.getInt();
		LOGGER.info("Please enter room price");
		double price = utils.getDouble();
		Room room = roomDAO.update(new Room(id, roomNumber, floor, size, capacity, price));
		LOGGER.info("Room updated");
		return room;
	}

	/**
	 * Deletes an existing customer by the id of the customer
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the room you would like to delete");
		Long id = utils.getLong();
		LOGGER.info("Room deleted");
		return roomDAO.delete(id);
		
	}


}
