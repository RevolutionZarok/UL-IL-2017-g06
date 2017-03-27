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

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbAlerts;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLatitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLongitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

import org.apache.log4j.Logger;

/**
 * The Class that tests the database table of alerts.
 */
public class TestCase_db_table_alerts {

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
		DtAlertID aId = new DtAlertID(new PtString("3"));
		
		//**********************************************************
		//set up status
		EtAlertStatus aStatus = EtAlertStatus.pending;

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
		DtComment aDtComment = new DtComment(new PtString("13 cars involved in an accident."));
		
		
		CtAlert aCtAlert = new CtAlert();
		aCtAlert.init(aId, aStatus,aDtGPSLocation,aInstant, aDtComment);
		
		DbAlerts.insertAlert(aCtAlert);
		

		log.info("---- test select -------");		
		CtAlert aCtAlert2 = DbAlerts.getAlert(aId.value.getValue());
		log.debug("The retrieved alert's id is " + aCtAlert2.id.value.getValue());
		log.debug("The retrieved alert's status is " + aCtAlert2.status.toString());
		log.debug("The retrieved alert's comment is " + aCtAlert2.comment.value.getValue());
		
		
		
		log.info("---- test binding Alert with Crisis -------");
		CtCrisis aCtCrisis = new CtCrisis();
		DtCrisisID acId = new DtCrisisID(new PtString("1"));
		EtCrisisType acType = EtCrisisType.small;
		EtCrisisStatus acStatus = EtCrisisStatus.pending;
		DtComment acComment = new DtComment(new PtString("no report defined, yet"));
		aCtCrisis.init(acId, acType, acStatus, aDtGPSLocation, aInstant, acComment);
		DbAlerts.bindAlertCrisis(aCtAlert2, aCtCrisis);


		log.info("---- test binding Alert with Human -------");
		CtHuman aCtHuman = new CtHuman();
		EtHumanKind aEtHumanKind = EtHumanKind.witness;
		DtPhoneNumber aDtPhoneNumber = new DtPhoneNumber(new PtString("+3524666445252"));
		aCtHuman.init(aDtPhoneNumber, aEtHumanKind);
		DbAlerts.bindAlertHuman(aCtAlert2, aCtHuman);


		int val = DbAlerts.getMaxAlertID();
		System.out.println("Current Max Alert ID is "+val);

		DbAlerts.deleteAlert(aCtAlert2);
		
	}


}
