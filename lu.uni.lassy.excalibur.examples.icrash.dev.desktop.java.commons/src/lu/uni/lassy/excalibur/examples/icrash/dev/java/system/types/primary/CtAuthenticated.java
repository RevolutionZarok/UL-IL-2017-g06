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
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Class CtAuthenticated, which is the base class that all users inherit from.
 */
public abstract class CtAuthenticated implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/**  The user's username used to logon to the system with. */
	public DtLogin login;
	
	/**  The user's password. */
	public DtPassword pwd;
	
	/**  A check to see if the current Ct class is considered logged into the system. */
	public PtBoolean vpIsLogged;	
	
	/**
	 * Initialisation of the user.
	 *
	 * @param aLogin The username of the user
	 * @param aPwd The password of the user
	 * @return The success of the initialisation of the user
	 */
	public PtBoolean init(DtLogin aLogin, DtPassword aPwd){
			login = aLogin;
			pwd = aPwd;
			vpIsLogged = new PtBoolean(false);
			return new PtBoolean(true); 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.login.value.getValue();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof CtAuthenticated))
			return false;
		CtAuthenticated aCtAuth = (CtAuthenticated)obj;
		if (!aCtAuth.login.value.getValue().equals(this.login.value.getValue()))
			return false;
		if (!aCtAuth.pwd.value.getValue().equals(this.pwd.value.getValue()))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return this.login.value.getValue().length();
	}
}
