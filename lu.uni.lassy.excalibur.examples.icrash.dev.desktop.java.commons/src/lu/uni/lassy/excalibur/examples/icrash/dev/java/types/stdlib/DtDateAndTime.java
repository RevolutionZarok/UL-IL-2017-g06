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
import java.util.Calendar;
import java.util.TimeZone;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import org.apache.log4j.Logger;
/**
 * A datatype that stores a date and time.
 */
public class DtDateAndTime implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/** The date. */
		public DtDate date;
		
		/** The time. */
		public DtTime time; 

		/**
		 * Instantiates a new datatype to store the date and time provided.
		 *
		 * @param aDate The date to be stored
		 * @param aTime The time to be stored
		 */
		public DtDateAndTime(DtDate aDate, DtTime aTime) {
			date = aDate;
			time = aTime;
		}	
		
		/**
		 * Prints the current date and time to the console.
		 */
		public void show(){
			Logger log = Log4JUtils.getInstance().getLogger();
			log.info(this.toString());
		
		}

		/**
		 * Gets the current date and time and puts it down to the seconds passed since Epoch for math equations.
		 *
		 * @return Returns a PtInteger of the number of seconds it has been since the Epoch
		 */
		public PtInteger toSecondsQty(){
			//Note: java's epoch is UTC 1/1/1970 
			
			int d = date.day.value.value;
			int m = date.month.value.value;
			int y = date.year.value.value;
			
			int h = time.hour.value.value;
			int min = time.minute.value.value;
			int sec = time.second.value.value;
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			calendar.clear();
			calendar.set(y, m, d, h, min, sec);
			double secondsSinceEpoch = calendar.getTimeInMillis() / 1000L; 
			int res = (int)secondsSinceEpoch;
			return new PtInteger(res);
		
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString(){
			return date.toString() + " - " + time.toString();
		}
}
