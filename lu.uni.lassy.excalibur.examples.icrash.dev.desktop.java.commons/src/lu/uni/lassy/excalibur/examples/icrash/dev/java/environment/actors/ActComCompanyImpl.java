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
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
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

import org.apache.log4j.Logger;

/**
 * The Class ActComCompanyImpl, that implements the server side actor for the Communication Companies.
 */
public class ActComCompanyImpl extends UnicastRemoteObject implements ActComCompany {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/** The ID of the communication company, this allows us to work out which actor belongs to which communication company.
	 * It should be a 1 to 1 relationship
	 */
	private int _id;

	/**
	 * Instantiates a new server side actor for the communication company. This is the implemented version of the actor
	 *
	 * @param n The name of the communication company this actor should be associated with
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActComCompanyImpl(int id) throws RemoteException {
		super(RmiUtils.getInstance().getPort());
		_id = id;
	}
	public ActComCompanyImpl(int id, String name) throws RemoteException{
		super(RmiUtils.getInstance().getPort());
		_id = id;
		_name = name;
	}
	
	private String _name;

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany#getName()
	 */
	public String getName() {
		if (_name == null || _name.trim() == "")
			return "Com Company "+ _id;
		else
			return _name;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany#oeAlert(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind, lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate, lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment)
	 */
	synchronized public PtBoolean oeAlert(EtHumanKind aEtHumanKind,
			DtDate aDtDate, DtTime aDtTime, DtPhoneNumber aDtPhoneNumber,
			DtGPSLocation aDtGPSLocation, DtComment aDtComment)
			throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		//set up ComCompany instance that performs the request
		iCrashSys_Server.setCurrentConnectedComCompany(this);

		log.info("message ActComCompany.oeAlert sent to system");
		PtBoolean res = iCrashSys_Server.oeAlert(aEtHumanKind, aDtDate,
				aDtTime, aDtPhoneNumber, aDtGPSLocation, aDtComment);

		if (res.getValue() == true)
			log.info("operation oeAlert successfully executed by the system");

		return res;
	}

	/** The listeners that have been added to to this class. */
	protected List<ActProxyComCompany> listeners = new ArrayList<ActProxyComCompany>();

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany#addListener(lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyComCompany)
	 */
	synchronized public void addListener(
			ActProxyComCompany aActProxyAuthenticated) throws RemoteException,
			NotBoundException {
		if (listeners.contains(aActProxyAuthenticated))
			listeners.remove(aActProxyAuthenticated);
		listeners.add(aActProxyAuthenticated);			
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany#removeListener(lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyComCompany)
	 */
	synchronized public void removeListener(
			ActProxyComCompany aActProxyAuthenticated) throws RemoteException,
			NotBoundException {
		listeners.remove(aActProxyAuthenticated);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany#ieSmsSend(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.secondary.DtSMS)
	 */
	public PtBoolean ieSmsSend(DtPhoneNumber aDtPhoneNumber, DtSMS aDtSMS) {
		Logger log = Log4JUtils.getInstance().getLogger();

		log.info("message ActComCompany.ieSmsSend received from system");
		log.info("Phone number: " + aDtPhoneNumber.value.getValue());
		log.info("SMS: " + aDtSMS.value.getValue());
		boolean messageSent = false;
		for (Iterator<ActProxyComCompany> iterator = listeners.iterator(); iterator
				.hasNext();) {
			ActProxyComCompany aProxy = iterator.next();
			try {
				aProxy.ieSmsSend(aDtPhoneNumber, aDtSMS);
				messageSent = true;
			} catch (RemoteException e) {
				//Most likely the client that created the listener disconnected, so we shall remove it
				log.error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(messageSent);
	}
	
	/* (non-Javadoc)
	 * @see java.rmi.server.RemoteObject#toString()
	 */
	@Override
	public String toString(){
		return this.getName();
	}
	
	/* (non-Javadoc)
	 * @see java.rmi.server.RemoteObject#hashCode()
	 */
	@Override
	public int hashCode(){
		return this.getName().length();
	}
	
	/* (non-Javadoc)
	 * @see java.rmi.server.RemoteObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof ActComCompanyImpl))
			return false;
		ActComCompanyImpl aActComCompany = (ActComCompanyImpl)obj;
		if (this._id != aActComCompany._id)
			return false;
		if (!this.getName().equals(aActComCompany.getName()))
			return false;
		return true;
	}
}
