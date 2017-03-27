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
 * The Class DtTime, which holds a datatype of the time.
 */
public class DtTime implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/** The datatype of the hour inside the time. */
		public DtHour hour;
		
		/** The datatype of the minute inside the time. */
		public DtMinute minute;
		
		/** The datatype of the second inside the time. */
		public DtSecond second; 

		/**
		 * Instantiates a new datatype of time, with the DtSecond, DtMinute and DtHour provided.
		 *
		 * @param aHour The datatype hour to use
		 * @param aMinute The datatype minute to use
		 * @param aSecond The datatype second to use
		 */
		public DtTime(DtHour aHour,	DtMinute aMinute,DtSecond aSecond){
			hour = aHour;
			minute = aMinute;
			second = aSecond; 
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString(){
			return hour.toString() + ":" + minute.toString() + ":" + second.toString(); 
		}
}
