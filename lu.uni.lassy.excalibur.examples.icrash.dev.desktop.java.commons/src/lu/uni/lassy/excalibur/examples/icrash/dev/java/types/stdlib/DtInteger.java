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
 * The Class DtInteger, which holds a datatype of the primitive type integer.
 */
public class DtInteger implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/** The value which holds the primitive type integer. */
		public PtInteger value;		
		
		/**
		 * Instantiates a new datatype integer.
		 *
		 * @param v The primitive type of integer to create the datatype
		 */
		public DtInteger(PtInteger v){
			value = v;
		}
}
