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

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbHumans;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

import org.apache.log4j.Logger;

/**
 * The Class used for testing the table of humans in the database.
 */
public class TestCase_db_table_humans {

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
		//set up human's id
		DtPhoneNumber aDtPhoneNumber = new DtPhoneNumber(new PtString("+352621731392"));
		
		//**********************************************************
		//set up human kind
		EtHumanKind aEtHumanKind = EtHumanKind.anonym;

		//**********************************************************
		//set up human communication company
		String comCompany = "orange";
		
		CtHuman aCtHuman = new CtHuman();
		aCtHuman.init(aDtPhoneNumber, aEtHumanKind);
		
		DbHumans.insertHuman(aCtHuman, comCompany);
		

		log.info("---- test select -------");		
		CtHuman aCtHuman2 = DbHumans.getHuman(aDtPhoneNumber.value.getValue());
		log.debug("The retrieved human's phone is " + aCtHuman2.id.value.getValue());
		log.debug("The retrieved human's kind is " + aCtHuman2.kind.toString());
		
		DbHumans.deleteHuman(aCtHuman2);
		
	}


}
