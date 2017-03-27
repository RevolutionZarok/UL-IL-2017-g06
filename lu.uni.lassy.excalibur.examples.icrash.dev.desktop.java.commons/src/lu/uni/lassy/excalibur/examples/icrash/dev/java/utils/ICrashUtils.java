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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.utils;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDay;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtHour;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtMinute;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtMonth;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtYear;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;

/**
 * The Class ICrashUtils which holds generic methods that are useful.
 */
public class ICrashUtils {
	
	/** The default location storing the iCrash common properties. Stores things like the database name, the address of the server, etc. */
	public static final String ICRASH_PROPERTIES_FILE = "iCrashState.properties";
	/**
	 *  The default location storing the iCrash common properties. Stores things like the database name, the address of the server, etc.
	 *  This one is external however, allowing the user to edit it
	 *   */
	public static final String ICRASH_PROPERTIES_FILE_EXT = "./iCrashState.properties";
		/**
	 * Gets the current date and time of the PC running the system.
	 *
	 * @return the current date and time as a datatype DtDateAndTime
	 */
	static public DtDateAndTime getCurrentDateAndTime(){
		return new DtDateAndTime(getCurrentDate(),getCurrentTime());
	}
	/**
	 * Gets the current date of the PC running the system.
	 *
	 * @return the current date as a datatype of DtDate
	 */
	static public DtDate getCurrentDate(){
		Calendar cal = Calendar.getInstance();
		DtDay currDay = new DtDay(new PtInteger(cal.get(Calendar.DAY_OF_MONTH))); 
		/**
		 * Month is retrieved as a zero index, don't ask why, Java being special....
		 * http://stackoverflow.com/questions/344380/why-is-january-month-0-in-java-calendar
		 */
		int month = (cal.get(Calendar.MONTH));
		DtMonth currMonth = new DtMonth(new PtInteger(++month));
		DtYear currYear = new DtYear(new PtInteger(cal.get(Calendar.YEAR)));
		return new DtDate(currYear,currMonth,currDay);
	}
	/**
	 * Gets the current time of the PC running the system.
	 *
	 * @return the current time as a datatype of DtTime
	 */
	static public DtTime getCurrentTime(){
		Calendar cal = Calendar.getInstance();
		DtHour currHour = new DtHour(new PtInteger(cal.get(Calendar.HOUR_OF_DAY))); 
		DtMinute currMinute = new DtMinute(new PtInteger(cal.get(Calendar.MINUTE)));
		DtSecond currSecond = new DtSecond(new PtInteger(cal.get(Calendar.SECOND)));
		return new DtTime(currHour,currMinute,currSecond);
	}
	/**
	 * Creates a DtDateAndTime using the parameters passed.
	 *
	 * @param y the year
	 * @param m the month
	 * @param d the day of the month
	 * @param h the hour of the day
	 * @param min the minute
	 * @param sec the second
	 * @return the DtDateAndTime created from the parameters provided
	 */
	static public DtDateAndTime setDateAndTime(int y, int m, int d, int h, int min, int sec){
		return new DtDateAndTime(setDate(y,m,d),setTime(h,min,sec));
	}
	/**
	 * Creates a DtDate using the parameters passed.
	 *
	 * @param y the year
	 * @param m the month
	 * @param d the day of the month
	 * @return the DtDate created from the parameters provided
	 */
	static public DtDate setDate(int y, int m, int d){
		DtDay currDay = new DtDay(new PtInteger(d)); 
		DtMonth currMonth = new DtMonth(new PtInteger(m));
		DtYear currYear = new DtYear(new PtInteger(y));
		return new DtDate(currYear,currMonth,currDay);
	}
	/**
	 * Creates a DtTime using the parameters passed.
	 *
	 * @param h the hour of the day
	 * @param min the minute
	 * @param sec the second
	 * @return the DtTime created from the parameters provided
	 */
	static public DtTime setTime(int h, int min, int sec){
		DtHour currHour = new DtHour(new PtInteger(h)); 
		DtMinute currMinute = new DtMinute(new PtInteger(min));
		DtSecond currSecond = new DtSecond(new PtInteger(sec));
		return new DtTime(currHour,currMinute,currSecond);
	}
	/**
	 * Returns a DtDateAndTime of the date 1970/01/01 00:00:00.
	 *
	 * @return A DtDateAndTime
	 */
	static public DtDateAndTime getMinimumDateAndTime(){
		return setDateAndTime(1970, 1, 1, 0, 0, 0);
	}
	
	/**
	 * Turns a string into a DtDate type, it can thrown an exception if the parsing fails.
	 *
	 * @param value The string value to convert
	 * @return The finished DtDate returned
	 * @throws DateTimeParseException Thrown if the parsing of the string fails
	 */
	static public DtDate stringToDtDate(String value) throws DateTimeParseException{
		LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		return setDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
	}
	
	/**
	 * Turns a string into a DtTime type, it can thrown an exception if the parsing fails.
	 *
	 * @param value The string value to convert
	 * @return The finished DtTime returned
	 * @throws DateTimeParseException Thrown if the parsing of the string fails
	 */
	static public DtTime stringToDtTime(String value) throws DateTimeParseException{
		LocalTime localTime = LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm:ss"));
		return setTime(localTime.getHour(), localTime.getMinute(), localTime.getSecond());
	}
}
