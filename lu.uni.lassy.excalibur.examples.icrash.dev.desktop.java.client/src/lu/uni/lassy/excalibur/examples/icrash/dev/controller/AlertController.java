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
package lu.uni.lassy.excalibur.examples.icrash.dev.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Server;

/**
 * The alert controller deals with any functions to do with alerts. This includes creation, retrieval,
 * handling and so on. It extends the AbstractController to handle checking of Dt information is correct
 */
public class AlertController {
	
	/** Parameter that allows the system to gain server access, the server function lives in the model of the client and  has RMI calls to access the server. */
	private Server server = Server.getInstance();
	
	/**
	 * Returns a list of all alerts in the system, without using a logged in user.
	 *
	 * @return Returns an ArrayList of type CtAlert, which contains all alerts currently within the iCrashSystem
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ArrayList<CtAlert> getListOfAlerts() throws ServerOfflineException, ServerNotBoundException{
		try {
			return server.sys().getAllCtAlerts();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Returns a list of all alerts in the system that match the status type specified, without using a logged in user.
	 *
	 * @param statusOfAlert the status of alert
	 * @return Returns an ArrayList of type CtAlert, which contains all alerts currently within the iCrashSystem
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ArrayList<CtAlert> getListOfAlerts(EtAlertStatus statusOfAlert) throws ServerOfflineException, ServerNotBoundException{
		return (ArrayList<CtAlert>)getListOfAlerts().stream().filter(t -> t.status == statusOfAlert).collect(Collectors.toList());
	}	
	/**
	 * Closes the server connection that is open at the moment.
	 */
	public void closeServerConnection(){
		server.disconnectServer();
	}
}
