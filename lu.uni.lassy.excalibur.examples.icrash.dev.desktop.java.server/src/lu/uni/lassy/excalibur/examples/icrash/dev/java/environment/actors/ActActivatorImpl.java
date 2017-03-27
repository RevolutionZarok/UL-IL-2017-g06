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
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

import org.apache.log4j.Logger;

/**
 * The Class ActActivatorImpl, it's the actor that implements the server side Activator.
 */
public class ActActivatorImpl extends UnicastRemoteObject implements
		ActActivator {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new server side actor activator implementation.
	 *
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActActivatorImpl() throws RemoteException {
		super(RmiUtils.getInstance().getPort());
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActActivator#oeSetClock(lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime)
	 */
	public PtBoolean oeSetClock(DtDateAndTime aDtDateAndTime)
			throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		log.info("message ActActivator.oeSetClock sent to system");
		PtBoolean res = iCrashSys_Server.oeSetClock(aDtDateAndTime);

		if (res.getValue() == true)
			log.info("operation oeSetClock successfully executed by the system");

		return res;
	}
}
