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
 * The Class PtBoolean which is wrapper for the primitive type of boolean.
 */
public class PtBoolean implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/** The value of the boolean to be wrapped. */
	boolean value;

	/**
	 * Instantiates a new primitive type of boolean.
	 *
	 * @param v The value of the boolean to wrap
	 */
	public PtBoolean(boolean v){
		value = v;
	}
	
	/**
	 * Returns the value from inside the wrapper.
	 *
	 * @return the value of the boolean
	 */
	public boolean getValue(){
		return value;
	}
	
}
