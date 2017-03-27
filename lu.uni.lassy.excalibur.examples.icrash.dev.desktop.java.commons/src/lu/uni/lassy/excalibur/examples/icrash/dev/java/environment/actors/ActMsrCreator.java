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
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;

/**
 * The Interface ActMsrCreator, which has a method for creating system and environment.
 */
public interface ActMsrCreator extends java.rmi.Remote, Serializable, JIntIsActor {

	/**
	 * Create system and environment.
	 *
	 * @param aQtyComCompanies The quantity of Communication companies to create within the system. The maximum number is defined by the list in ComCompaniesInLux in the utils package
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server was not bound correctly in the RMI settings
	 */
	public PtBoolean oeCreateSystemAndEnvironment(PtInteger aQtyComCompanies)
			throws RemoteException, NotBoundException;

}
