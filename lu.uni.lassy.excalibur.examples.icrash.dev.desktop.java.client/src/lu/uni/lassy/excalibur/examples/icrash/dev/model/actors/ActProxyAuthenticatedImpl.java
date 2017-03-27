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
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.*;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message.MessageType;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class ActProxyAuthenticatedImpl, which creates a client side actor of authenticated type.
 */
public abstract class ActProxyAuthenticatedImpl extends UnicastRemoteObject implements ActProxyAuthenticated {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/** The server side actor to use when calling user specific methods. */
	private ActAuthenticated _serverSideActor;
	
	/**
	 * Instantiates a new client side actor of type authenticated.
	 *
	 * @param serverSideActor The server side actor associated with this client side actor
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server hasn't been correctly bound in the RMI settings
	 */
	public ActProxyAuthenticatedImpl(ActAuthenticated serverSideActor) throws RemoteException, NotBoundException{
		super(RmiUtils.getInstance().getPortClient());
		setServerSideActor(serverSideActor);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated#getName()
	 */
	public DtLogin getLogin() throws RemoteException{
		return this.getServerSideActor().getLogin();
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.HasServerSideActor#destroy()
	 */
	public void destroy() throws RemoteException, NotBoundException {
		// unregister server side listener
		if(_serverSideActor!=null)
			_serverSideActor.removeListener(this);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated#oeLogin(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword)
	 */
	public PtBoolean oeLogin(DtLogin aDtLogin,DtPassword aDtPassword)  throws RemoteException, NotBoundException{
		return this._serverSideActor.oeLogin(aDtLogin, aDtPassword);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated#oeLogout()
	 */
	public PtBoolean oeLogout() throws RemoteException, NotBoundException{
		return this._serverSideActor.oeLogout();
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated#ieMessage(lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString)
	 */
	public PtBoolean ieMessage(PtString aMessage) {
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("message ActAuthenticated.ieMessage received from system");
		log.info("ieMessage is: " + aMessage.getValue());
		listOfMessages.add(new Message(MessageType.ieMessage, aMessage.getValue()));
		return new PtBoolean(true);
	}

	/**
	 * Gets the server side actor.
	 * Server side actors are used to call methods on the server that are user specific
	 *
	 * @return the server side actor associated with this class
	 */
	protected ActAuthenticated getServerSideActor() {
		return _serverSideActor;
	}
	
	/**
	 * Sets the server side actor, this is done at time of initialisation.
	 *
	 * @param actAuth the server side actor that should be associated with this actor
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server hasn't been correctly bound in the RMI settings
	 */
	private void setServerSideActor(ActAuthenticated actAuth) throws RemoteException, NotBoundException{
		destroy();
		this._serverSideActor = actAuth;
		this._serverSideActor.addListener(this);
	}
	/** A list of messages that have been sent to the client actor. */
	public ArrayList<Message> _listOfMessages = new ArrayList<Message>();
	
	/**  This is an observable list of messages, which when a listener is bound to, will inform the listerner something has happened and it should update This allows real life time updates on the client GUI. */
	public ObservableList<Message> listOfMessages = FXCollections.observableArrayList(_listOfMessages);
	
	/* (non-Javadoc)
	 * @see java.rmi.server.RemoteObject#toString()
	 */
	@Override
	public String toString(){
		try {
			return this.getLogin().value.getValue();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			return "Server down - Can't get name";
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated#getUserType()
	 */
	@Override
	public UserType getUserType() {
		if(_serverSideActor == null)
			return UserType.None;
		if(_serverSideActor instanceof ActAdministrator)
			return UserType.Admin;
		if(_serverSideActor instanceof ActCoordinator)
			return UserType.Coordinator;
		if (_serverSideActor instanceof ActComCompany)
			return UserType.ComCompany;
		return UserType.Unknown;
	}
}
