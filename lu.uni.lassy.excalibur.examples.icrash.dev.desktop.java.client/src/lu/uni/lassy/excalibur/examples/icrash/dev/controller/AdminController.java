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
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated.UserType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtStatisticNumberofCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtStatisticUserActivity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtMailAddress;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtStatisticNumberOfCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtStatisticUserActivity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Server;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyAdministratorImpl;

/**
 * The Class AdminController, used to do functions that an admin can only do.
 */
public class AdminController extends AbstractUserController {
	
	/**
	 * Instantiates a new admin controller.
	 *
	 * @param aActAdmin the a act admin
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound in the RMI properties
	 */
	public AdminController(ActAdministrator aActAdmin) throws RemoteException, NotBoundException{	
		super(new ActProxyAdministratorImpl(aActAdmin));
	}
	/**
	 * If an administrator is logged in, will send an addCoordinator request to the server. If successful, it will return a PtBoolean of true
	 * @param coordinatorID The ID of the coordinator to create, the user specifies the ID, not the system in the creation process
	 * @param login The logon of the user to create. This is the username they will use at point of logon at the client front end
	 * @param password The password of the user to create. This is the password they will use at point of logon at the client front end
	 * @return Returns a PtBoolean true if the user was created, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeAddCoordinator(String coordinatorID, String login, String password, String mail) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtCoordinatorID aDtCoordinatorID = new DtCoordinatorID(new PtString(coordinatorID));
			DtLogin aDtLogin = new DtLogin(new PtString(login));
			DtPassword aDtPassword = new DtPassword(new PtString(password));
			DtMailAddress aDtMail = new DtMailAddress(new PtString(mail));
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aDtCoordinatorID, aDtCoordinatorID.value.getValue());
			ht.put(aDtLogin, aDtLogin.value.getValue());
			ht.put(aDtPassword, aDtPassword.value.getValue());
			ht.put(aDtMail, aDtMail.value.getValue());
			try {
				return actorAdmin.oeAddCoordinator(aDtCoordinatorID, aDtLogin, aDtPassword, aDtMail);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	/**
	 * If an administrator is logged in, will send a deleteCoordinator request to the server. If successful, it will return a PtBoolean of true
	 * @param coordinatorID The ID of the coordinator to delete
	 * @return Returns a PtBoolean true if the user was deleted, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeDeleteCoordinator(String coordinatorID) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtCoordinatorID aDtCoordinatorID = new DtCoordinatorID(new PtString(coordinatorID));
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aDtCoordinatorID, aDtCoordinatorID.value.getValue());
			if (!aDtCoordinatorID.is().getValue())
				return new PtBoolean(false);
			try {
				return actorAdmin.oeDeleteCoordinator(aDtCoordinatorID);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/** Parameter that allows the system to gain server access, the server function lives in the model of the client and  has RMI calls to access the server. */
	private Server server = Server.getInstance();
	
	/**
	 * Returns a list of all statistic for the number of sending crises  in the system, with using a logged in user.
	 *
	 * @return Returns an ArrayList of type CtStatisticUserActivity, which contains all statistic for the number of number of user login currently within the iCrashSystem
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ArrayList<CtStatisticUserActivity> getListOfStatisticUserLogin() throws ServerOfflineException, ServerNotBoundException{
		try {
			return server.sys().getStatisticUserLogin();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Returns a list of all statistic for the number of sending crises  in the system, with using a logged in user.
	 *
	 * @return Returns an ArrayList of type CtStatisticNumberofCrises, which contains all statistic for the number of sending crises currently within the iCrashSystem
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ArrayList<CtStatisticNumberofCrises> getListOfStatisticNumberofsendingCrises() throws ServerOfflineException, ServerNotBoundException{
		try {
			return server.sys().getStatisticNumberofCrises();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	// TODO Not finish for the moment has now the basic but nothing for the statistic. 
	// For the Function Statistic
	public PtBoolean oegetStatistic()throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			
			try {
			return actorAdmin.oegetStatistic();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	//TODO define a valuer for int so i must give something with this function. 
	public PtBoolean oegetStatisticUserActivity(int statisticNumber)throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		System.out.println("www11");
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtStatisticUserActivity aDtgetstatisticUserActivity = new DtStatisticUserActivity(new PtInteger(statisticNumber));
			Hashtable<JIntIs, Integer> ht = new Hashtable<JIntIs, Integer>();
			ht.put(aDtgetstatisticUserActivity, aDtgetstatisticUserActivity.value.getValue());
			try {
			return actorAdmin.oegetStatisticUserActivity(aDtgetstatisticUserActivity);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	//TODO
	public PtBoolean oegetStatisticNumberOfCrises(int statisticNumber)throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		System.out.println("www");
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtStatisticNumberOfCrises aDtStatisticNumberOfCrises= new DtStatisticNumberOfCrises(new PtInteger(statisticNumber));
			Hashtable<JIntIs, Integer> ht = new Hashtable<JIntIs, Integer>();
			ht.put(aDtStatisticNumberOfCrises, aDtStatisticNumberOfCrises.value.getValue());
			try {
			return actorAdmin.oegetStatisticNumberOfCrises(aDtStatisticNumberOfCrises);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	//TODO make a time value not a int because is for the average time. 
	public PtBoolean oegetStatisticTypes()throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			try {
			return actorAdmin.oegetStatistic();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	
}


