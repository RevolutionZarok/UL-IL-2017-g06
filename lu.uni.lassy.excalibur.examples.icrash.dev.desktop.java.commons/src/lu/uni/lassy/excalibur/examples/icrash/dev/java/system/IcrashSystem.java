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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtState;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtStatisticNumberofCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtStatisticUserActivity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtFamilyComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtMailAddress;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtStatisticNumberOfCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtStatisticUserActivity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtResetCode;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtVictimFirstName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtVictimLastName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Interface IcrashSystem that allows RMI access to the server methods.
 */
public interface IcrashSystem extends Remote {

	/*
	 * ********************************
	 * New implementation operations 
	 * *********************************.
	 */
	/**
	 * Do test.
	 *
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void doTest() throws RemoteException;

	/**
	 * Sets the current requesting authenticated actor on the server. This is the current user accessing the server and doing functions.
	 * This is called by the actor themselves and must be set before calling a modification method
	 *
	 * @param aActAuthenticated the new current requesting authenticated actor
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void setCurrentRequestingAuthenticatedActor(ActAuthenticated aActAuthenticated) throws RemoteException;
	
	/**
	 * Gets the current requesting authenticated actor that was set by the server side actor.
	 *
	 * @return the current requesting authenticated actor
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActAuthenticated getCurrentRequestingAuthenticatedActor() throws RemoteException;
	
	/**
	 * Sets the current connected communication company, this is set by the actor calling the method.
	 *
	 * @param aComCompany The actor communication company that is currently calling the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void setCurrentConnectedComCompany(ActComCompany aComCompany) throws RemoteException; 
	
	/**
	 * Gets the class type of the system state, contains things like the current system date and time.
	 *
	 * @return the class type state
	 * @throws RemoteException Thrown if the server is offline
	 */
	public CtState getCtState() throws RemoteException; 
	
	/**
	 * Gets a random class type coordinator from the system.
	 *
	 * @return the random class type coordinator that has been selected
	 * @throws RemoteException Thrown if the server is offline
	 */
	public CtCoordinator getRandomCtCoordinator() throws RemoteException; 
	
	/**
	 * Gets the actor coordinator with the same class type coordinator passed.
	 *
	 * @param keyCtCoordinator the class type coordinator to use to retrieve the associated actor coordinator
	 * @return the actor coordinator retrieved from the system
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActCoordinator getActCoordinator(CtCoordinator keyCtCoordinator) throws RemoteException; 
	
	/**
	 * Binds the class type crisis to the class type coordinator.
	 *
	 * @param aCtCrisis the class type crisis to bind a coordinator to
	 * @param aCtCoordinator the a class type coordinator to bind to the crisis
	 * @throws RemoteException Thrown if the server is offline
	 */
	public void bindCtCrisisCtCoordinator(CtCrisis aCtCrisis,CtCoordinator aCtCoordinator) throws RemoteException; 
    
    /**
     * Gets all of the actor administrators in the system.
     *
     * @return all of the actor administrators in the system
     * @throws RemoteException Thrown if the server is offline
     */
    public List<ActAdministrator> getAllActAdministrators() throws RemoteException;
    
    /**
     * Gets all of the class type administrators in the system.
     *
     * @return all of the class type administrators in the system
     * @throws RemoteException Thrown if the server is offline
     */
    public ArrayList<CtAdministrator> getAllCtAdministrators() throws RemoteException;
	
	/**
	 * Gets the actor communication company from the system associated with the class type human passed.
	 *
	 * @param aHuman The class type human to use to retrieve the communication with
	 * @return the actor communication company from the server who is associated to the human passed
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActComCompany getActComCompany(CtHuman aHuman) throws RemoteException; 
	
	/**
	 * Gets the hashtable of all authenticated class types in the system.
	 *
	 * @return the hashtable of all the authenticated class types in the system
	 * @throws RemoteException Thrown if the server is offline
	 */
	public Hashtable<String, CtAuthenticated> getCmpSystemCtAuthenticated() throws RemoteException; 
	
