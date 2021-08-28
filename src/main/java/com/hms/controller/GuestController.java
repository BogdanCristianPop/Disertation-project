package com.hms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.hms.dao.GuestDAO;
import com.hms.domain.Guest;

import com.hms.utils.Utils;



public class GuestController implements CrudController<Guest> {

	public static final Logger LOGGER = LogManager.getLogger();


	private GuestDAO guestDAO;
	private Utils utils;

 
	public GuestController(GuestDAO guestDAO, Utils utils) {
		super();
		this.guestDAO = guestDAO;
		this.utils = utils;
	} 

	/**
	 * Reads all guests to the logger
	 */
	@Override
	public List<Guest> readAll() {
		List<Guest> guests = guestDAO.readAll();
		for (Guest guest : guests) {
			LOGGER.info(guest);
		}
		return guests;
	}

	/** 
	 * Creates a customer by taking in user input
	 */
	@Override
	public Guest create() {
		LOGGER.info("Please enter a first name");
		String firstName = utils.getString();
		LOGGER.info("Please enter a surname");
		String lastName = utils.getString();
		LOGGER.info("Please enter date of birth");
		String DOB = utils.getString();
		LOGGER.info("Please enter passport number");
		String passportNumber = utils.getString();
		LOGGER.info("Please enter membership number");
		String membershipNumber = utils.getString();
		LOGGER.info("Is the guest active? (Please enter true or false)");
		Boolean isActive = utils.getBoolean();
		Guest Guest = guestDAO.create(new Guest(firstName, lastName, DOB, passportNumber, membershipNumber, isActive));
		LOGGER.info("Guest created");
		return Guest;
	}

	/**
	 * Updates an existing customer by taking in user input
	 */
	@Override
	public Guest update() {
		
		LOGGER.info("What would you like to do:");
		LOGGER.info("1. Add CSV file with multiple guests");
		LOGGER.info("2. Update a particular guest");
		int orderAction1 = utils.getInt();
		switch (orderAction1) {
		case 1 :
			guestDAO.uploadData();
			break;
		case 2 :
			LOGGER.info("Which guest would you like to update?");
			readAll();
			Long id = utils.getLong();
			LOGGER.info("What would you like to do with the guest selected:");
			LOGGER.info("1. Activate guest");
			LOGGER.info("2. Dezactivate guest");
			LOGGER.info("3. Update guest details");
			int orderAction = utils.getInt();
			switch (orderAction) {
			case 1 :
				Boolean isActive = true;
				guestDAO.changeActive(new Guest(id, isActive));
				LOGGER.info("Guest is now active!");
				break;
			case 2 :
				Boolean isActive2 = false;
				guestDAO.changeActive(new Guest(id, isActive2));
				LOGGER.info("Guest is now not active!");
				break;
			case 3 :
			LOGGER.info("Please enter a first name");
			String firstName = utils.getString();
			LOGGER.info("Please enter a surname");
			String lastName = utils.getString();
			LOGGER.info("Please enter date of birth");
			String DOB = utils.getString();
			LOGGER.info("Please enter passport number");
			String passportNumber = utils.getString();
			LOGGER.info("Please enter membership number");
			String membershipNumber = utils.getString();
			LOGGER.info("Is the guest active? (Please enter true or false)");
			Boolean isActive1 = utils.getBoolean();
			guestDAO.update(new Guest(id, firstName, lastName, DOB, passportNumber, membershipNumber, isActive1));
			LOGGER.info("Guest updated");
			break;
			//return guest1;
			
		}	
		}
		return null;
		}
		

	
	//public void uploadData() {
	//	guestDAO.uploadData();
	//}
	
	
//	@Override
//	public Guest makeActive() {
//		LOGGER.info("Please enter the id of the guest you would like to activate");
//		List<Guest> guests = guestDAO.readAll();
//		for (Guest guest : guests) {
//			LOGGER.info(guest);
//		}
//		Long id = utils.getLong();
//		Boolean isActive = true;
//		Guest guest = guestDAO.makeActive(new Guest(id, isActive));
//		LOGGER.info("Guest is now active!");
//		return guest;
//	}
	
	
	
	/**
	 * Deletes an existing customer by the id of the customer
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the customer you would like to delete");
		Long id = utils.getLong();
		LOGGER.info("Guest deleted");
		return guestDAO.delete(id);
		
	}

}