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
package lu.uni.lassy.excalibur.examples.icrash.dev.model.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message.MessageType;

import org.apache.log4j.Logger;

/**
 * The Class ActProxyAdministratorImpl, that implements the client side actor for the administrator.
 */
public class ActProxyAdministratorImpl extends ActProxyAuthenticatedImpl implements ActProxyAdministrator {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new client side actor for the administrator.
	 *
	 * @param user The server side actor this client side actor should be linked to. This allows the actor to listen for incoming messages
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server hasn't been correctly bound in the RMI settings
	 */
	public ActProxyAdministratorImpl(ActAdministrator user) throws RemoteException, NotBoundException {
		super(user);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAdministrator#oeAddCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword)
	 */
	synchronized public PtBoolean oeAddCoordinator(DtCoordinatorID aDtCoordinatorID, DtLogin aDtLogin, DtPassword aDtPassword) throws RemoteException, NotBoundException {
		if(getServerSideActor() !=null)
			return ((ActAdministrator) getServerSideActor()).oeAddCoordinator(aDtCoordinatorID, aDtLogin, aDtPassword);
		else
			return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAdministrator#oeDeleteCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID)
	 */
	synchronized public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID) throws RemoteException, NotBoundException{
		if(getServerSideActor() !=null)
			return ((ActAdministrator) getServerSideActor()).oeDeleteCoordinator(aDtCoordinatorID);
		else
			return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAdministrator#ieCoordinatorAdded()
	 */
	public PtBoolean ieCoordinatorAdded(){
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("message ActAdministrator.ieCoordinatorAdded received from system");
		listOfMessages.add(new Message(MessageType.ieCoordinatorAdded));
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAdministrator#ieCoordinatorDeleted()
	 */
	public PtBoolean ieCoordinatorDeleted(){
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("message ActAdministrator.ieCoordinatorDeleted received from system");
		listOfMessages.add(new Message(MessageType.ieCoordinatorDeleted));
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAdministrator#ieCoordinatorUpdated()
	 */
	@Override
	public PtBoolean ieCoordinatorUpdated() throws RemoteException {
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("message ActAdministrator.ieCoordinatorUpdated received from system");
		listOfMessages.add(new Message(MessageType.ieCoordinatorUpdated));
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyAuthenticatedImpl#oeLogout()
	 */
	@Override
	public PtBoolean oeLogout() throws RemoteException, NotBoundException {
		return super.oeLogout();
	}
}
