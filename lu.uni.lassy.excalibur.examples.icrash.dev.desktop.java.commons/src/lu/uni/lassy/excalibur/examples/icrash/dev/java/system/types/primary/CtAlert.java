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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Class CtAlert, that hold details about the alert instance.
 */
public class CtAlert implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/** The id of the alert. */
	public DtAlertID id;
	
	/** The current status of the alert.*/
	public EtAlertStatus status;
	
	/** The location of the alert. */
	public DtGPSLocation location;
	
	/** The date and time of the accident the alert is associated with. */
	public DtDateAndTime instant;
	
	/** The comment associated with the alert. */
	public DtComment comment;

	/**
	 * Initialises the alert.
	 *
	 * @param aId the ID of the alert
	 * @param aStatus the status of the alert
	 * @param aLocation the location of the alert
	 * @param aInstant the date and time of the accident the alert is associated with
	 * @param aComment the comment associated with the alert.
	 * @return the success of the initialisation of the alert
	 */
	public PtBoolean init(DtAlertID aId, EtAlertStatus aStatus,
			DtGPSLocation aLocation, DtDateAndTime aInstant, DtComment aComment) {
			
		id = aId;
		status = aStatus;
		location = aLocation;
		instant = aInstant;
		comment = aComment;
		return new PtBoolean(true);
	}

	/**
	 * Allows for receiving a requested ctAlert instance.
	 *
	 * @param aActCoordinator the actor coordinator that should recieve the alert
	 * @return the success of the method
	 * @throws RemoteException If the server is offline, this exception is thrown
	 */
	public PtBoolean isSentToCoordinator(ActCoordinator aActCoordinator) throws RemoteException {
		aActCoordinator.ieSendAnAlert(this);
		return new PtBoolean(true);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.id.value.getValue();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof CtAlert))
			return false;
		CtAlert aCtAlert = (CtAlert)obj;
		if (!(aCtAlert.id.value.getValue().equals(this.id.value.getValue())))
			return false;
		if (!(aCtAlert.instant.toString().equals(this.instant.toString())))
			return false;
		if (!(aCtAlert.comment.value.getValue().equals(this.comment.value.getValue())))
			return false;
		if (!(aCtAlert.status.equals(this.status)))
			return false;
		if (aCtAlert.location.latitude.value.getValue() != this.location.latitude.value.getValue()) 
			return false;
		if (aCtAlert.location.longitude.value.getValue() != this.location.longitude.value.getValue()) 
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return this.id.value.getValue().length();
	}
}
