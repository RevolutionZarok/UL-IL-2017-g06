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

import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActActivator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActMsrCreator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtState;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Server;

/**
 * The Class SystemStateController which controls setting up the initial environment on the server, updating the system time and running crisis handling functions.
 */
public class SystemStateController {
	
	/** 
	 * Parameter that allows the system to gain server access, the server function lives in the model of the client and  has RMI calls to access the server. */
	private Server server = Server.getInstance();
	
	/**
	 * Will tell the server to create the system and environment. This is only called by the Admin window, it has to be ran first
	 *
	 * @param numberOfComCompanies the number of communication companies to create on the server
	 * @return Returns if the creation of the system and environment was successful or not
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public PtBoolean createSystemAnEnviroment(PtInteger numberOfComCompanies)throws ServerOfflineException, ServerNotBoundException{
		ActMsrCreator theCreator;
		try {
			IcrashEnvironment env = server.env();
			Log4JUtils.getInstance().getLogger().debug("Getting creator actor");
			theCreator = env.getActMsrCreator();
			Log4JUtils.getInstance().getLogger().debug("Running oeCreateSystemAndEnvironment");
			return theCreator.oeCreateSystemAndEnvironment(numberOfComCompanies);
		}
		catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Gets the server date time.
	 *
	 * @return the date and time of the server
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public DtDateAndTime getServerDateTime() throws ServerOfflineException, ServerNotBoundException{
		try {
			return server.sys().getCtState().clock;
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Gets the date and time of the last crisis handling from the server.
	 *
	 * @return the date and time of the last crisis handling
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public DtDateAndTime getLastCrisisHandling() throws ServerOfflineException, ServerNotBoundException{	 
		try {
			return server.sys().getCtState().vpLastReminder;
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	/**
	 * Sets the clock of the server to the date and time specified.
	 *
	 * @param datetimeToSet the datetime to set
	 * @return the pt boolean
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public PtBoolean oeSetClock(DtDateAndTime datetimeToSet) throws ServerOfflineException, ServerNotBoundException{
		try {
			return getActActivator().oeSetClock(datetimeToSet);
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Closes the server connection that is open at the moment.
	 */
	public void closeServerConnection(){
		server.disconnectServer();
	}
	
	/**
	 * Gets the single actor administrator from the server that was created once the system and environment was initiated.
	 *
	 * @return The actor administrator from the server
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ActAdministrator getActAdministrator() throws ServerOfflineException, ServerNotBoundException{
		try {
			return server.env().getAdministrators().values().stream().findFirst().get();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Gets the act coordinator.
	 *
	 * @param userName the user name
	 * @return the act coordinator
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 */
	public ActCoordinator getActCoordinator(String userName) throws ServerNotBoundException, ServerOfflineException{
		try {
			return server.env().getActCoordinator(userName);
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Gets all the actor communication company in the server.
	 *
	 * @return A list of all the actor communication companies in the system
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 */
	public ArrayList<ActComCompany> getActComCompany() throws ServerNotBoundException, ServerOfflineException{
		try {
			ArrayList<ActComCompany> listToReturn = new ArrayList<ActComCompany>();
			listToReturn.addAll(server.env().getActComCompanies().values());
			return listToReturn;
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Gets the act activator.
	 *
	 * @return the act activator
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ActActivator getActActivator() throws ServerOfflineException, ServerNotBoundException{
		try {
			return server.env().getActActivator();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	/**
	 * Gets an ArrayList of all CtCoordinators in the system.
	 *
	 * @return An ArrayList of all CtCoordinators from the server
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ArrayList<CtCoordinator> getAllCoordinators() throws ServerOfflineException, ServerNotBoundException{
		try {
			return server.sys().getAllCtCoordinators();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	/**
	 * Gets an ArrayList of all CtAdministrators in the system.
	 *
	 * @return An ArrayList of all CtAdministrators from the server
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ArrayList<CtAdministrator> getAllAdministrators() throws ServerOfflineException, ServerNotBoundException{
		try{
			return server.sys().getAllCtAdministrators();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	/**
	 * Returns a list of the names of all communication companies  in the system.
	 *
	 * @return Returns an ArrayList of type String, which contains the names of all communication companies currently within the iCrashSystem
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ArrayList<String> getListOfComCompaniesNames() throws ServerOfflineException, ServerNotBoundException{
		ArrayList<String> result = new ArrayList<String>();
		try {
			for(ActComCompany comp : server.env().getActComCompanies().values())
					result.add(comp.getName());
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		return result;
	}
	
	/**
	 * Gets the class type of the server state, this include things like the current server date and time, the last time a reminder was ran and so on.
	 *
	 * @return The class type of the server state
	 * @throws ServerOfflineException Thrown if the server is offline
	 * @throws ServerNotBoundException Thrown if the server was not bound correctly in the RMI settings
	 */
	public CtState getServerState() throws ServerOfflineException, ServerNotBoundException{
		try {
			return server.sys().getCtState();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
}
