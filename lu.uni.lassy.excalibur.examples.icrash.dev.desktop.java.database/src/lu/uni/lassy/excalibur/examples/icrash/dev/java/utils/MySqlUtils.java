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

/**
 * The Class MySqlUtils which holds the address, username and other details for accessing the MySQL server.
 */
public class MySqlUtils {
	
	/**
	 * Initiates the MySqlUtils class and reads the properties file to populate the data.
	 */
	private MySqlUtils(){
		Log4JUtils log = Log4JUtils.getInstance();
		String strPort = PropertyUtils.getInstance().getProperty("database.port", String.valueOf(DB_PORT));
		try{
			DB_PORT = Integer.parseInt(strPort);
		} catch (NumberFormatException e){
			log.getLogger().error("Unable to parse port number");
			log.getLogger().error(e.getMessage());
		}			
		DB_HOST = PropertyUtils.getInstance().getProperty("database.host", DB_HOST);
		DB_NAME = PropertyUtils.getInstance().getProperty("database.name", DB_NAME);
		DB_USER_NAME = PropertyUtils.getInstance().getProperty("database.username", DB_USER_NAME);
		DB_USER_PWD = PropertyUtils.getInstance().getProperty("database.password", DB_USER_PWD);
		log.getLogger().debug("DB address is " + DB_HOST);
		log.getLogger().debug("DB port is " + DB_PORT);
	}
	
	/** The singleton instance of MySqlUtils. */
	private static MySqlUtils instance;
	
	/**
	 * Gets the singleton instance of the MySqlUtils class.
	 *
	 * @return single instance of MySqlUtils
	 */
	public static MySqlUtils getInstance(){
		if (instance == null)
			instance = new MySqlUtils();
		return instance;
	}
	
	/** The Constant DB_PORT, the port number of the database to access. */
	private int DB_PORT = 3306;
	
	/**
	 * Gets the Database port to use in connections.
	 *
	 * @return The database port as an integer
	 */
	public int getDBPort(){
		return this.DB_PORT;
	}
	
	/** The Constant DB_HOST, the address of the database to access. */
	private String DB_HOST = "localhost";
	
	/**
	 * Gets the Database host address to use in connections.
	 *
	 * @return the Database host address
	 */
	public String getDBHost(){
		return this.DB_HOST;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getURL(){
		return "jdbc:mysql://" + DB_HOST +":" + String.valueOf(DB_PORT) + "/";
	}
	
	/** The Constant DB_NAME, the name of the database with the data in on the database. */
	private String DB_NAME = "icrashfx";
	
	/**
	 * Gets the DB name.
	 *
	 * @return the DB name
	 */
	public String getDBName(){
		return this.DB_NAME;
	}
	
	/** The Constant DB_USER_NAME, the name of the user to access the database with. */
	private String DB_USER_NAME = "il2_icrash";
	
	/**
	 * Gets the DB user name.
	 *
	 * @return the DB user name
	 */
	public String getDBUserName(){
		return this.DB_USER_NAME;
	}
	
	/** The Constant DB_USER_PWD, the password of the user to access the database with. */
	private String DB_USER_PWD = "il2_icrash";
	
	/**
	 * Gets the DB password.
	 *
	 * @return the DB password
	 */
	public String getDBPassword(){
		return this.DB_USER_PWD;
	}
}
