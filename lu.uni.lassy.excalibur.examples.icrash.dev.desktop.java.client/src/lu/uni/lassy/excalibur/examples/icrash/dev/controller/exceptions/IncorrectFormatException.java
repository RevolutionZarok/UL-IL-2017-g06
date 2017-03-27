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

import java.util.ArrayList;

/**
 * The Class IncorrectFormatException.
 */
public class IncorrectFormatException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 457447815395872289L;
	
	/**
	 * Instantiates a new incorrect format exception.
	 * This exception is thrown when a user attempts to pass off a string as data type, but it doesn't comply
	 * with the is method specified in the data type. These data types should implement the JIntIs interface
	 * @param dataPassed The original data passed
	 * @param expectedFormat What the expected format is. This can be retrieved from the method getExpectedDataStructure
	 */
	public IncorrectFormatException(String dataPassed, String expectedFormat){
		this._dataPassed.add(dataPassed);
		this._expectedFormat.add(expectedFormat);
	}
	
	/**
	 * Instantiates a new incorrect format exception.
	 * This exception is thrown when a user attempts to pass off a string as data type, but it doesn't comply
	 * with the is method specified in the data type. These data types should implement the JIntIs interface
	 * This is the list version, each index in the list should correspond to the same index in the other list
	 * Would have liked to do this as a Hashtable, but what would be the guaranteed unique key? The user could
	 * put the same data twice for other items. Maybe a double array list would work, but still funny...
	 * @param dataPassed A list of the original data passed
	 * @param expectedFormat A list of what the expected format is. This can be retrieved from the method getExpectedDataStructure
	 */
	public IncorrectFormatException(ArrayList<String> dataPassed, ArrayList<String> expectedFormat){
		this._dataPassed.addAll(dataPassed);
		this._expectedFormat.addAll(expectedFormat);
	}
	
	/**  The original data passed by the user. */
	private ArrayList<String> _dataPassed = new ArrayList<String>();
	
	/**  The expected format of the data type. */
	private ArrayList<String> _expectedFormat = new ArrayList<String>();
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("Incorrect format(s) specified, the following error(s) were encountered:\n");
		int max = 0;
		if (_dataPassed.size() < _expectedFormat.size())
			max = _dataPassed.size();
		else
			max = _expectedFormat.size();		
		for(int i = 0; i < max; i++){
			sb.append("The expected format of " + _expectedFormat.get(i) + " did not apply to the value " + _dataPassed.get(i) + "\n\n");
		}
		sb.append("Please try again");
		return sb.toString();
	}
}
