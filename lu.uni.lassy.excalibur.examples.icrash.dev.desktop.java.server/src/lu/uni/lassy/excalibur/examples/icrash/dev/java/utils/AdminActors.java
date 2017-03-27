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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.utils;

/**
 * The Enum of admin usernames in the system.
 */
public enum AdminActors {
	//add as many admin actor's names as needed
	
	/* ********************************************* for the moment, the specification only  considers the existence of one single admin! ********************************************/ 
	/** The default username, in the server, for the admin*/  
	icrashadmin;
	
	/** The Constant values of the admins in the system. */
	public static final AdminActors values[] = values();
}
