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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.secondary.DtSMS;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

import org.apache.log4j.Logger;

/**
 * The Class of the Human and their details
 * Humans are created at point of alert creation and are stored by their phone number as a key.
 */
public class CtHuman implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**  The phone number of the human, this is also used as the ID. */
	public DtPhoneNumber id;
	
	/** The type of person they are. An example would be a victim to a crash */
	public EtHumanKind kind;

	/**
	 * Initialises the class and it's data.
	 *
	 * @param aId The phone number of the human and their ID 
	 * @param aKind The type of human
	 * @return the success of the Initialisation
	 */
	public PtBoolean init(DtPhoneNumber aId,EtHumanKind aKind){

		id = aId;
		kind = aKind;

		return new PtBoolean(true);
	}

	/**
	 * used to specify the property of having sent an alert acknowledgement message to the human who had created the
	 * alert through its own communication company.
	 *
	 * @return the success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean isAcknowledged() throws RemoteException {

		Logger log = Log4JUtils.getInstance().getLogger();
		try{
			Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(), RmiUtils.getInstance().getPort());
	        IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
			ActComCompany theComCompany = iCrashSys_Server.getActComCompany(this);
			if(theComCompany != null){
				DtSMS sms = new DtSMS(new PtString("The handling of your alert by our services is in progress !"));
				return theComCompany.ieSmsSend(this.id, sms);
			} else
				throw new Exception("No com company assigned to the human " + this.id.value.getValue());
		}catch(Exception ex){
			log.error("Exception in CtHuman.isAcknowledged ..." + ex);
		}
		return new PtBoolean(false);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof CtHuman))
			return false;
		CtHuman aCtHuman = (CtHuman)obj;
		if (!aCtHuman.id.value.getValue().equals(this.id.value.getValue()))
			return false;
		if (!aCtHuman.kind.equals(this.kind))
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
