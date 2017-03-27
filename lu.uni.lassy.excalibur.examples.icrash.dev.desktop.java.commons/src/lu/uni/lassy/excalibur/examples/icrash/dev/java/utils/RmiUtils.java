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
 * The Class RmiUtils which holds address of the server and the port number to use.
 */
public class RmiUtils {
	
	/**
	 * Instantiates a new class, that holds information like the RMI port and address to use.
	 */
	private RmiUtils(){
		Log4JUtils log = Log4JUtils.getInstance();
		
		String strPort = PropertyUtils.getInstance().getProperty("iCrash.port", String.valueOf(PORT));
		String strPortClient = PropertyUtils.getInstance().getProperty("iCrashClient.port", String.valueOf(PORTCLIENT));
		try{
			PORT = Integer.parseInt(strPort);
			PORTCLIENT = Integer.parseInt(strPortClient);
		} catch (NumberFormatException e){
			log.getLogger().error("Unable to parse port number");
			log.getLogger().error(e.getMessage());
		}			
		HOST = PropertyUtils.getInstance().getProperty("iCrash.host", HOST);
		HOSTCLIENT = PropertyUtils.getInstance().getProperty("iCrashClient.host", HOSTCLIENT);
		
	}
	
	/** The instance of the class. */
	private static RmiUtils instance;
	
	/**
	 * Gets the single instance of RmiUtils.
	 *
	 * @return single instance of RmiUtils
	 */
	public static RmiUtils  getInstance(){
		if (instance == null)
			instance = new RmiUtils();
		return instance;
	}
	
	/** The Constant host address of the server. */
	private String HOST = "localhost"; 
	/** The Constant host address of the client. */
	private String HOSTCLIENT = "localhost";
	
	/** The Constant port number of the server. */
	private int PORT = 1099;
	
	/**
	 * The constant port number of the client
	 */
	private int PORTCLIENT = 1098;
	
	/**
	 * Gets the host address set by the system, this is read from the properties file or it uses the default specified here.
	 *
	 * @return the host address to use
	 */
	public String getHost(){
		return HOST;
	}
	
	public String getHostClient(){
		return HOSTCLIENT;
	}
	
	/**
	 * Gets the port number set by the system, this is read from the properties file or it uses the default specified here.
	 *
	 * @return the port number to use
	 */
	public int getPort(){
		return PORT;
	}
	/**
	 * gets the port that the client should use for RMI connections
	 * @return An integer number of the port to use
	 */
	public int getPortClient(){
		return PORTCLIENT;
	}

	
}
