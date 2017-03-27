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
 * The Class NullValueException, which extends a null pointer exception.
 * It adds the factor of knowing which class the null pointer came from
 * without digging through the lines of the exception
 */
public class NullValueException extends NullPointerException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7293698075839790870L;
	
	/**
	 * Instantiates a new null value exception.
	 *
	 * @param currentClass the current class that the exception was thrown in
	 */
	public NullValueException(Class<?> currentClass){
		this._currentClass = currentClass;
	}
	
	/** The current class that the exception was thrown in. */
	private Class<?> _currentClass;
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		return _currentClass.getName() + " has a missing value and is unable to update";
	}
}
