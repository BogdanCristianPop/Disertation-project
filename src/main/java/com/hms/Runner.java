package com.hms;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Runner {

	
		public static final Logger LOGGER = LogManager.getLogger();

		public static void main(String[] args) {
			HMS hms = new HMS();
			hms.hmsSystem();
			LOGGER.info("SO LONG!");
		}
	
	
}
