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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib;

import java.io.Serializable;

/**
 * The Class PtString to wrap a primitive type of string.
 */
public class PtString implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/** The value to wrap. */
	String value;

	/**
	 * Instantiates a new primitive type of string.
	 *
	 * @param v The value to be wrapped
	 */
	public PtString(String v){
		value = v;
	}

	/**
	 * Gets the value inside the wrapper.
	 *
	 * @return The value inside the wrapper
	 */
	public String getValue(){
		return value;
	}

}
