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
 * The exception that is thrown if the system attempts to convert a string to a number and fails.
 */
public class StringToNumberException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3781110035981125833L;
	
	/**
	 * Initialises the exception message.
	 *
	 * @param dataPassed The data passed by the user that couldn't be parsed to a number
	 */
	public StringToNumberException(String dataPassed){
		this._dataPassed = dataPassed;
	}
	
	/** The original data passed by the user. */
	private String _dataPassed;
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		return "Unable to parse the string " + _dataPassed +" into a number format, please try again";
	}
}
