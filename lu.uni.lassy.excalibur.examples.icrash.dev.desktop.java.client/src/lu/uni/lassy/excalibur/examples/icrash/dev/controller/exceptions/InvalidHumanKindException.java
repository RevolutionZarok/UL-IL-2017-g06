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

/**
 * An exception for when someone attempts to pass a value that isn't in the enum type of 
 * human kind.
 */
public class InvalidHumanKindException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1769573992297837913L;
	
	/**
	 * Instantiates a new invalid human kind exception.
	 *
	 * @param dataProvided the data provided that was expected to be a enum of Human kind, but wasn't
	 */
	public InvalidHumanKindException(String dataProvided){
		this._dataProvided = dataProvided;
	}
	
	/** The _data provided to the system by the user. */
	private String _dataProvided;
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		return "Invalid type of person, " + _dataProvided + ", provided to system, please contact support";
	}
}
