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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

import org.apache.log4j.Logger;
/**
 * The Class ActAdministratorImpl, which is a server side actor for the user Administrator.
 */
public class ActAdministratorImpl extends ActAuthenticatedImpl implements
		ActAdministrator {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new server side actor of type administrator.
	 *
	 * @param n The username of the Administrator associated with this actor. This helps linking class types and actors together
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActAdministratorImpl(DtLogin n) throws RemoteException {
		super(n);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#getName()
	 */
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#oeAddCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword)
	 */
	synchronized public PtBoolean oeAddCoordinator(
			DtCoordinatorID aDtCoordinatorID, DtLogin aDtLogin,
			DtPassword aDtPassword) throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeAddCoordinator sent to system");
		PtBoolean res = iCrashSys_Server.oeAddCoordinator(aDtCoordinatorID,
				aDtLogin, aDtPassword);

		if (res.getValue() == true)
			log.info("operation oeAddCoordinator successfully executed by the system");

		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#oeDeleteCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID)
	 */
	synchronized public PtBoolean oeDeleteCoordinator(
			DtCoordinatorID aDtCoordinatorID) throws RemoteException,
			NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeDeleteCoordinator sent to system");
		PtBoolean res = iCrashSys_Server.oeDeleteCoordinator(aDtCoordinatorID);

		if (res.getValue() == true)
			log.info("operation oeDeleteCoordinator successfully executed by the system");

		return res;
		
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#ieCoordinatorAdded()
	 */
	public PtBoolean ieCoordinatorAdded() {
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator
				.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieCoordinatorAdded();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#ieCoordinatorDeleted()
	 */
	public PtBoolean ieCoordinatorDeleted() {
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator
				.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieCoordinatorDeleted();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#ieCoordinatorUpdated()
	 */
	@Override
	public PtBoolean ieCoordinatorUpdated() throws RemoteException {
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator
				.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieCoordinatorUpdated();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}

}
