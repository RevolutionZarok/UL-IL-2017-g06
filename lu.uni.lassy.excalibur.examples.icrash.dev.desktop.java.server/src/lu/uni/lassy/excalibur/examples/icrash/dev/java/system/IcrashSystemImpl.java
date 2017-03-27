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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActActivator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActActivatorImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministratorImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompanyImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinatorImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbAlerts;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbComCompanies;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbCoordinators;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbHumans;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtState;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.secondary.DtSMS;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.AdminActors;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.PropertyUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;
import org.apache.log4j.Logger;

/**
 * The implementation of the iCrash system.
 */
public class IcrashSystemImpl extends UnicastRemoteObject implements
		IcrashSystem {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**  The The state of the current system. */
	CtState ctState = new CtState();

	/** The current requesting authenticated actor that is performing a method on the system.
	 * This is set by the Actor itself before it performs an oe action*/
	ActAuthenticated currentRequestingAuthenticatedActor;
	
	/** The current connected communication company that is performing a method on the system.
	 * This is set by the Actor itself before it performs an oe action*/
	ActComCompany currentConnectedComCompany;

	// Messir compositions
	/**  A hashtable of all administrators in the system, stored by their login as a key. */
	Hashtable<String, CtAdministrator> cmpSystemCtAdministrator = new Hashtable<String, CtAdministrator>();
	
	/**  A hashtable of all authenticated users in the system, stored by their login as a key. */
	Hashtable<String, CtAuthenticated> cmpSystemCtAuthenticated = new Hashtable<String, CtAuthenticated>();
	
	/**  A hashtable of the alerts in the system, stored by their ID as a key. */
	Hashtable<String, CtAlert> cmpSystemCtAlert = new Hashtable<String, CtAlert>();
	
	/**  A hashtable of the crises in the system, stored by their ID as a key. */
	Hashtable<String, CtCrisis> cmpSystemCtCrisis = new Hashtable<String, CtCrisis>();
	
	/**  A hashtable of the humans in the system, stored by their phone number as a key. */
	Hashtable<String, CtHuman> cmpSystemCtHuman = new Hashtable<String, CtHuman>();
	
	/**  A hashtable of the actor com companies in the system, stored by their name as a key. */
	Hashtable<String, ActComCompany> cmpSystemActComCompany = new Hashtable<String, ActComCompany>();

	// Messir associations	
	/**  A hashtable of the joint alerts and crises in the system, stored by their alert as a key. */
	Hashtable<CtAlert, CtCrisis> assCtAlertCtCrisis = new Hashtable<CtAlert, CtCrisis>();
	
	/**  A hashtable of the joint alerts and humans in the system, stored by their alert as a key. */
	Hashtable<CtAlert, CtHuman> assCtAlertCtHuman = new Hashtable<CtAlert, CtHuman>();
	
	/**  A hashtable of the joint class types and actors in the system, stored by their class type as a key. */
	Hashtable<CtAuthenticated, ActAuthenticated> assCtAuthenticatedActAuthenticated = new Hashtable<CtAuthenticated, ActAuthenticated>();
	
	/**  A hashtable of the joint class coordinators and actors coordiantors in the system, stored by their class type. */
	Hashtable<CtCoordinator, ActCoordinator> assCtCoordinatorActCoordinator = new Hashtable<CtCoordinator, ActCoordinator>();
	
	/**  A hashtable of the joint crises and coordinators in the system, stored by their crisis as a key. */
	Hashtable<CtCrisis, CtCoordinator> assCtCrisisCtCoordinator = new Hashtable<CtCrisis, CtCoordinator>();
	
	/**  A hashtable of the joint humans and Actor com companies in the system, stored by the human as a key. */
	Hashtable<CtHuman, ActComCompany> assCtHumanActComCompany = new Hashtable<CtHuman, ActComCompany>();
	
	/** The logger user by the system to print information to the console. */
	private Logger log = Log4JUtils.getInstance().getLogger();
	
	/*
	 * ********************************
	 * Internal operations 
	 * *********************************.
	 */
	 /**
	 * Gets the alerts by crisis.
	 *
	 * @param aCtCrisis The crisis to use to search for the associated alert
	 * @return The alert(s) that are associated with crisis provided
	 */
	private List<CtAlert> getAlertsByCrisis(CtCrisis aCtCrisis) {

		List<CtAlert> listAlerts = new ArrayList<CtAlert>();

		for (CtAlert ctAlert : assCtAlertCtCrisis.keySet()) {
			if (assCtAlertCtCrisis.get(ctAlert).id.value.getValue().equals(
					aCtCrisis.id.value.getValue()))
				listAlerts.add(ctAlert);
		}

		return listAlerts;
	}

	/**
	 * Gets the class Authenticated (Of a coordinator type) that has the associated ID provided.
	 *
	 * @param aDtCoordinatorID The ID to use to search for the user
	 * @return the class Authenticated that was associated with the ID provided, if none found then return a null
	 */
	public CtAuthenticated getCtCoordinator(DtCoordinatorID aDtCoordinatorID) {

		for (CtAuthenticated ctAuth : assCtAuthenticatedActAuthenticated
				.keySet()) {
			if (ctAuth instanceof CtCoordinator) {
				PtBoolean res = ((CtCoordinator) ctAuth).id
						.eq(aDtCoordinatorID);
				if (res.getValue())
					return ctAuth;
			}
		}
		return null;
	}

	/**
	 * Gets the class Authenticated that has the associated Actor provided.
	 *
	 * @param aActAuthenticated The actor to use to search for the class Authenticated
	 * @return the class Authenticated that was associated with the Actor provided, if none found then return a null
	 */
	private CtAuthenticated getCtAuthenticated(
			ActAuthenticated aActAuthenticated) {
		try {
			String ActAuthname = aActAuthenticated.getLogin().value.getValue();
			for (CtAuthenticated ctAuth : assCtAuthenticatedActAuthenticated
					.keySet()) {
				ActAuthenticated currActAuth = assCtAuthenticatedActAuthenticated
						.get(ctAuth);
				if (currActAuth.getLogin().value.getValue().equals(ActAuthname) && !currActAuth.getLogin().value.getValue().isEmpty())
					return ctAuth;
			}
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
		}
		return null;
	}
	
	/**
	 * A standard check to see if the system is started, if not then an exception is thrown to be caught by the checking method.
	 *
	 * @throws Exception The exception with a simple text description of the reason for failure
	 */
	private void isSystemStarted() throws Exception {
		if (!ctState.vpStarted.getValue()){
			throw new Exception("The system is not started");
		}
	}
	
	/**
	 * A standard check to see if the current actor requesting to do an action on the server is logged in and also an admin.
	 *
	 * @throws Exception The exception with a simple text description of the reason for failure
	 */
	private void isAdminLoggedIn() throws Exception {
		
	}
	/**
	 * A standard check to see if the current actor requesting to do an action on the server is logged in.
	 *
	 * @throws Exception The exception with a simple text description of the reason for failure
	 */
	private void isUserLoggedIn() throws Exception{
		DtLogin user = currentRequestingAuthenticatedActor.getLogin();
		if (user != null && user.value != null){
			CtAuthenticated ctAuthenticatedInstance = cmpSystemCtAuthenticated
					.get(user.value.getValue());
			if (ctAuthenticatedInstance == null || !ctAuthenticatedInstance.vpIsLogged.getValue())
				throw new Exception("The user " + user.value.getValue() + " is not logged in");
		}
		else
			throw new Exception("The user does not exist");
	}
	
	/**
	 * If an exception is thrown inside a lambda stream, it must be caught during the actual stream. This is a method to catch said issue
	 * and then propagate it up to the calling method. See here for more information: http://stackoverflow.com/questions/19757300/java-8-lambda-streams-filter-by-method-with-exception
	 *
	 * @param <V> the value type
	 * @param callable the lambda stream that will cause the exception. It's called like this a -&gt; propagate(a::isActive) where isActive is a method inside the class of a
	 * @return Returns the result if completed, otherwise it will throw the exception up to the calling method
	 */
    private static <V> V propagate(Callable<V> callable){
        try {
            return callable.call();
        } catch (Exception e) {
            throw runtime(e);
        }
    }
	
	/**
	 * Creates a wrapper around an exception to prevent Java compile errors when attempting to catch an exception inside a lambda expression.
	 *
	 * @param e The exception to throw to the surrounding method
	 * @return Returns a surrounding runtimeexception for the exception of e
	 */
    private static RuntimeException runtime(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException)e;
        }
        return new RuntimeException(e);
    }
	
	/*
	 * ********************************
	 * New implementation operations 
	 * *********************************.
	 */
    
	/**
	 * A test method proving the server is up and running.
	 *
	 * @throws RemoteException If the server is not alive, this exception will be thrown
	 */
	public void doTest() throws java.rmi.RemoteException {
		System.out.println("I'm alive and reacheable boy!");
	}
	
	/**  The instance of the iCrashSystem   Eager singleton pattern. */
	private static volatile IcrashSystem instance = null;

	/**
	 * Instantiates a new iCrash system implementation.
	 * Constructor of the class
	 * @throws RemoteException If the server is not alive, this exception will be thrown
	 */
	public IcrashSystemImpl(int portNumber) throws RemoteException {
		super(portNumber);
	}

	/**
	 * Gets the single instance of the iCrash system implementation.
	 *
	 * @return single instance of the iCrash system implementation
	 * @throws RemoteException If the server is not alive, this exception will be thrown
	 */
	public static IcrashSystem getInstance(int portNumber) throws RemoteException {
		if (instance == null)
			instance = new IcrashSystemImpl(portNumber);

		return instance;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#setCurrentRequestingAuthenticatedActor(lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated)
	 */
	synchronized public void setCurrentRequestingAuthenticatedActor(
			ActAuthenticated aActAuthenticated) throws RemoteException {
		currentRequestingAuthenticatedActor = aActAuthenticated;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getCurrentRequestingAuthenticatedActor()
	 */
	synchronized public ActAuthenticated getCurrentRequestingAuthenticatedActor(){
		return currentRequestingAuthenticatedActor;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#setCurrentConnectedComCompany(lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany)
	 */
	synchronized public void setCurrentConnectedComCompany(ActComCompany aComCompany)
			throws RemoteException {
		currentConnectedComCompany = aComCompany;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getCtState()
	 */
	public CtState getCtState() throws RemoteException {
		return ctState;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getRandomCtCoordinator()
	 */
	public CtCoordinator getRandomCtCoordinator() throws RemoteException {
		List<CtCoordinator> collCtCoor = assCtCoordinatorActCoordinator.keySet().stream().filter(t -> t != null).collect(Collectors.toList());
		int max = collCtCoor.size();
		int min = 0;
		int randomNum = 0;

		if (max > 1) {
			Random rand = new Random();
			// nextInt is exclusive of max, and inclusive of min,
			randomNum = rand.nextInt(max - min) + min;
		}
		if (max != 0){
			return collCtCoor.get(randomNum);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getActCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator)
	 */
	public ActCoordinator getActCoordinator(CtCoordinator keyCtCoordinator)
			throws RemoteException {
		return assCtCoordinatorActCoordinator.get(keyCtCoordinator);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#bindCtCrisisCtCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator)
	 */
	public void bindCtCrisisCtCoordinator(CtCrisis aCtCrisis,
			CtCoordinator aCtCoordinator) throws RemoteException {
		assCtCrisisCtCoordinator.put(aCtCrisis, aCtCoordinator);
		DbCrises.bindCrisisCoordinator(aCtCrisis, aCtCoordinator);
		DbCrises.updateCrisis(aCtCrisis);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getAllActAdministrators()
	 */
	public List<ActAdministrator> getAllActAdministrators()
			throws RemoteException {
		List<ActAdministrator> listAdmins = new ArrayList<ActAdministrator>();
		for (CtAuthenticated ctAuth : assCtAuthenticatedActAuthenticated
				.keySet()) {
			if (ctAuth instanceof CtAdministrator)
				listAdmins
						.add((ActAdministratorImpl) assCtAuthenticatedActAuthenticated
								.get(ctAuth));
		}
		return listAdmins;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getAllCtAdministrators()
	 */
	public ArrayList<CtAdministrator> getAllCtAdministrators() throws RemoteException{
		ArrayList<CtAdministrator> listAdmins = new ArrayList<CtAdministrator>();
		for (CtAuthenticated ctAuth : cmpSystemCtAuthenticated
				.values()) {
			if (ctAuth instanceof CtAdministrator)
				listAdmins.add((CtAdministrator)ctAuth);
		}
		return listAdmins;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getActComCompany(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman)
	 */
	public ActComCompany getActComCompany(CtHuman aHuman)
			throws RemoteException {
		return assCtHumanActComCompany.get(aHuman);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getCmpSystemCtAuthenticated()
	 */
	public Hashtable<String, CtAuthenticated> getCmpSystemCtAuthenticated()
			throws RemoteException {
		return cmpSystemCtAuthenticated;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getAllCtCoordinators()
	 */
	public ArrayList<CtCoordinator> getAllCtCoordinators() throws RemoteException {
		ArrayList<CtCoordinator> result = new ArrayList<CtCoordinator>();
		for (CtAuthenticated auth : cmpSystemCtAuthenticated.values())
			if (auth instanceof CtCoordinator)
				result.add((CtCoordinator) auth);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getAllCtAlerts()
	 */
	public ArrayList<CtAlert> getAllCtAlerts() throws RemoteException {
		ArrayList<CtAlert> result = new ArrayList<CtAlert>();
		if (cmpSystemCtAlert != null){
			for(CtAlert alert : cmpSystemCtAlert.values())
				result.add(alert);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getAllCtHumans()
	 */
	public ArrayList <CtHuman> getAllCtHumans() throws java.rmi.RemoteException{
		ArrayList<CtHuman> result = new ArrayList<CtHuman>();
		if (cmpSystemCtHuman != null){
			for(CtHuman human : cmpSystemCtHuman.values())
				result.add(human);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getAllCtCrises()
	 */
	public ArrayList<CtCrisis> getAllCtCrises() throws java.rmi.RemoteException{
		ArrayList<CtCrisis> result = new ArrayList<CtCrisis>();
		if (cmpSystemCtCrisis != null){
			for(CtCrisis crisis : cmpSystemCtCrisis.values())
				result.add(crisis);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getAllActComCompanies()
	 */
	public ArrayList<ActComCompany> getAllActComCompanies() throws java.rmi.RemoteException{
		ArrayList<ActComCompany> result = new ArrayList<ActComCompany>();
		if (cmpSystemActComCompany != null){
			for(ActComCompany comComp : cmpSystemActComCompany.values())
				result.add(comComp);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#getActComCompany(java.lang.String)
	 */
	public ActComCompany getActComCompany(String name) throws java.rmi.RemoteException{
		return cmpSystemActComCompany.get(name);
	}
	
	/*
	 * ************************
	 * System operations 
	 * ************************.
	 */
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeCreateSystemAndEnvironment(lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger)
	 */
	public PtBoolean oeCreateSystemAndEnvironment(PtInteger aQtyComCompanies)
			throws RemoteException {
		try {
			log.debug("in IcrashSystemimpl.oeCreateSystemAndEnvironment...");

			/*
			 * 	PostF 1:
			 * 
			 *  the ctState instance is initialised with
			 *  the integer 1 for the crisis and alert counters used for their	identifications,
			 *  clock = current time 
			 *  the crisis reminder period is set to 300 seconds, 
			 *  the maximum crisis reminder period	is fixed to 1200 seconds (i.e. 20 minutes) and 
			 *  the system is considered in a started state.
			 *  aVpLastReminder = current time
			 *  Those predicates must be satisfied first since all the other depend on the existence of a
			 *  ctState instance !
			 */
			DtDateAndTime aClock = ICrashUtils.getMinimumDateAndTime();
			DtSecond aCrisisReminderPeriod = new DtSecond(new PtInteger(300));
			DtSecond aMaxCrisisReminderPeriod = new DtSecond(
					new PtInteger(1200));
			try{
				String strReminderPeriod = PropertyUtils.getInstance().getProperty("iCrash.reminderPeriod", String.valueOf(300));
				String strMaxReminderPeriod = PropertyUtils.getInstance().getProperty("iCrash.maxReminderPeriod", String.valueOf(1200));
				aCrisisReminderPeriod = new DtSecond(new PtInteger(Integer.parseInt(strReminderPeriod)));
				aMaxCrisisReminderPeriod = new DtSecond(new PtInteger(Integer.parseInt(strMaxReminderPeriod)));			
				DtDate aDtDate = ICrashUtils.stringToDtDate(PropertyUtils.getInstance().getProperty("iCrash.date", ICrashUtils.getMinimumDateAndTime().date.toString()));
				DtTime aDtTime = ICrashUtils.stringToDtTime(PropertyUtils.getInstance().getProperty("iCrash.time", ICrashUtils.getMinimumDateAndTime().time.toString()));
				aClock = new DtDateAndTime(aDtDate, aDtTime);
			} catch(Exception e){
				log.error("Error reading properties file");
				log.error(e.getMessage());
			}
			int nextValueForAlertID = DbAlerts.getMaxAlertID() + 1;
			DtInteger aNextValueForAlertID = new DtInteger(new PtInteger(
					nextValueForAlertID));
			int nextValueForCrisisID = DbCrises.getMaxCrisisID() + 1;
			DtInteger aNextValueForCrisisID = new DtInteger(new PtInteger(
					nextValueForCrisisID));
			PtBoolean aVpStarted = new PtBoolean(true);
			ctState.init(aNextValueForAlertID, aNextValueForCrisisID, aClock,
					aCrisisReminderPeriod, aMaxCrisisReminderPeriod, aClock,
					aVpStarted);
			/* ENV
			PostF 2 the actMsrCreator actor instance is initiated (remember that since the
			oeCreateSystemAndEnvironment is a special event, its role is to make consistent the post
			state, thus creating the actor and its interfaces is required even though the sending 
			of this message	logically would need the actor and its interfaces to already exist ...).
			*/
			//implementation done at the level of the init method caller 		

			/*	ENV
			PostF 3 the environment for communication company actors, in the post state, is made of
			AqtyComCompanies instances allowing for receiving and sending messages to humans.
			*/
			Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(), RmiUtils.getInstance().getPort());
			IcrashEnvironment env = (IcrashEnvironment) registry
					.lookup("iCrashEnvironment");
			for (int i = 1; i <= aQtyComCompanies.getValue(); i++) {
				ActComCompany aActComCompany = new ActComCompanyImpl(i);
				if (DbComCompanies.getComCompanyID(aActComCompany.getName()) == "")
					DbComCompanies.insertComCompany(Integer.toString(i), aActComCompany.getName());
				env.setComCompany(aActComCompany.getName(), aActComCompany);
				this.cmpSystemActComCompany.put(aActComCompany.getName(), aActComCompany);
			}
			/*	ENV
			PostF 4 the environment for administrator actors, in the post state, is made of one instance.
			*/
			String adminName = AdminActors.values[0].name();
			ActAdministrator aActAdministrator = new ActAdministratorImpl(new DtLogin(new PtString(adminName)));
			env.setActAdministrator(adminName, aActAdministrator);
			/* ENV
			PostF 5 the environment for activator actors, in the post state, is made of one instance allowing for automatic
			message sending based on current system’s and environment state’.
			*/
			ActActivator aActActivator = new ActActivatorImpl();
			env.setActActivator(aActActivator);
			/*
			PostF 6 the set of ctAdministrator instances at post is made of one instance initialized with 
			’icrashadmin’ (resp. ’7WXC1359’) for login (resp. password) values.
			*/
			CtAdministrator ctAdmin = new CtAdministrator();
			DtLogin aLogin = new DtLogin(new PtString(adminName));
			DtPassword aPwd = new DtPassword(new PtString("7WXC1359"));
			ctAdmin.init(aLogin, aPwd);
			/*
			PostF 7 the association between ctAdministrator and actAdministrator is made of 
			one couple made of the jointly specified instances.
			*/
			assCtAuthenticatedActAuthenticated.put(ctAdmin, aActAdministrator);
			
			//set up Messir compositions		
			cmpSystemCtAdministrator.put(ctAdmin.login.value.toString(),
					ctAdmin);
			cmpSystemCtAuthenticated.put(ctAdmin.login.value.getValue(),
					ctAdmin);
			// initialise relationships taking information from the DB
			cmpSystemCtAlert = DbAlerts.getSystemAlerts();
			cmpSystemCtCrisis = DbCrises.getSystemCrises();
			cmpSystemCtHuman = DbHumans.getSystemHumans();
			Hashtable<String, CtCoordinator> cmpSystemCtCoordinator = DbCoordinators.getSystemCoordinators();
			for(CtCoordinator ctCoord: cmpSystemCtCoordinator.values()){
				cmpSystemCtAuthenticated.put(ctCoord.login.value.getValue(), ctCoord);
				ActCoordinator actCoord = new ActCoordinatorImpl(ctCoord.login);
				env.setActCoordinator(ctCoord.login.value.getValue(), actCoord);
				assCtAuthenticatedActAuthenticated.put(ctCoord, actCoord);
				assCtCoordinatorActCoordinator.put(ctCoord, actCoord);
			}
			assCtAlertCtCrisis = DbAlerts.getAssCtAlertCtCrisis();
			assCtAlertCtHuman = DbAlerts.getAssCtAlertCtHuman();
			assCtCrisisCtCoordinator = DbCrises.getAssCtCrisisCtCoordinator();
			assCtHumanActComCompany = DbHumans.getAssCtHumanActComCompany(env.getActComCompanies());
			/*
			*Creating a thread to auto check if handling delay has passed and if so run oeSollicitateCrisisHandling 
			*/
			Thread checkingForDelayPassed = new Thread(){
				public void run(){
					while(true){
						try{
							hasHandlingDelayPassed();
							log.debug("Something has passed handling delay, running oeSollicitateCrisisHandling");
							oeSollicitateCrisisHandling();
							Thread.sleep(10000);
						}
						catch (Exception e){
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e1) {
								//DO NOTHING
							}
						}
					}
				}
			};
			checkingForDelayPassed.start();
		} catch (Exception ex) {
			log.error("Exception when trying to reach Environment..." + ex);
		}
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeAlert(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind, lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate, lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment)
	 */
	public  synchronized PtBoolean oeAlert(EtHumanKind aEtHumanKind, DtDate aDtDate,
			DtTime aDtTime, DtPhoneNumber aDtPhoneNumber,
			DtGPSLocation aDtGPSLocation, DtComment aDtComment)
			throws RemoteException {
		try{
			//PreP1
			isSystemStarted();
			DtDateAndTime aInstant = new DtDateAndTime(aDtDate, aDtTime);
			int nextValueForAlertID_at_pre = ctState.nextValueForAlertID.value
					.getValue();
			int nextValueForCrisisID_at_pre = ctState.nextValueForCrisisID.value
					.getValue();
			//PostF1				
			ctState.nextValueForAlertID.value = new PtInteger(
					ctState.nextValueForAlertID.value.getValue() + 1);
	
			//PostF2
			CtAlert aCtAlert = new CtAlert();
			DtAlertID aId = new DtAlertID(new PtString(""
					+ nextValueForAlertID_at_pre));
			EtAlertStatus aStatus = EtAlertStatus.pending;
			aCtAlert.init(aId, aStatus, aDtGPSLocation, aInstant, aDtComment);
			//DB: insert alert in the database
			DbAlerts.insertAlert(aCtAlert);
	
			//PostF3
			boolean existsNear = false;
			CtCrisis aCtCrisis = new CtCrisis();
			//check if there already exists a reported Alert that is closer than 100 m. 
			for (CtAlert existingCtAlert : assCtAlertCtCrisis.keySet()) {
				existsNear = existingCtAlert.location.isNearTo(
						aDtGPSLocation.latitude, aDtGPSLocation.longitude)
						.getValue();
				if (existsNear) {
					aCtCrisis = assCtAlertCtCrisis.get(existingCtAlert);
					break;
				}
			}
	
			//if there no exits a near alert, then we need to initialise the just created crisis instance
			if (!existsNear) {
				DtCrisisID acId = new DtCrisisID(new PtString(""
						+ nextValueForCrisisID_at_pre));
				ctState.nextValueForCrisisID.value = new PtInteger(
						ctState.nextValueForCrisisID.value.getValue() + 1);
				EtCrisisType acType = EtCrisisType.small;
				EtCrisisStatus acStatus = EtCrisisStatus.pending;
				DtComment acComment = new DtComment(new PtString(
						"no report defined, yet"));
				aCtCrisis.init(acId, acType, acStatus, aDtGPSLocation, aInstant,
						acComment);
	
				//DB: insert crisis in the database
				DbCrises.insertCrisis(aCtCrisis);
				
				//update Messir composition
				cmpSystemCtCrisis.put(aCtCrisis.id.value.getValue(), aCtCrisis);
	
			}
	
			//PostF4
			assCtAlertCtCrisis.put(aCtAlert, aCtCrisis);
			//DB: update just inserted alert with its corresponding associated (near) crisis
			DbAlerts.bindAlertCrisis(aCtAlert, aCtCrisis);
	
			//PostF5
			CtHuman aCtHuman = new CtHuman();
			boolean existsHuman = false;
	
			//check if there already exists a human who reported an Alert 
			for (CtHuman existingHuman : assCtHumanActComCompany.keySet()) {
				String exPhoneNumber = existingHuman.id.value.getValue();
				if (exPhoneNumber.equals(aDtPhoneNumber.value.getValue())) {
					aCtHuman = existingHuman;
					existsHuman = true;
					break;
				}
			}
	
			//if there no exists human, then we need (1) to initialise the just created instance
			// and (2) to add it to the assCtHumanActComCompany relationship 
			if (!existsHuman) {
				aCtHuman.init(aDtPhoneNumber, aEtHumanKind);
				assCtHumanActComCompany.put(aCtHuman, currentConnectedComCompany);
	
				//update Messir composition
				cmpSystemCtHuman.put(aCtHuman.id.value.getValue(), aCtHuman);
	
				//DB: get currentConnectedComCompany's id
				String idComCompany = DbComCompanies
						.getComCompanyID(currentConnectedComCompany.getName());
	
				//DB: insert human in the database
				DbHumans.insertHuman(aCtHuman, idComCompany);
	
			}
	
			//PostF6
			DtSMS sms = new DtSMS(new PtString(	"Your alert has been registered. We will handle it and keep you informed."));
			currentConnectedComCompany.ieSmsSend(aDtPhoneNumber, sms);
			//bind human with alert
			assCtAlertCtHuman.put(aCtAlert, aCtHuman);
			//DB: update just inserted alert with reporting human
			DbAlerts.bindAlertHuman(aCtAlert, aCtHuman);
			//update Messir composition
			cmpSystemCtAlert.put(aCtAlert.id.value.getValue(), aCtAlert);
			return new PtBoolean(true);
		}
		catch(Exception e){
			log.error("Exception in oeAlert..." + e);
		}
		return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeValidateAlert(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID)
	 */
	//actCoordinator Actor
	public PtBoolean oeValidateAlert(DtAlertID aDtAlertID)
			throws RemoteException {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			CtAlert theAlert = cmpSystemCtAlert
					.get(aDtAlertID.value.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				//PostF1
				theAlert.status = EtAlertStatus.valid;
				DbAlerts.updateAlert(theAlert);
				PtString aMessage = new PtString("The alert "
						//+ "with ID '"
						//+ aDtAlertID.value.getValue()
						//+ "' "
						+ "is now declared as valid !");
				theActCoordinator.ieMessage(aMessage);
				return new PtBoolean(true);
			}
		}
		catch(Exception e){
			log.error("Exception in oeAlert..." + e);
		}
		return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeInvalidateAlert(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID)
	 */
	public PtBoolean oeInvalidateAlert(DtAlertID aDtAlertID)
			throws RemoteException {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			CtAlert theAlert = cmpSystemCtAlert
					.get(aDtAlertID.value.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				//PostF1
				theAlert.status = EtAlertStatus.invalid;
				DbAlerts.updateAlert(theAlert);
				PtString aMessage = new PtString("The alert "
						//+ "with ID '"
						//+ aDtAlertID.value.getValue()
						//+ "' "
						+ "is now declared as invalid !");
				theActCoordinator.ieMessage(aMessage);
	
				return new PtBoolean(true);
			}
		}
		catch(Exception e){
			log.error("Exception in oeAlert..." + e);
		}
		return new PtBoolean(false);
	}
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeSetCrisisType(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType)
	 */
	public synchronized PtBoolean oeSetCrisisType(DtCrisisID aDtCrisisID, EtCrisisType aEtCrisisType) throws RemoteException {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value
					.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				//PostF1
				theCrisis.type = aEtCrisisType;
				DbCrises.updateCrisis(theCrisis);
				PtString aMessage = new PtString("The crisis with ID '"
						+ aDtCrisisID.value.getValue() + "' is now of type '"
						+ aEtCrisisType.toString() + "' !");
				theActCoordinator.ieMessage(aMessage);
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeSetCrisisType..." + e);
		}
		return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeSetCrisisStatus(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus)
	 */
	public  synchronized PtBoolean oeSetCrisisStatus(DtCrisisID aDtCrisisID,
			EtCrisisStatus aEtCrisisStatus) throws RemoteException {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value
					.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				//PostF1
				theCrisis.status = aEtCrisisStatus;
				DbCrises.updateCrisis(theCrisis);
				PtString aMessage = new PtString("The crisis status has been updated !");
				theActCoordinator.ieMessage(aMessage);
	
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeSetCrisisStatus..." + e);
		}
		return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeSetCrisisHandler(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID)
	 */
	public synchronized  PtBoolean oeSetCrisisHandler(DtCrisisID aDtCrisisID)
			throws RemoteException {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value
					.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				CtCoordinator theCtCoordinator = (CtCoordinator) getCtAuthenticated(theActCoordinator);
				
				log.debug("theCrisis Instance is " + theCrisis.toString());
				log.debug("aDtCrisisID.value.getValue() is "
						+ aDtCrisisID.value.getValue());
	
				CtCoordinator theCtCoordinatorAtPre = assCtCrisisCtCoordinator
						.get(theCrisis);
				if (theCtCoordinatorAtPre == null)
					log.debug("theCtCoordinatorAtPre is none");
				else
						log.debug("theCtCoordinatorAtPre is "
								+ assCtCrisisCtCoordinator.get(theCrisis).toString());
	
				//PostF1
				theCrisis.status = EtCrisisStatus.handled;
				DbCrises.updateCrisis(theCrisis);
				
				assCtCrisisCtCoordinator.put(theCrisis, theCtCoordinator);
				DbCrises.bindCrisisCoordinator(theCrisis, theCtCoordinator);
				PtString aMessage = new PtString(
						"You are now considered as handling the crisis !");
				theActCoordinator.ieMessage(aMessage);
	
				//PostF2
				for (CtAlert theAlert : getAlertsByCrisis(theCrisis)) {
					theAlert.isSentToCoordinator(theActCoordinator);
				}
				//PostF3
				if (theCtCoordinatorAtPre != null) {
					ActCoordinator theActCoordinatorAtPre = (ActCoordinator) assCtAuthenticatedActAuthenticated
							.get(theCtCoordinatorAtPre);
					log.info("One of the crisis you were handling is now handled by one of your colleagues!");
					PtString aMessage2 = new PtString(
							"One of the crisis you were handling is now handled by one of your colleagues!");
					theActCoordinatorAtPre.ieMessage(aMessage2);
				}
	
				//PostF4
				for (CtAlert theAlert : getAlertsByCrisis(theCrisis))
				{
					Enumeration<CtAlert> enumKey = assCtAlertCtHuman.keys();
					while(enumKey.hasMoreElements()){
						CtAlert aCtAlert = enumKey.nextElement();
						if (aCtAlert.equals(theAlert)){
							CtHuman theHuman = assCtAlertCtHuman.get(aCtAlert);
							if (!theHuman.isAcknowledged().getValue())
								log.error("Unable to message a communication company about the crisis update");
						}
					}
				}
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeSetCrisisHandler..." + e);
		}

		return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeReportOnCrisis(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment)
	 */
	public  synchronized PtBoolean oeReportOnCrisis(DtCrisisID aDtCrisisID,
			DtComment aDtComment) {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value
					.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				//PostF1
				theCrisis.comment = aDtComment;
				DbCrises.updateCrisis(theCrisis);
				PtString aMessage = new PtString("The crisis comment has been updated !");
				try {
					theActCoordinator.ieMessage(aMessage);
				} catch (RemoteException e) {
					Log4JUtils.getInstance().getLogger().error(e);
				}
	
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeReportOnCrisis..." + e);
		}
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeGetCrisisSet(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus)
	 */
	public PtBoolean oeGetCrisisSet(EtCrisisStatus aEtCrisisStatus) {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator aActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				//go through all existing crises
				for (String crisisKey : cmpSystemCtCrisis.keySet()) {
					CtCrisis crisis = cmpSystemCtCrisis.get(crisisKey);
					if (crisis.status.toString().equals(aEtCrisisStatus.toString()))
						//PostF1
						crisis.isSentToCoordinator(aActCoordinator);
				}
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeGetCrisisSet..." + e);
		}
		return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeGetAlertsSet(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus)
	 */
	public PtBoolean oeGetAlertsSet(EtAlertStatus aEtAlertStatus) {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
	
				//PostF1
				for (String alertKey : cmpSystemCtAlert.keySet()) {
					CtAlert theCtAlert = cmpSystemCtAlert.get(alertKey);
					if (theCtAlert.status.equals(aEtAlertStatus))
						try {
							//PostF1
							theCtAlert.isSentToCoordinator(theActCoordinator);
						} catch (RemoteException e) {
							Log4JUtils.getInstance().getLogger().error(e);
						}
				}
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeGetAlertsSet..." + e);
		}
		return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeCloseCrisis(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID)
	 */
	public  synchronized PtBoolean oeCloseCrisis(DtCrisisID aDtCrisisID) {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value
					.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				//PostF1
				theCrisis.status = EtCrisisStatus.closed;
				DbCrises.updateCrisis(theCrisis);
				//PostF2
				assCtCrisisCtCoordinator.remove(theCrisis);
				//PostF3
				Collection<CtAlert> keys = assCtAlertCtCrisis.keySet();
				CtAlert[] alertkeys = keys.toArray(new CtAlert[0]);
				for (int i = 0; i < alertkeys.length; i++) {
					CtAlert theAlert = alertkeys[i];
					if (assCtAlertCtCrisis.get(theAlert) == theCrisis) {
						DbAlerts.deleteAlert(theAlert);
						assCtAlertCtCrisis.remove(theAlert);
						cmpSystemCtAlert.remove(theAlert.id.value.getClass());
						if (!assCtAlertCtCrisis.contains(theCrisis))
							break;
					}
				}
	
				//PostF4	
				PtString aMessage = new PtString("The crisis "
						//+ "with ID '"
						//+ aDtCrisisID.value.getValue() + "' "
								+ "is now closed !");
				try {
					theActCoordinator.ieMessage(aMessage);
				} catch (RemoteException e) {
					Log4JUtils.getInstance().getLogger().error(e);
				}
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeCloseCrisis..." + e);
		}

		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeLogin(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword)
	 */
	//actAuthenticated Actor
	public PtBoolean oeLogin(DtLogin aDtLogin, DtPassword aDtPassword)
			throws RemoteException {		
		try {
			log.debug("The current requesting authenticating actor is " + currentRequestingAuthenticatedActor.getLogin().value.getValue());
			//PreP1
			isSystemStarted();
			/**
			 * check whether the credentials corresponds to an existing user
			 *this is done by checking if there exists an instance with
			 *such credential in the ctAuthenticatedInstances data structure
			 */
			CtAuthenticated ctAuthenticatedInstance = cmpSystemCtAuthenticated
					.get(aDtLogin.value.getValue());
			if (ctAuthenticatedInstance != null){
				//PreP2
				if(ctAuthenticatedInstance.vpIsLogged.getValue())
					throw new Exception("User " + aDtLogin.value.getValue() + " is already logged in");
				PtBoolean pwdCheck = ctAuthenticatedInstance.pwd.eq(aDtPassword);
				if(pwdCheck.getValue()) {
					//PostP1
					/**
					 * Make sure that the user logging in is the current requesting user
					 * We do this as each window is a dumb terminal and only one use can logon at each individual window
					 * So user 1 can only logon at the window for user 1, if user 2 tries, it should fail
					 */
					ActAuthenticated authActorCheck = assCtAuthenticatedActAuthenticated.get(ctAuthenticatedInstance);
					log.debug("The logging in actor is " + authActorCheck.getLogin().value.getValue());
					if (authActorCheck != null && authActorCheck.getLogin().value.getValue().equals(currentRequestingAuthenticatedActor.getLogin().value.getValue())){
						ctAuthenticatedInstance.vpIsLogged = new PtBoolean(true);
						//PostF1
						PtString aMessage = new PtString("You are logged ! Welcome ...");
						currentRequestingAuthenticatedActor.ieMessage(aMessage);
						return new PtBoolean(true);
					}
				}
			}
			//PostF1
			PtString aMessage = new PtString(
					"Wrong identification information! Please try again ...");
			currentRequestingAuthenticatedActor.ieMessage(aMessage);
			Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(), RmiUtils.getInstance().getPort());
			IcrashEnvironment env = (IcrashEnvironment) registry
					.lookup("iCrashEnvironment");
			//notify to all administrators that exist in the environment
			for (String adminKey : env.getAdministrators().keySet()) {
				ActAdministrator admin = env.getActAdministrator(adminKey);
				aMessage = new PtString("Intrusion tentative !");
				admin.ieMessage(aMessage);
			}
		} catch (Exception ex) {
			log.error("Exception in oeLogin..." + ex);
		}
		return new PtBoolean(false);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeLogout()
	 */
	public PtBoolean oeLogout() throws java.rmi.RemoteException {
		try{
			//PreP1
			isSystemStarted();
			//PreP2
			isUserLoggedIn();
			log.debug("current Requesting Authenticated Actor Instance is "
					+ currentRequestingAuthenticatedActor.getLogin().value.getValue());
			CtAuthenticated ctAuth = getCtAuthenticated(currentRequestingAuthenticatedActor);
			log.debug("current Associated CtAuthenticated Instance is " + ctAuth.toString());
			if (ctAuth != null) {
				String key = ctAuth.login.value.getValue();
				CtAuthenticated user = cmpSystemCtAuthenticated.get(key);
				//PostP1
				user.vpIsLogged = new PtBoolean(false);
				//PostF1
				PtString aMessage = new PtString(
						"You are logged out ! Good Bye ...");
				currentRequestingAuthenticatedActor.ieMessage(aMessage);
			}
			return new PtBoolean(true);
		}
		catch(Exception e){
			log.error("Exception in oeLogout..." + e);
		}
		return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeAddCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword)
	 */
	public PtBoolean oeAddCoordinator(DtCoordinatorID aDtCoordinatorID,
			DtLogin aDtLogin, DtPassword aDtPassword) throws RemoteException {
		try {
			//PreP1
			isSystemStarted();
			//PreP2
			isAdminLoggedIn();
			Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(), RmiUtils.getInstance().getPort());
			IcrashEnvironment env = (IcrashEnvironment) registry
					.lookup("iCrashEnvironment");
			//PostF1
			ActCoordinator actCoordinator = new ActCoordinatorImpl(aDtLogin);
			env.setActCoordinator(aDtLogin.value.getValue(), actCoordinator);

			//PostF2
			CtCoordinator ctCoordinator = new CtCoordinator();
			ctCoordinator.init(aDtCoordinatorID, aDtLogin, aDtPassword);
			DbCoordinators.insertCoordinator(ctCoordinator);
			
			
			//PostF3 and PostF4 done at once w.r.t. our implementation
			assCtAuthenticatedActAuthenticated.put(ctCoordinator,
					actCoordinator);

			//Update composition relationships
			cmpSystemCtAuthenticated.put(aDtLogin.value.getValue(),
					ctCoordinator);
			assCtCoordinatorActCoordinator.put(ctCoordinator, actCoordinator);
			//PostF5
			ActAdministrator admin = (ActAdministrator) currentRequestingAuthenticatedActor;
			admin.ieCoordinatorAdded();
		} catch (Exception ex) {
			log.error("Exception in oeAddCoordinator..." + ex);
		}

		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeDeleteCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID)
	 */
	public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID) throws RemoteException {
		try {
			//PreP1
			isSystemStarted();
			//PreP2
			isAdminLoggedIn();
			CtAuthenticated ctAuth = getCtCoordinator(aDtCoordinatorID);
			if (ctAuth != null && ctAuth instanceof CtCoordinator) {
				CtCoordinator aCtCoordinator = (CtCoordinator)ctAuth;
				DbCoordinators.deleteCoordinator(aCtCoordinator);
				//PostF1
				assCtAuthenticatedActAuthenticated.remove(ctAuth);
				cmpSystemCtAuthenticated.remove(ctAuth.login.value.getValue());
				ActAdministrator admin = (ActAdministrator) currentRequestingAuthenticatedActor;
				//PostF2
				admin.ieCoordinatorDeleted();
				return new PtBoolean(true);
			}
			return new PtBoolean(false);
		} catch (Exception e) {
			log.error("Exception in oeDeleteCoordinator..." + e);
			return new PtBoolean(false);
		}
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeUpdateCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword)
	 */
	/* This system operation is not specified in the Messir Analysis Document. However, such functionality
	 * has been implemented to show how a coordinator can change his login credentials.
	 * It is worth noticing that such system operation is not used anywhere for the moment (not even included in the class' interface)
	 * 
	 * */
	public PtBoolean oeUpdateCoordinator(DtCoordinatorID aDtCoordinatorID,DtLogin aDtLogin,DtPassword aDtPassword) throws java.rmi.RemoteException{
		try {
			//PreP1
			isSystemStarted();
			//PreP2
			isAdminLoggedIn();
			CtAuthenticated ctAuth = getCtCoordinator(aDtCoordinatorID);
			if (ctAuth != null && ctAuth instanceof CtCoordinator){
				CtCoordinator aCtCoordinator = (CtCoordinator)ctAuth;
				CtCoordinator oldCoordinator = new CtCoordinator();
				oldCoordinator.init(aCtCoordinator.id, aCtCoordinator.login, aCtCoordinator.pwd);
				aCtCoordinator.update(aDtLogin, aDtPassword);
				if (DbCoordinators.updateCoordinator(aCtCoordinator).getValue()){
					cmpSystemCtAuthenticated.remove(oldCoordinator.login.value.getValue());
					cmpSystemCtAuthenticated.put(aCtCoordinator.login.value.getValue(), aCtCoordinator);
					ActAdministrator admin = (ActAdministrator) currentRequestingAuthenticatedActor;
					admin.ieCoordinatorUpdated();
					return new PtBoolean(true);
				}
				else
					aCtCoordinator.update(oldCoordinator.login, oldCoordinator.pwd);
			}
			return new PtBoolean(false);
		} catch (Exception e) {
			log.error("Exception in oeDeleteCoordinator..." + e);
			return new PtBoolean(false);
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeSollicitateCrisisHandling()
	 */
	public PtBoolean oeSollicitateCrisisHandling() throws RemoteException {
		try {
			//PreP1
			isSystemStarted();
			//PreP2
			hasHandlingDelayPassed();
			//go through all existing crises
			for (String crisisKey : cmpSystemCtCrisis.keySet()) {
				CtCrisis crisis = cmpSystemCtCrisis.get(crisisKey);
				//PostF1
				if (crisis.maxHandlingDelayPassed().getValue()){
					crisis.isAllocatedIfPossible();
					DbCrises.updateCrisis(crisis);
				}
				//PostF2
				else if (crisis.handlingDelayPassed().getValue()) {
					PtString aMessageForCrisisHandlers = new PtString(
							"There are alerts pending since more than the defined delay. Please REACT !");
					//notify all ActAuthenticated about crises not being handled
					for(ActAuthenticated actAuth : assCtAuthenticatedActAuthenticated.values())
						actAuth.ieMessage(aMessageForCrisisHandlers);
				}
			}
		} catch (Exception ex) {
			//log.error("Exception in oeSollicitateCrisisHandling..." + ex);
			return new PtBoolean(false);
		}
		//PostP1
		ctState.vpLastReminder = ctState.clock;
		return new PtBoolean(true);
	}
	
	/**
	 * A check to see if the handling delay has passed the standard set in CtState.
	 *
	 * @throws Exception the exception that was encountered when trying to run this method
	 */
	private synchronized void hasHandlingDelayPassed() throws Exception {
		//Interesting read: http://stackoverflow.com/questions/19757300/java-8-lambda-streams-filter-by-method-with-exception
		if (cmpSystemCtCrisis.values().stream().filter(t -> t.status == EtCrisisStatus.pending && propagate(t::handlingDelayPassed).getValue()).count() == 0)
			throw new Exception("There are no unhandled crisises that have exceeded the handling delay");
	}
    
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem#oeSetClock(lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime)
	 */
	public PtBoolean oeSetClock(DtDateAndTime aCurrentClock) {
		try{
			//PreP1
			isSystemStarted();
			//PreP1
			if (aCurrentClock.toSecondsQty().getValue() <= ctState.clock.toSecondsQty().getValue())
			{
				log.error("The clock of " + aCurrentClock.toString() + " is less than the current clock of "+ ctState.clock.toString());
				return new PtBoolean(false);
			}
			//PostF1
			ctState.clock = aCurrentClock;
			ctState.clock.show();
			return new PtBoolean(true);
		} catch (Exception ex) {
			log.error("Exception in oeSetClock..." + ex);
			return new PtBoolean(false);
		}
	}
}
