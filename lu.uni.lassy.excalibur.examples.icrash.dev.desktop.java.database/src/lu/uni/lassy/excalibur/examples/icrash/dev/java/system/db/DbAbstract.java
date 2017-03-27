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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.Connection;
import org.apache.log4j.Logger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.MySqlUtils;

/**
 * The Class DbAbstract with some default settings that every database access should use.
 */
public abstract class DbAbstract {
	/** The logger that is used to log information to the console. */
	public static Logger log = Log4JUtils.getInstance().getLogger();

	/** The connection to the database. */
	public static Connection conn = null;

	/** The address of the database. */
	public static String url = MySqlUtils.getInstance().getURL();
	
	/** The name of the database. */
	public static String dbName = MySqlUtils.getInstance().getDBName();

	/** The user name used to logon to the database. */
	public static String userName = MySqlUtils.getInstance().getDBUserName();
	
	/** The password used to logon to the database. */
	public static String password = MySqlUtils.getInstance().getDBPassword();
	
	/**
	 * A generic method to log an issue when connecting or using the database
	 * @param e The exception to appear in the server console window
	 */
	public static void logException(Exception e){
		log.error("Error connecting to SQL server at: " + url);
		log.error(e.getMessage());
	}
}
