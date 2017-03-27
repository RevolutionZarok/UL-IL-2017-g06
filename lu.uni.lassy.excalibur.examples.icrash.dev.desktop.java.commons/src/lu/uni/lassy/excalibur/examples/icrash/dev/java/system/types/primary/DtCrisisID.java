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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class DtCrisisID, which holds a datatype of the Crisis ID.
 */
public class DtCrisisID extends DtString implements JIntIs {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new datatype of the crisis id.
	 *
	 * @param s The primitive type of string to create the datatype
	 */
	public DtCrisisID(PtString s) {
		super(s);
	}
	
	/** The minimum length the crisis ID cannot be. */
	private int _minLength = 0;
	
	/** The maximum length the crisis ID can be. */
	private int _maxLength = 10;
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#is()
	 */
	public PtBoolean is(){
		return new PtBoolean(this.value.getValue().length() > _minLength &&
				this.value.getValue().length() <= _maxLength);
	}
}
