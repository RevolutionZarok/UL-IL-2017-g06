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
 * A datatype that stores a date.
 */
public class DtDate implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/** The year. */
		public DtYear year;
		
		/** The month. */
		public DtMonth month;
		
		/** The day. */
		public DtDay day;


		/**
		 * Creates a new DtDate with the year, month and day provided.
		 *
		 * @param aYear The year to use in the date
		 * @param aMonth The month to use in the date
		 * @param aDay The day to use in the date
		 */
		public DtDate(DtYear aYear,DtMonth aMonth,DtDay aDay){
				year = aYear;
				month = aMonth;
				day = aDay;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString(){
			String test =year.toString() + "/" + month.toString() + "/" + day.toString(); 
			return  test;
		}
}
