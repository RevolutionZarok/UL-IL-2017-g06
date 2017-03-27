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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActActivator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActMsrCreator;

/**
 * The Interface IcrashEnvironment, which makes the methods public for the RMI access.
 */
public interface IcrashEnvironment  extends Remote {

	/**
	 * Do test runs a test to confirm the environment is up and working.
	 * It doesn't do much, but I've left it in
	 *
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void doTest() throws RemoteException;

	
	
	/**
	 * Puts an actor administrator into a hashtable containing all current system admins.
	 *
	 * @param keyName The key of which to store the actor by, keeps this the same as that you use the get actor method. Currently we use the login name as the key
	 * @param aActAdministrator The actor administrator to place in the hashtable
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void setActAdministrator(String keyName,ActAdministrator aActAdministrator) throws RemoteException;
	
	/**
	 * Gets the act administrator from the hashtable using the key provided.
	 *
	 * @param keyName The key name to use when retrieving from the hashtable
	 * @return The actor retrieved from the database, can be null if one does not exist within the hashtable with the correct key
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActAdministrator getActAdministrator(String keyName) throws RemoteException;

	/**
	 * Gets all of the administrators that have been stored in the hashtable.
	 *
	 * @return The hashtable of all the administrators in the hashtable
	 * @throws RemoteException Thrown if the server is offline
	 */
	public Hashtable<String, ActAdministrator> getAdministrators() throws RemoteException;

	/**
	 * Sets the actor activator for the system, there can only be one in the entire system.
	 *
	 * @param aActActivator the new actor activator for the system
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void setActActivator(ActActivator aActActivator) throws RemoteException;
	
	/**
	 * Gets the actor activator set for the system.
	 *
	 * @return the actor activator set in the system
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActActivator getActActivator() throws RemoteException;

	/**
	 * Puts a communication company into the hashtable containing all communication companies in the system.
	 *
	 * @param keyName The key of which to store the actor by, keeps this the same as that you use the get actor method. Currently we use the name of the company as the key
	 * @param aActComCompany the actor communication company to store
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void setComCompany(String keyName, ActComCompany aActComCompany) throws RemoteException;
	
	/**
	 * Gets a communication company from the hashtable using the key value provided.
	 *
	 * @param keyName The name of the communication company to retrieve
	 * @return The communication company found, could be null if none is found
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActComCompany getComCompany(String keyName) throws RemoteException;
	
	/**
	 * Puts an actor coordinator into the hashtable of the system.
	 *
	 * @param keyName The key of which to store the actor by, keeps this the same as that you use the get actor method. Currently we use the login name as the key
	 * @param aActCoordinator The actor coordinator stored in the hashtable
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void setActCoordinator(String keyName, ActCoordinator aActCoordinator) throws RemoteException;
	
	/**
	 * Gets the actor coordinator from the hashtable using the key value provided.
	 *
	 * @param keyName The login name of the coordinator to retrieve
	 * @return The actor retrieved from the database, can be null if one does not exist within the hashtable with the correct key
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActCoordinator getActCoordinator(String keyName) throws RemoteException;

	/**
	 * Gets the entire hashtable of actor communication companies in the system.
	 *
	 * @return The hashtable of communication companies
	 * @throws RemoteException Thrown if the server is offline
	 */
	public Hashtable<String, ActComCompany> getActComCompanies() throws RemoteException;
	
	/**
	 * Gets the actor MSR creator from the system, this is created at moment of initiation.
	 *
	 * @return the actor MSR creator
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActMsrCreator getActMsrCreator() throws RemoteException;
	
	/**
	 * Gets the authenticated actor from the system with the name specified.
	 *
	 * @param keyName Name of the actor to retrieve
	 * @return An authenticated actor, if found, otherwise a null
	 * @throws RemoteException the remote exception
	 */
	public ActAuthenticated getActAuthenticated(String keyName) throws RemoteException;
}
