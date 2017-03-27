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
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Interface ActAuthenticated that allows RMI access to it's methods.
 */
public interface ActAuthenticated extends java.rmi.Remote, Serializable, JIntIsActor {

	/**
	 * Gets the username of user associated with this actor.
	 *
	 * @return the username of the associated user
	 * @throws RemoteException Thrown if the server isn't online
	 */
	public DtLogin getLogin() throws RemoteException;	
	
	/**
	 * Allows a user to logon to the system.
	 *
	 * @param aDtLogin The username to logon with
	 * @param aDtPassword The password to logon with
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server isn't online
	 * @throws NotBoundException Thrown if the server has not been bound in the RMI settings
	 */
	public PtBoolean oeLogin(DtLogin aDtLogin,DtPassword aDtPassword) throws RemoteException, NotBoundException;
	
	/**
	 * Allows a user to logoff to the system.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server isn't online
	 * @throws NotBoundException Thrown if the server has not been bound in the RMI settings
	 */
	public PtBoolean oeLogout() throws RemoteException, NotBoundException;
	
	/**
	 * Adds the proxy actor as a listener to this class.
	 *
	 * @param aActProxyAuthenticated The proxy actor to add as a listener to this class
	 * @throws RemoteException Thrown if the server isn't online
	 * @throws NotBoundException Thrown if the server has not been bound in the RMI settings
	 */
	public void addListener(ActProxyAuthenticated aActProxyAuthenticated) throws RemoteException, NotBoundException;
	
	/**
	 * Removes the proxy actor as a listener from this class.
	 *
	 * @param aActProxyAuthenticated The proxy actor to remove as a listener from this class
	 * @throws RemoteException Thrown if the server isn't online
	 * @throws NotBoundException Thrown if the server has not been bound in the RMI settings
	 */
	public void removeListener(ActProxyAuthenticated aActProxyAuthenticated) throws RemoteException, NotBoundException;
	
	/**
	 * Sends a message to all listeners of this class.
	 *
	 * @param aMessage The message to send to the listeners
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server isn't online
	 */
	public PtBoolean ieMessage(PtString aMessage) throws RemoteException;
}