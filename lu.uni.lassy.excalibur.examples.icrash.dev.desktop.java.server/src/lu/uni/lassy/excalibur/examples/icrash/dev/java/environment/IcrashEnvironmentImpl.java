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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActActivator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActMsrCreator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActMsrCreatorImpl;

/**
 * The Class IcrashEnvironmentImpl.
 */
public class IcrashEnvironmentImpl extends UnicastRemoteObject implements IcrashEnvironment {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The admins stored in the environment. */
	private Hashtable<String, ActAdministrator> admins = new Hashtable<String, ActAdministrator>();
	
	/** The actor activator that is set during create system and environment. 
	 * Allows setting of the clock and solicitates crisis handling*/
	private ActActivator actActivator;
	
	/** The actor MSR creator, used to create the system and environment. */
	private ActMsrCreator actMsrCreator;
	
	/** The comunication company stored in the environment. */
	private Hashtable<String, ActComCompany> comCompanies = new Hashtable<String, ActComCompany>();
	
	/** The coordinators stored in the environment. 
	 * Key = ActCoordinator's login's name*/
	private Hashtable<String, ActCoordinator> coordinators = new Hashtable<String, ActCoordinator>();

	/** The instance of the environment. */
	private static volatile IcrashEnvironmentImpl instance = null;
	
    /**
     * Instantiates a new icrash environment implementation.
     * It will also create the Actor MsrCreator
     *
     *@throws RemoteException Thrown if the server is offline
     */
    private IcrashEnvironmentImpl() throws RemoteException {
     	super();
     	actMsrCreator = new ActMsrCreatorImpl();
    }
 
    /**
     * Gets the single instance of the IcrashEnvironmentImplementation.
     *
     * @return The instance of the icrash environment, this prevents more than one version being available
     * @throws RemoteException Thrown if the server is offline
     */
    public static IcrashEnvironmentImpl getInstance()  throws RemoteException {
     	if(instance == null)
        	instance = new IcrashEnvironmentImpl();
        return instance;
    }

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#doTest()
	 */
	public void doTest() throws java.rmi.RemoteException{
		System.out.println("The environment is set ...");
	}
		
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#setActAdministrator(java.lang.String, lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator)
	 */
	public void setActAdministrator(String keyName,ActAdministrator aActAdministrator){
		admins.put(keyName, aActAdministrator);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#getActAdministrator(java.lang.String)
	 */
	public ActAdministrator getActAdministrator(String keyName){
		return admins.get(keyName);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#getAdministrators()
	 */
	public Hashtable<String, ActAdministrator> getAdministrators(){
		return admins;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#setActActivator(lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActActivator)
	 */
	public void setActActivator(ActActivator aActActivator){
		actActivator = aActActivator;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#getActActivator()
	 */
	public ActActivator getActActivator(){
		return actActivator;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#getActAuthenticated(java.lang.String)
	 */
	public ActAuthenticated getActAuthenticated(String keyName){
		ActAuthenticated aActAuth = getActAdministrator(keyName);
		if (aActAuth == null)
			aActAuth = getActCoordinator(keyName);
		return aActAuth; 
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#getActMsrCreator()
	 */
	public ActMsrCreator getActMsrCreator(){
		return actMsrCreator;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#setComCompany(java.lang.String, lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany)
	 */
	public void setComCompany(String keyName, ActComCompany aActComCompany){
		comCompanies.put(keyName, aActComCompany);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#getComCompany(java.lang.String)
	 */
	public ActComCompany getComCompany(String keyName){
		return comCompanies.get(keyName);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#setActCoordinator(java.lang.String, lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator)
	 */
	public void setActCoordinator(String keyName, ActCoordinator aActCoordinator){
		coordinators.put(keyName, aActCoordinator);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#getActCoordinator(java.lang.String)
	 */
	public ActCoordinator getActCoordinator(String keyName){
		return coordinators.get(keyName);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment#getActComCompanies()
	 */
	public Hashtable<String, ActComCompany> getActComCompanies(){
		return comCompanies;
	}
	
}
