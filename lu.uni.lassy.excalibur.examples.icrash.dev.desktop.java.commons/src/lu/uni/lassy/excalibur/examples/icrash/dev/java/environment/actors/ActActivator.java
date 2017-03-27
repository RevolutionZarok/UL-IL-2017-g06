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

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Interface ActActivator.
 */
public interface ActActivator extends Remote, Serializable, JIntIsActor{

	/**
	 * The method to set the server's date and time.
	 *
	 * @param aDtDateAndTime The date and time to set
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server hasn't been bound in the RMI properties
	 */
	public PtBoolean oeSetClock(DtDateAndTime aDtDateAndTime)
			throws RemoteException, NotBoundException;
}
