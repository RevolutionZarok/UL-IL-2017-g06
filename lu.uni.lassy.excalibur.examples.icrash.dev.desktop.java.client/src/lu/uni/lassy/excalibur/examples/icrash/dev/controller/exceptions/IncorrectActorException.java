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
package lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;

/**
 * The Class IncorrectActorException, thrown if the wrong actor type is passed to a new window.
 */
public class IncorrectActorException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1105998031919290246L;
	
	/**
	 * Instantiates a new incorrect actor exception.
	 *
	 * @param actorPassed the actor passed
	 * @param expectedActor the expected actor
	 */
	public IncorrectActorException(JIntIsActor actorPassed, Class<?> expectedActor) {
		this._ActorPassedName = actorPassed.getClass().getName();
		this._expectedActorName = expectedActor.getName();
	}
	
	/** The _expected actor name. */
	private String _expectedActorName;
	
	/** The _ actor passed name. */
	private String _ActorPassedName;
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		return "Unable to create the window as incorrect actor type was passed. Expecting a " + _expectedActorName + " but got a " + _ActorPassedName;
	}
}
