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
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class DtLongitude, which holds a datatype of the longitude.
 */
public class DtLongitude extends DtReal implements JIntIs{

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/**
		 * Instantiates a new datatype longitude.
		 *
		 * @param r The primitive type real (Double) to become the datatype longitude
		 */
		public DtLongitude(PtReal r) {
			super(r);
		}
		
		/** The minimum number a longitude value could be. */
		private double _minNumber = -180;
		
		/** The maximum number a longitude value could be. */
		private double _maxNumber = 180;
		
		/* (non-Javadoc)
		 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#is()
		 */
		public PtBoolean is(){
			//http://www.geomidpoint.com/latlon.html
			return new PtBoolean(this.value.getValue() >= _minNumber && this.value.getValue() <= _maxNumber);
		}
		
		/* (non-Javadoc)
		 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#getExpectedDataStructure()
		 */
		public PtString getExpectedDataStructure(){
			return new PtString("Expected strucutre of the longitude is to have a minimum size of " + _minNumber + " and a maximum size of " + _maxNumber); 
		}
}
