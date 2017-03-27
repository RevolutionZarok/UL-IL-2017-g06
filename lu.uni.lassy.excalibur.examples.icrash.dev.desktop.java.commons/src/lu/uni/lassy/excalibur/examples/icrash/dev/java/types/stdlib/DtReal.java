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
 * The Class DtReal, which holds a datatype of the primitive type of real (Double).
 */
public class DtReal implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/** The value holding the primitive type of the real (Double). */
	public PtReal value;
	
	/**
	 * Instantiates a new datatype real.
	 *
	 * @param r The primitive type real to become the datatype
	 */
	public DtReal(PtReal r){
		value = r;
	}
}
