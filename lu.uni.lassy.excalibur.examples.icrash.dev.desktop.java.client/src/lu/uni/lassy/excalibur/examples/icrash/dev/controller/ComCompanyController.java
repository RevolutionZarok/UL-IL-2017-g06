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
package lu.uni.lassy.excalibur.examples.icrash.dev.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.InvalidHumanKindException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.StringToNumberException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLatitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLongitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDay;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtHour;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtMinute;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtMonth;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtYear;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyComCompanyImpl;

/**
 * The ComCompany controller deals with any functions to do with communication companies. This entails retrieval,
 * of data. It extends the AbstractController to handle checking of Dt information is correct
 */
public class ComCompanyController implements HasListeners{
	
	/**
	 * Instantiates a new communication company controller.
	 *
	 * @param aActComCompany the a act com company
	 * @throws ServerOfflineException Thrown if the server is offline
	 * @throws ServerNotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public ComCompanyController(ActComCompany aActComCompany) throws ServerOfflineException, ServerNotBoundException
	{
		try {
			 aActProxyComCompany= new ActProxyComCompanyImpl(aActComCompany);
			}
		catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/** The a act proxy com company. */
	private ActProxyComCompany aActProxyComCompany;
	
	/**
	 * Gets the auth.
	 *
	 * @return the auth
	 */
	public ActProxyComCompanyImpl getAuth(){
		return (ActProxyComCompanyImpl)aActProxyComCompany;
	}
	
	/**
	 * Checks the data passed is correct and if so, will create an alert in the system.
	 *
	 * @param aEtHumanKind the a et human kind
	 * @param year is the year that the accident took place
	 * @param month is the month that the accident took place
	 * @param day is the day of the month that the accident took place
	 * @param hour is the hour of the clock at the time that the accident took place
	 * @param minute is the minute of the clock at the time that the accident took place
	 * @param second is the second of the clock at the time that the accident took place
	 * @param phoneNumber is the contact phone number of the person reporting the accident to the iCrashSystem.
	 * @param latitude is the latitude point of where the accident happened
	 * @param longitude is the longitude point of where the accident happened
	 * @param comment is the information conveyed in the received SMS sent by the human (victim, witness, or anonymous)
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws InvalidHumanKindException is thrown when the enum type of HumanKind does not match the specification
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 * @throws StringToNumberException the string to number exception
	 */
	public PtBoolean oeAlert(EtHumanKind aEtHumanKind, int year, int month, int day, int hour, int minute, int second,
			String phoneNumber, String latitude, String longitude, String comment) throws ServerOfflineException, InvalidHumanKindException, ServerNotBoundException, IncorrectFormatException, StringToNumberException{
		try {
			if (aActProxyComCompany == null)
				return new PtBoolean(false);
			double dblLatitude = Double.parseDouble(latitude);
			double dblLongitude = Double.parseDouble(longitude);
			DtGPSLocation aDtGPSLocation = new DtGPSLocation(new DtLatitude(new PtReal(dblLatitude)), new DtLongitude(new PtReal(dblLongitude)));
			DtPhoneNumber aDtPhoneNumber = new DtPhoneNumber(new PtString(phoneNumber));
			DtComment aDtComment = new DtComment(new PtString(comment));
			DtDate aDtDate = new DtDate(new DtYear(new PtInteger(year)), new DtMonth(new PtInteger(month)), new DtDay(new PtInteger(day)));
			DtTime aDtTime = new DtTime(new DtHour(new PtInteger(hour)), new DtMinute(new PtInteger(minute)), new DtSecond(new PtInteger(second)));
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aDtGPSLocation.latitude, Double.toString(aDtGPSLocation.latitude.value.getValue()));
			ht.put(aDtGPSLocation.longitude, Double.toString(aDtGPSLocation.longitude.value.getValue()));
			ht.put(aEtHumanKind, aEtHumanKind.name());
			ht.put(aDtPhoneNumber, aDtPhoneNumber.value.getValue());
			ht.put(aDtComment, aDtComment.value.getValue());
			return aActProxyComCompany.oeAlert(aEtHumanKind, aDtDate, aDtTime, aDtPhoneNumber, aDtGPSLocation, aDtComment);
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		} catch (NumberFormatException e){
			Log4JUtils.getInstance().getLogger().error(e);
			throw new StringToNumberException("Longitude: " + longitude + " and latitude: " + latitude);
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.controller.HasListeners#removeAllListeners()
	 */
	public void removeAllListeners() throws ServerOfflineException, ServerNotBoundException {
		try {
			((ActProxyComCompanyImpl)aActProxyComCompany).destroy();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
}
