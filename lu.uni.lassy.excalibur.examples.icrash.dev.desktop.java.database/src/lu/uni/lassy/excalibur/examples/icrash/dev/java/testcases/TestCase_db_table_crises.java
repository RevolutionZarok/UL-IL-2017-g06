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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLatitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLongitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

import org.apache.log4j.Logger;

/**
 * The Class for testing the database table of crises.
 */
public class TestCase_db_table_crises {

	/** The logger used to log issues or information. */
	static Logger log = Log4JUtils.getInstance().getLogger();
	
	
	/**
	 * The main method, which runs the test.
	 *
	 * @param args the arguments passed to the system, these are ignored
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server is not bound correctly in RMI settings
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {

		log.info("---- test insert-------");
		//**********************************************************
		//set up id
		DtCrisisID aId = new DtCrisisID(new PtString("1"));
		
		//**********************************************************
		//set up crisis' type
		EtCrisisType aType = EtCrisisType.small;
		
		//**********************************************************
		//set up status
		EtCrisisStatus aStatus = EtCrisisStatus.pending;

		//**********************************************************
		//set up location
		DtLatitude aDtLatitude = new DtLatitude(new PtReal(49.627675));
		DtLongitude aDtLongitude = new DtLongitude(new PtReal(6.159590));
		DtGPSLocation aDtGPSLocation = new DtGPSLocation(aDtLatitude,aDtLongitude);
		
		
		//**********************************************************
		//set up instant
		int d,m,y,h,min,sec;
		d=26; m=11;	 y=2017;
		DtDate aDtDate = ICrashUtils.setDate(y, m, d);
		h=10; min=10; sec=16;
		DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
		DtDateAndTime aInstant = new DtDateAndTime(aDtDate,aDtTime);
	
	
		//**********************************************************
		//set up comment
		DtComment aDtComment = new DtComment(new PtString("1 bicycle involved in an accident."));
		
		
		CtCrisis aCtCrisis = new CtCrisis();
		aCtCrisis.init(aId, aType, aStatus,aDtGPSLocation,aInstant, aDtComment);
		
		DbCrises.insertCrisis(aCtCrisis);
		

		log.info("---- test select -------");		
		CtCrisis aCtCrisis2 = DbCrises.getCrisis(aId.value.getValue());
		log.debug("The retrieved crisis' id is " + aCtCrisis2.id.value.getValue());
		log.debug("The retrieved crisis' type is " + aCtCrisis2.type.toString());
		log.debug("The retrieved crisis' status is " + aCtCrisis2.status.toString());
		log.debug("The retrieved crisis' comment is " + aCtCrisis2.comment.value.getValue());
		
		DbCrises.deleteCrisis(aCtCrisis2);
		
	}


}
