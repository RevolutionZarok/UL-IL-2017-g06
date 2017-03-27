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

/**
 * A datatype for storing the hour (24 hour format) as a PtInteger.
 */
public class DtHour extends DtInteger{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Creates a datatype for storing the hour provided.
	 *
	 * @param v The hour to store (In the 24 hour format)
	 */
	public DtHour(PtInteger v) {
		super(v);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return String.format("%02d", value.getValue());
	}
}
