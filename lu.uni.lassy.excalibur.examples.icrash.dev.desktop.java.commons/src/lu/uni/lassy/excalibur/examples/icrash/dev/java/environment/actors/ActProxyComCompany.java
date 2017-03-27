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

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.secondary.DtSMS;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Interface ActProxyComCompany that allows RMI access to the proxy actor on the client side.
 */
public interface ActProxyComCompany extends Remote, JIntIsActor {

	/**
	 * Gets the name of the communication company assigned to this actor.
	 *
	 * @return The name of the communication company assigned to this actor
	 * @throws RemoteException Thrown if the server is offline
	 */
	public String getName() throws RemoteException;
	
	/**
	 * Contacts the server actor and initiates an alert creation.
	 *
	 * @param aEtHumanKind The type of human reporting the accident
	 * @param aDtDate The date of the accident
	 * @param aDtTime The time of the accident
	 * @param aDtPhoneNumber The phone number of the human associated with the accident
	 * @param aDtGPSLocation The location of the accident
	 * @param aDtComment The message sent by the human associated with the accident
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server is not bound correctly in RMI settings
	 */
	public PtBoolean oeAlert(EtHumanKind aEtHumanKind,DtDate aDtDate,
			DtTime aDtTime,DtPhoneNumber aDtPhoneNumber,
			DtGPSLocation aDtGPSLocation,DtComment aDtComment) throws RemoteException, NotBoundException;

	/**
	 * Receives a message from the server side actor with details of if the alert was logged or not.
	 *
	 * @param aDtPhoneNumber The phone number of the human associated with the accident
	 * @param aDtSMS The message sent to the human
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieSmsSend(DtPhoneNumber aDtPhoneNumber,DtSMS aDtSMS) throws RemoteException;
	
}
