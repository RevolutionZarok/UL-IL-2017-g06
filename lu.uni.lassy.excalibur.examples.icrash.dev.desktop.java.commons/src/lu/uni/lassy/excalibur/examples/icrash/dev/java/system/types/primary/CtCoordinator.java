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

import java.util.Random;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class CtCoordinator which extends the class of CtAuthenticated
 * This is the class that stores details about the coordinator.
 */
public class CtCoordinator extends CtAuthenticated {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/** The date and time of the registration for the associated coordinator. */
	public DtDateAndTime instant;

	/** The id of the coordinator. */
	public DtCoordinatorID id;
	
	public PtBoolean locked = new PtBoolean(false);
	public DtResetCode resetCode = generateResetCode();
	public DtMailAddress mail;
		
	/**
	 * Initialises the coordinator.
	 *
	 * @param aId The ID of the coordinator
	 * @param aLogin The username of the coordinator
	 * @param aPwd The password of the coordinator
	 * @return The success of the initialisation
	 */
	public PtBoolean init(DtCoordinatorID aId,DtLogin aLogin,DtPassword aPwd,DtMailAddress aMail,PtBoolean aLocked,DtResetCode aResetCode){
			super.init(aLogin, aPwd);
			id = aId;
			mail = aMail;
			locked = aLocked;
			resetCode = aResetCode;
			return new PtBoolean(true); 
	}
	
	/**
	 * Updates the user details, used when the administrator does an update on the coordinator.
	 *
	 * @param aLogin The value to change the login to
	 * @param aPwd the value to change the password to 
	 * @return the success of the update method
	 */
	public PtBoolean update(DtLogin aLogin,DtPassword aPwd,DtMailAddress aMail){
		login = aLogin;
		pwd = aPwd;
		mail = aMail;
		return new PtBoolean(true);
	}
	
	public PtBoolean updateLockedState(PtBoolean aLocked){
		locked = aLocked;
		return new PtBoolean(true);
	}
	
	public PtBoolean updateResetCode(DtResetCode aResetCode){
		resetCode = aResetCode;
		return new PtBoolean(true);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAuthenticated#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof CtCoordinator))
			return false;
		CtCoordinator aCtCoordinator = (CtCoordinator)obj;
		if (!aCtCoordinator.id.value.getValue().equals(this.id.value.getValue()))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAuthenticated#hashCode()
	 */
	@Override
	public int hashCode(){
		return this.id.value.getValue().length() + super.hashCode();
	}
	
	public static DtResetCode generateResetCode(){
		StringBuilder sb = new StringBuilder();
		Random rand = new Random(System.currentTimeMillis());
		for(int i = 0 ; i < 8 ; i++){
			int j = 48;
			switch(rand.nextInt(3)){
			case 0:
				j = 48 + rand.nextInt(10);
				break;
			case 1:
				j = 65 + rand.nextInt(26);
				break;
			case 2:
				j = 97 + rand.nextInt(26);
				break;
			}
			sb.append((char)j);
		}
		return new DtResetCode(new PtString(sb.toString()));
	}
}
