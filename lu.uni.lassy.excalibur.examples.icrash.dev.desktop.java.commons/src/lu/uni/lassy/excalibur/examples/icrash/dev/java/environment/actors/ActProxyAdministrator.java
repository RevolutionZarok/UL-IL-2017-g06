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

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Interface ActProxyAdministrator.
 */
public interface ActProxyAdministrator extends ActProxyAuthenticated {
	
	/**
	 * Add a coordinator to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when creating the coordinator
	 * @param aDtLogin The username to use when creating the coordinator
	 * @param aDtPassword The password to use when creating the coordinator
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeAddCoordinator(DtCoordinatorID aDtCoordinatorID, DtLogin aDtLogin,DtPassword aDtPassword) throws RemoteException, NotBoundException;
	
	/**
	 * Delete a coordinator to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when looking for the coordinator to delete
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID) throws RemoteException, NotBoundException;
	
	/**
	 * A message received from the server side actor saying the coordinator was created .
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorAdded() throws RemoteException;
	
	/**
	 * A message received from the server side actor saying the coordinator was deleted.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorDeleted() throws RemoteException;
	
	/**
	 * A message received from the server side actor saying the coordinator was updated.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorUpdated() throws RemoteException;
	
	
}
