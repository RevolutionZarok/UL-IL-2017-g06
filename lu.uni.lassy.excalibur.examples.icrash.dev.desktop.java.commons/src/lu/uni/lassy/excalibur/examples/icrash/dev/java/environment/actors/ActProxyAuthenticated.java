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
import java.rmi.Remote;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntHasServerSideActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Interface ActProxyAuthenticated that allows remote usage via RMI.
 * BE CAREFUL, this must extend remote otherwise the system falls over
 * http://stackoverflow.com/questions/7472338/java-rmi-call-with-remote-parameters-fails
 */
public interface ActProxyAuthenticated extends Remote, JIntHasServerSideActor, JIntIsActor {
	/**
	 * Gets the username of the user associated with this actor.
	 *
	 * @return DtLogin associated with this actor
	 * @throws RemoteException Thrown if the server is offline
	 */
	public DtLogin getLogin() throws RemoteException;
	/**
	 * Performs the oeLogin function with the username and password provided.
	 *
	 * @param aDtLogin The username to logon with
	 * @param aDtPassword The password to logon with
	 * @return The success of the operation
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeLogin(DtLogin aDtLogin, DtPassword aDtPassword)
			throws RemoteException, NotBoundException;

	/**
	 * Performs the oeLogut function with the current user.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeLogout() throws RemoteException, NotBoundException;

	/**
	 * Recieves a message to the actor, this must be displayed on the user's GUI screen as well.
	 *
	 * @param aMessage The message sent to the actor
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieMessage(PtString aMessage) throws RemoteException;
	
	/**
	 * An enum of different user types, makes it easier to read code where you can check the type rather than having to call instance of to check the actor type.
	 */
	public enum UserType{
		
		/** The actor Admin. */
		Admin,
		
		/** The actor Coordinator. */
		Coordinator,
		
		/** The actor Communication company. */
		ComCompany,
		
		/** The Unknown actor. */
		Unknown,
		
		/** The No actor defined. */
		None
	}
	
	/**
	 * Gets the user type of this actor. It uses instanceof to work out the current user type
	 *
	 * @return the user type
	 * @throws RemoteException the remote exception
	 */
	public UserType getUserType() throws RemoteException;
	
}
