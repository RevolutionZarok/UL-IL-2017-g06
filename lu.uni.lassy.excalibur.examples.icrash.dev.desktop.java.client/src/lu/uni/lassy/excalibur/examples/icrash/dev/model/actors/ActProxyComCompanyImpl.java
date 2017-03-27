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

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntHasServerSideActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.secondary.DtSMS;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message.MessageType;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class ActProxyComCompanyImpl, which creates a client side actor for the communication company.
 */
public class ActProxyComCompanyImpl extends UnicastRemoteObject implements
		ActProxyComCompany, JIntHasServerSideActor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/** The name of the communication company associated with this actor. */
	private String name;

	/** The server side actor associated with this class. */
	ActComCompany serverSideActor;

	/**
	 * Instantiates a new client side actor for the communication company.
	 *
	 * @param serverSideActor The server side actor to associate with this class
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public ActProxyComCompanyImpl(ActComCompany serverSideActor)
			throws RemoteException, NotBoundException {
		super(RmiUtils.getInstance().getPortClient());
		this.serverSideActor = serverSideActor;
		name = serverSideActor.getName();
		// register server side listener
		if (serverSideActor != null)
			serverSideActor.addListener(this);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.HasServerSideActor#destroy()
	 */
	public void destroy() throws RemoteException, NotBoundException {
		// unregister server side listener
		if (serverSideActor != null)
			serverSideActor.removeListener(this);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyComCompany#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyComCompany#oeAlert(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind, lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate, lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment)
	 */
	synchronized public PtBoolean oeAlert(EtHumanKind aEtHumanKind,
			DtDate aDtDate, DtTime aDtTime, DtPhoneNumber aDtPhoneNumber,
			DtGPSLocation aDtGPSLocation, DtComment aDtComment)
			throws RemoteException, NotBoundException {

		if (serverSideActor != null)
			return serverSideActor.oeAlert(aEtHumanKind, aDtDate, aDtTime,
					aDtPhoneNumber, aDtGPSLocation, aDtComment);
		else
			return new PtBoolean(false);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyComCompany#ieSmsSend(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.secondary.DtSMS)
	 */
	public PtBoolean ieSmsSend(DtPhoneNumber aDtPhoneNumber, DtSMS aDtSMS) {
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("message ActComCompany.ieSmsSend received from system");
		log.info("Phone number: " + aDtPhoneNumber.value.getValue());
		log.info("SMS: " + aDtSMS.value.getValue());
		listOfMessages.add(new Message(MessageType.ieSmsSend, aDtPhoneNumber.value.getValue() +": " + aDtSMS.value.getValue()));
		return new PtBoolean(true);
	}
	
	/** A list of messages sent to this actor from the server. It is updated via ieSmsSend, but has to update the observable list */
	private ArrayList<Message> _listOfMessages = new ArrayList<Message>();
	
	/** An observable list of messages, which allows attachment of a listener which will do something if the list is changed
	 * The event is only triggered if the observable list is changed directly. If the underlying array list is updated, the event does not
	 * get triggered*/
	public ObservableList<Message> listOfMessages = FXCollections.observableArrayList(_listOfMessages);
	
	/* (non-Javadoc)
	 * @see java.rmi.server.RemoteObject#hashCode()
	 */
	@Override
	public int hashCode(){
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see java.rmi.server.RemoteObject#equals(java.lang.Object)
	 */
	@Override
    public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof ActProxyComCompanyImpl))
			return false;
		ActProxyComCompanyImpl aActProxComComp = (ActProxyComCompanyImpl)obj;
		if (!aActProxComComp.name.equals(this.name))
			return false;
		return true;
	}
	
}