	/**
	 * Gets a list of all class type coordinators in the system.
	 *
	 * @return all of the class type coordinators
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ArrayList<CtCoordinator> getAllCtCoordinators() throws RemoteException;
	
	/**
	 * Gets a list of all class types alerts in the system.
	 *
	 * @return a list of all class type alerts
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ArrayList <CtAlert> getAllCtAlerts() throws RemoteException;
	
	/**
	 * Gets a list of all class type humans in the system.
	 *
	 * @return A list of all class type humans
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ArrayList <CtHuman> getAllCtHumans() throws RemoteException;
	
	/**
	 * Gets a list of all crises in the system.
	 *
	 * @return A list of all the crises in the system
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ArrayList<CtCrisis> getAllCtCrises() throws RemoteException;
	
	//TODO
	/**
	 * Gets a list of all crises in the system.
	 *
	 * @return A list of all the crises in the system
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ArrayList<CtStatisticUserActivity> getAllCtStatisticUserActivity() throws RemoteException;
	
	/**
	 * Gets a list of all communication companies in the system.
	 *
	 * @return A list of all communication companies in the system
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ArrayList<ActComCompany> getAllActComCompanies() throws RemoteException;
	
	/**
	 * Get a list of all statistic for the number of user logins
	 * @return A list of all statistic for the number of user logins in the system
	 * @throws RemoteException
	 */
	public ArrayList<CtStatisticUserActivity> getStatisticUserLogin() throws RemoteException;
	/**
	 * Get a list of all statistic for the number of sending crises
	 * @return A list of all statistic for the number of sending crises in the system
	 * @throws RemoteException
	 */
	public ArrayList<CtStatisticNumberofCrises> getStatisticNumberofCrises() throws RemoteException;
	
