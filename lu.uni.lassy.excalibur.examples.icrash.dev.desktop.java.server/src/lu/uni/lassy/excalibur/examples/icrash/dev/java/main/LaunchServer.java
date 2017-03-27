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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.main;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironmentImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystemImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbInitialize;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

/**
 * The Class LaunchServer, that launches the server, opens the ports and sets the other RMI options required for connection.
 */
public class LaunchServer {

	/** The logger to use to show messages in the console window if there are messages. */
	static Logger log = Log4JUtils.getInstance().getLogger();
	/**
	 * Instantiates a new server for the system.
	 */
	public LaunchServer(File mysqlScript){
		try{
			log.info("*************************************************");
			//http://stackoverflow.com/a/11225707/833070
			System.setProperty("java.rmi.server.hostname", RmiUtils.getInstance().getHost());
			log.info("--- Registry created in port " + RmiUtils.getInstance().getPort());
	    	LocateRegistry.createRegistry(RmiUtils.getInstance().getPort());
			log.info("--- Registry located in host [" + RmiUtils.getInstance().getHost() + "] and port [" +  RmiUtils.getInstance().getPort() + "]");
		    Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(), RmiUtils.getInstance().getPort());
			log.info("*************************************************");
			log.info("--- Create iCrashSys_Server Remote Object");
			IcrashSystem iCrashSys_Server_RO = IcrashSystemImpl.getInstance(RmiUtils.getInstance().getPort());
			log.info("--- Bind iCrashSys_Server Remote Object");
			registry.rebind("iCrashServer", iCrashSys_Server_RO);
	    	log.info("--- ICrash Server ready and running ...");
			log.info("*************************************************");
			log.info("--- Create Environment Remote Object");
			IcrashEnvironment iCrashEnvironment_RO = IcrashEnvironmentImpl.getInstance();
			log.info("--- Bind Environment Remote Object");
			registry.rebind("iCrashEnvironment", iCrashEnvironment_RO);
	    	log.info("--- Environment Remote Object ready and running ...");
	    	log.info("*************************************************");
	  		if(mysqlScript!=null){  	
		    	log.info("*************************************************");
		    	DbInitialize.initializeDatabase(mysqlScript);
		    	log.info("--- Database initialized ...");
		    	log.info("*************************************************");
	  		}
	    	
		} catch (Exception e) {
	       log.error("Troubles when launching the ICrash Server: " + e);
	     }
	}

	/**
	 * The main method that allows starting of the server.
	 *
	 * @param args The arguments passed via a user, we do not use them
	 */
	public static void main(String[] args) {
	
			 if(args.length > 0) {
           			 File file = new File(args[0]);	
           			  new LaunchServer(file);
			 }else
	    	  	new LaunchServer(null);
	    	  
	}

}
