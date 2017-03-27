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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

import org.apache.log4j.Logger;

/**
 * The Class ActMsrCreatorImpl, that implements an actor of type MSR creator.
 */
public class ActMsrCreatorImpl extends UnicastRemoteObject implements ActMsrCreator {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/**
	 * Instantiates a new actor of type MSR creator. This actor is used to create the system and environment
	 *
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActMsrCreatorImpl() throws RemoteException {
		super(RmiUtils.getInstance().getPort());
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActMsrCreator#oeCreateSystemAndEnvironment(lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger)
	 */
	public PtBoolean oeCreateSystemAndEnvironment(PtInteger aQtyComCompanies) throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();
			
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
  
		log.info("get registry");
		 	
		//Gathering the remote object as it was published into the registry
        IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
        
		
		log.info("message ActMsrCreator.oeCreateSystemAndEnvironment sent to system");
		PtBoolean res = iCrashSys_Server.oeCreateSystemAndEnvironment(aQtyComCompanies);
		
		
		if(res.getValue() == true)
			log.info("operation oeCreateSystemAndEnvironment successfully executed by the system");
	
		return new PtBoolean(true);
	}		
}
