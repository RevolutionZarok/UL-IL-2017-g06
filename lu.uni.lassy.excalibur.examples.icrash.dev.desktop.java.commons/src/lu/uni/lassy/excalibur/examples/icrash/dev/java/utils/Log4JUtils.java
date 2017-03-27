/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.java.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * The Class Log4JUtils.
 */
public class Log4JUtils {

	/** The logger which will log any details and show them on the console. */
	 final Logger log = Logger.getLogger(Log4JUtils.class);
	 
 	/**  The properties file that controls how the logger works. */
 	final String LOG_PROPERTIES_FILE = "Log4J.properties";

	/** The instance of the logger, created using the singleton pattern. */
	private static volatile Log4JUtils instance = new Log4JUtils();
 
    /**
     * Instantiates a the class file and initialises the logger.
     */
    private Log4JUtils() {
    	initializeLogger();
		log.debug("Log4JUtils - leaving the constructor ...");
    }
 
    /**
     * Gets the singleton instance of Log4JUtils.
     *
     * @return The single instance of this class
     */
    public static Log4JUtils getInstance() {
        return instance;
    }

    /**
     * Gets the logger.
     *
     * @return the logger to be used
     */
    public Logger getLogger() {
        return log;
    }

	/**
	 * Initialise the logger, which will log any details and show them on the console.
	 */
	private void initializeLogger() {
		Properties logProperties = new Properties();

		try {
			// load our log4j properties / configuration file
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(LOG_PROPERTIES_FILE);
			logProperties.load(in);
			PropertyConfigurator.configure(logProperties);
			log.debug("Logging initialized.");
		} catch (IOException e) {
			throw new RuntimeException("Unable to load logging property "
					+ LOG_PROPERTIES_FILE);
		}
	}
	
	/**
	 * Close logger.
	 */
	public void closeLogger(){
		org.apache.log4j.LogManager.shutdown();
	}
}
