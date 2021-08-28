package com.hms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hms.controller.GuestController;
import com.hms.controller.ReservationController;
import com.hms.controller.RoomController;
import com.hms.dao.GuestDAO;
import com.hms.dao.ReservationDAO;
import com.hms.dao.RoomDAO;
import com.hms.utils.Utils;
import com.hms.controller.Action;
import com.hms.controller.CrudController;
import com.hms.domain.Domain;
import com.hms.utils.DBUtils;


public class HMS {
	public static final Logger LOGGER = LogManager.getLogger();

	private final GuestController guests;
	private final RoomController rooms;
	private final ReservationController reservations;
	private final Utils utils;
	
	
	public HMS() {
		this.utils = new Utils();
		final GuestDAO guestDAO = new GuestDAO();
		this.guests = new GuestController(guestDAO, utils);
		final ReservationDAO reservationDAO = new ReservationDAO();
		this.reservations = new ReservationController(reservationDAO, utils);
		final RoomDAO roomDAO = new RoomDAO();
		this.rooms = new RoomController(roomDAO, utils);
	}
	
	public HMS(GuestController guests,RoomController rooms, Utils utils, ReservationController reservations) {
		super();
		this.guests = guests;
		this.rooms = rooms;
		this.utils = utils;
		this.reservations = reservations;
	}
	
	public void hmsSystem() {
		LOGGER.info("Welcome to the Inventory Management System!");
		DBUtils.connect();

		Domain domain = null;
		do {
			LOGGER.info("Which entity would you like to use?");
			Domain.printDomains();

			domain = Domain.getDomain(utils);

			domainAction(domain);

		} while (domain != Domain.STOP);
	}

	public void domainAction(Domain domain) {
		boolean changeDomain = false;
		do {

			CrudController<?> active = null;
			switch (domain) {
			case GUEST:
				active = this.guests;
				break;
			case ROOM:
				active = this.rooms;
			case RESERVATION:
				active = this.reservations;
				break;
			case STOP:
				return;
			default:
				break;
			}
 
			LOGGER.info(() ->"What would you like to do with " + domain.name().toLowerCase() + ":");

			Action.printActions();
			Action action = Action.getAction(utils);
			
			if (action == Action.RETURN) {
				changeDomain = true;
			} else {
				doAction(active, action);
			}
		} while (!changeDomain);
	}

	public void doAction(CrudController<?> crudController, Action action) {
		switch (action) {
		case CREATE:
			crudController.create();
			break;
		case READ:
			crudController.readAll();
			break;
		case UPDATE:
			crudController.update();
			break;
		case DELETE:
			LOGGER.info("In the system are curently the following guests:");
			crudController.readAll();
			crudController.delete();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}
	
	}

