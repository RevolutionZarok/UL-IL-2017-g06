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

import java.io.Serializable;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Class CtState which has the systems's status.
 */
public class CtState implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/** The next available value for alert id, this is retrieved from the database at moment of system and environment creation. */
	public DtInteger nextValueForAlertID;
	
	/** The next available value for crisis id, this is retrieved from the database at moment of system and environment creation. */
	public DtInteger nextValueForCrisisID;
	
	/** The current system date and time. */
	public DtDateAndTime clock;
	
	/** The set crisis reminder period, if a crisis hasn't been handled within the period, coordinators will be warned. */
	public DtSecond crisisReminderPeriod;
	
	/** The max crisis reminder period, if a crisis hasn't been handled within the period, coordinators will be warned. */
	public DtSecond maxCrisisReminderPeriod;
	
	/** The last time the coordinators have been reminded about outstanding crisises. */
	public DtDateAndTime vpLastReminder;
	
	/**  If the system has been started or not. */
	public PtBoolean vpStarted;
			
	/**
	 * Initialises the system's state.
	 *
	 * @param aNextValueForAlertID The next index value available for the alert ID
	 * @param aNextValueForCrisisID The next index value available for the crisis ID
	 * @param aClock The current system date and time
	 * @param aCrisisReminderPeriod The set crisis reminder period, if a crisis hasn't been handled within the period, coordinators will be warned.
	 * @param aMaxCrisisReminderPeriod The max crisis reminder period, if a crisis hasn't been handled within the period, coordinators will be warned.
	 * @param aVpLastReminder The last time the coordinators have been reminded about outstanding crisises.
	 * @param aVpStarted If the system has been started or not
	 * @return Success of the initialisation
	 */
	public PtBoolean init(DtInteger aNextValueForAlertID, DtInteger aNextValueForCrisisID, 
						DtDateAndTime aClock, DtSecond aCrisisReminderPeriod, 
						DtSecond aMaxCrisisReminderPeriod, DtDateAndTime aVpLastReminder, 
						PtBoolean aVpStarted){
	
	
				nextValueForAlertID = aNextValueForAlertID;
				nextValueForCrisisID = aNextValueForCrisisID;
				clock = aClock;
				crisisReminderPeriod = aCrisisReminderPeriod;
				maxCrisisReminderPeriod = aMaxCrisisReminderPeriod;
				vpLastReminder = aVpLastReminder;
				vpStarted = aVpStarted;
				
				return new PtBoolean(true);
	}
}