	/**
	 * Gets the specified communication company with the name specified.
	 *
	 * @param name The name of the company to retrieve
	 * @return If an actor communication company was found, send it back. Otherwise retun null
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActComCompany getActComCompany(String name) throws RemoteException;
	
	/*
	 * ************************
	 * System operations 
	 * ************************.
	 */
	/**
	 * Creates the system and environment on the system.
	 *
	 * @param aQtyComCompanies The quantity of communication companies to create. This can only be a maximum of 4, if you want more, you need to add more to the Enumeration ComCompaniesInLux
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeCreateSystemAndEnvironment(PtInteger aQtyComCompanies) throws RemoteException; 

	/**
	 * Creates an alert in the system using the parameters passed
	 * Uses the actor communication company for this method.
	 *
	 * @param aEtHumanKind The kind of human reporting the accident
	 * @param aDtDate The date of the accident
	 * @param aDtTime The time of the accident
	 * @param aDtPhoneNumber The phone number of the human reporting the accident
	 * @param aDtGPSLocation The location of the accident
	 * @param aDtComment The message sent by the user
	 * @param aDtVictimLastName 
	 * @param aDtVictimFirstName 
	 * @param aDtFamilyComment 
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeAlert(EtHumanKind aEtHumanKind,DtDate aDtDate,
				DtTime aDtTime,DtPhoneNumber aDtPhoneNumber,DtGPSLocation aDtGPSLocation,DtComment aDtComment, DtFamilyComment aDtFamilyComment, DtVictimFirstName aDtVictimFirstName, DtVictimLastName aDtVictimLastName) throws RemoteException; 

	/**
	 * Validates an alert on the system
	 * Uses the coordinator actor to do this function.
	 *
	 * @param aDtAlertID The ID of the alert to validate
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeValidateAlert(DtAlertID aDtAlertID) throws RemoteException; 

	public PtString oeSendFamilyNotification(DtAlertID aDtAlertID) throws RemoteException;
	
	/**
	 * Validates an alert on the system
	 * Uses the coordinator actor to do this function.
	 *
	 * @param aDtAlertID The ID of the alert to invalidate
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeInvalidateAlert(DtAlertID aDtAlertID) throws RemoteException; 
	
	/**
	 * Sets the crisis type to one passed.
	 *
	 * @param aDtCrisisID The ID of the crisis to change
	 * @param aEtCrisisType The type to change the crisis to
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeSetCrisisType(DtCrisisID aDtCrisisID, EtCrisisType aEtCrisisType) throws RemoteException; 
	
	/**
	 * Sets the crisis status to the one passed.
	 *
	 * @param aDtCrisisID The ID of the crisis to change
	 * @param aEtCrisisStatus The status to change the crisis to
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeSetCrisisStatus(DtCrisisID aDtCrisisID, EtCrisisStatus aEtCrisisStatus) throws RemoteException; 		
	
	/**
	 * Sets the current authenticating actor to be handling the crisis.
	 *
	 * @param aDtCrisisID The ID of the crisis to change
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeSetCrisisHandler(DtCrisisID aDtCrisisID) throws RemoteException; 
	
	/**
	 * Sets the report on the crisis to be the one of the comment passed.
	 *
	 * @param aDtCrisisID The ID of the crisis to change
	 * @param aDtComment The new report to set in the crisis
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeReportOnCrisis(DtCrisisID aDtCrisisID, DtComment aDtComment) throws RemoteException; 
	
	/**
	 * Gets the crisis with the status specified.
	 *
	 * @param aEtCrisisStatus The status to filter the crisis by
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeGetCrisisSet(EtCrisisStatus aEtCrisisStatus) throws RemoteException; 
	
	/**
	 * Gets the alerts with the status specified.
	 *
	 * @param aEtAlertStatus The status to filter the alerts by
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeGetAlertsSet(EtAlertStatus aEtAlertStatus) throws RemoteException; 
	
	/**
	 * Closes the crisis specified.
	 *
	 * @param aDtCrisisID The ID of the crisis to change
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeCloseCrisis(DtCrisisID aDtCrisisID) throws RemoteException; 
	
	
	/**
	 * Processes a login for the username and password specified.
	 *
	 * @param aDtLogin The username to login with
	 * @param aDtPassword The password to login with
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeLogin(DtLogin aDtLogin,DtPassword aDtPassword) throws RemoteException; 
	
	/**
	 * Processes a logout for the current authenticating actor.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeLogout() throws RemoteException; 
	
	/**
	 * Adds a coordinator with the details specified.
	 *
	 * @param aDtCoordinatorID The ID to create the coordinator with
	 * @param aDtLogin The username of the coordinator to create
	 * @param aDtPassword The password of the coordinator to create
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeAddCoordinator(DtCoordinatorID aDtCoordinatorID,DtLogin aDtLogin,DtPassword aDtPassword,DtMailAddress aMail) throws RemoteException;
	public PtBoolean oeUpdateCoordinator(DtCoordinatorID aDtCoordinatorID,DtLogin aDtLogin,DtPassword aDtPassword,DtMailAddress aMail,PtBoolean aLocked,DtResetCode aResetCode) throws java.rmi.RemoteException;
	
	/**
	 * Deletes a coordinator with the details specified.
	 *
	 * @param aDtCoordinatorID The coordiantor ID to delete from the system
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID) throws RemoteException; 
	
	/**
	 * 
	 * @param aDtStatisticUserActivity
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oegetStatisticUserActivity(DtStatisticUserActivity aDtStatisticUserActivity) throws RemoteException;
	/**
	 * 
	 * @param aDtStatisticNumberOfCrises
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oegetStatisticNumberOfCrises(DtStatisticNumberOfCrises aDtStatisticNumberOfCrises)throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oegetStatisticTypes()throws RemoteException;
	
	/**
	 * Runs the function of sollicitating crisis handling, if a crisis hasn't been handled with the delay, coordinators will be warned. If it passes max delax, it will be auto assigned out 
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeSollicitateCrisisHandling() throws RemoteException; 
	
	/**
	 * Sets the date and time of the server to the one passed.
	 *
	 * @param aCurrentClock The date and time to set the system to
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean oeSetClock(DtDateAndTime aCurrentClock) throws RemoteException; 
	
	public PtBoolean oeSendCaptcha(CtCaptcha aCaptcha) throws RemoteException, NotBoundException;

	public PtBoolean oeTryPasswordReset(DtLogin aLogin, DtResetCode aResetCode, DtPassword aNewPwd) throws RemoteException, NotBoundException;

	public PtBoolean oeSendResetCodePerMail(DtLogin aLogin) throws RemoteException, NotBoundException;//TODO: Messir S4 (Post conditions too)
	
	public void setCurrentRequestingAuthenticatedLogin(DtLogin aDtLogin) throws RemoteException;
	public void setCurrentRequestingAuthenticatedPassword(DtPassword aDtPassword) throws RemoteException;
	public DtLogin getCurrentRequestingAuthenticatedLogin() throws RemoteException;
	public DtPassword getCurrentRequestingAuthenticatedPassword() throws RemoteException;

}
