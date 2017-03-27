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

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Interface ActCoordinator that allows the client to access the server via RMI.
 */
public interface ActCoordinator extends ActAuthenticated {

	/**
	 * Requests a crisis to be sent to the coordinator with the same status as the one provided.
	 *
	 * @param aEtCrisisStatus The crises with this status type will be sent to the requesting actor
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeGetCrisisSet(EtCrisisStatus aEtCrisisStatus) throws RemoteException, NotBoundException;
	
	/**
	 * Requests an alert to be sent to the coordinator with the same status as the one provided.
	 *
	 * @param aEtAlertStatus The alert with this status type will be sent
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public PtBoolean oeGetAlertsSet(EtAlertStatus aEtAlertStatus) throws RemoteException, NotBoundException;
	
	/**
	 * Sets the crisis' handler, with the ID passed, as the current user.
	 *
	 * @param aDtCrisisID The ID of the crisis to change the handler of
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public PtBoolean oeSetCrisisHandler(DtCrisisID aDtCrisisID) throws RemoteException, NotBoundException;
	
	/**
	 * Validates the alert with the same ID as the one passed.
	 *
	 * @param aDtAlertID The ID to use to find the alert to validate
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public PtBoolean oeValidateAlert(DtAlertID aDtAlertID) throws RemoteException, NotBoundException;
	
	/**
	 * Invalidates the alert with the same ID as the one passed.
	 *
	 * @param aDtAlertID The ID to use to find the alert to invalidate
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public PtBoolean oeInvalidateAlert(DtAlertID aDtAlertID) throws RemoteException, NotBoundException;
	
	/**
	 * Sets the crisis' status, with the ID passed, to the status passed.
	 *
	 * @param aDtCrisisID The ID of the crisis to change the status of
	 * @param aEtCrisisStatus The status to change the status of the crisis to
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public PtBoolean oeSetCrisisStatus(DtCrisisID aDtCrisisID, EtCrisisStatus aEtCrisisStatus) throws RemoteException, NotBoundException;
	
	/**
	 * Sets the crisis' type, with the ID passed, to the type passed.
	 *
	 * @param aDtCrisisID The ID of the crisis to change the status of
	 * @param aEtCrisisType The type to set the crisis to
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public PtBoolean oeSetCrisisType(DtCrisisID aDtCrisisID, EtCrisisType aEtCrisisType) throws RemoteException, NotBoundException;
	
	/**
	 * Sets the crisis' comment, with the ID passed, to the comment passed.
	 *
	 * @param aDtCrisisID The ID of the crisis to change the status of
	 * @param aDtComment The comment to set the new comment to
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public PtBoolean oeReportOnCrisis(DtCrisisID aDtCrisisID,DtComment aDtComment) throws RemoteException, NotBoundException;
	
	/**
	 * Sets the crisis' to be closed, with the ID passed.
	 *
	 * @param aDtCrisisID The ID of the crisis to change the status of
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public PtBoolean oeCloseCrisis(DtCrisisID aDtCrisisID) throws RemoteException, NotBoundException;

	/**
	 * A message and crisis is received by the user.
	 *
	 * @param aCtCrisis The crisis received by the user
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieSendACrisis(CtCrisis aCtCrisis) throws RemoteException;
	
	/**
	 * A message and alert is received by the user.
	 *
	 * @param aCtAlert The alert received by the user
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieSendAnAlert(CtAlert aCtAlert) throws RemoteException;

}