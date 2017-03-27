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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

import org.apache.log4j.Logger;

/**
 * A test case that was generated that tests if the server is up and running.
 */
public class TestCase_server_alive {

	/**  The logger that is used to print text to the command line/shell. */
	static Logger log = Log4JUtils.getInstance().getLogger();
	
	/**
	 * The main method for running the test case.
	 *
	 * @param args the arguments passed to the system
	 * @throws RemoteException If the server is offline, a Remote Exception will be thrown
	 * @throws NotBoundException If the server has not been bound, a Not Bound Exception will be thrown
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {

		//Step 1
		log.info("----Step 0-------");

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(), RmiUtils.getInstance().getPort());
		log.info("get registry");
		 	
		//Gathering the remote object as it was published into the registry
        IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		
		
		iCrashSys_Server.doTest();
		log.info("getting the echo...");

	}

}
