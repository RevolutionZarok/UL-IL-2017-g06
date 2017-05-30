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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtFamilyComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLatitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLongitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtVictimFirstName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtVictimLastName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.secondary.DtSMS;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;

/**
 * The Class DbAlerts for updating and retrieving information from the table Alerts in the database.
 */
public class DbAlerts extends DbAbstract {


	/**
	 * Count the number of alerts registered into the database.
	 *
	 */
	static public int countAlerts() {

		int answer = 0; 
		try{

			conn = DriverManager.getConnection(url + dbName, userName, password);
			try{

				String sql = "SELECT COUNT(id)  AS numberOfAlertsl FROM "+ dbName + ".alerts" ;


				Statement statement = conn.createStatement();
				ResultSet  res = statement.executeQuery(sql);

				if (res.next())
					answer = res.getInt("numberOfAlertsl");	

			}catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			} catch (Exception e) {
			logException(e);
		}
		return answer;

	}



	/**
	 * Insert an alert into the database.
	 *
	 * @param aCtAlert the class of the alert to insert
	 */
	static public void insertAlert(CtAlert aCtAlert) {
		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Insert

			try {
				Statement st = conn.createStatement();

				String id = aCtAlert.id.value.getValue();
				String status = aCtAlert.status.toString();
				double latitude = aCtAlert.location.latitude.value.getValue();
				double longitude = aCtAlert.location.longitude.value.getValue();

				int year = aCtAlert.instant.date.year.value.getValue();
				int month = aCtAlert.instant.date.month.value.getValue();
				int day = aCtAlert.instant.date.day.value.getValue();

				int hour = aCtAlert.instant.time.hour.value.getValue();
				int min = aCtAlert.instant.time.minute.value.getValue();
				int sec = aCtAlert.instant.time.second.value.getValue();

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Calendar calendar = new GregorianCalendar(year, month, day,
						hour, min, sec);
				String instant = sdf.format(calendar.getTime());

				String comment = aCtAlert.comment.value.getValue();
				
				String familyComment = aCtAlert.familyComment.value.getValue();
				
				String victimFirstName = aCtAlert.victimFirstName.value.getValue();
				
				String victimLastName = aCtAlert.victimLastName.value.getValue();

				log.debug("[DATABASE]-Insert alert");
				int val = st.executeUpdate("INSERT INTO " + dbName + ".alerts"
						+ "(id,status,latitude,longitude,instant,comment,familyComment,victimFirstName,victimLastName)"
						+ "VALUES(" + "'" + id + "'" + ",'" + status + "', "
						+ latitude + ", " + longitude + ", '" + instant + "','"
						+ comment + "','" + familyComment + "','" + victimFirstName
						+ "','" + victimLastName + "')");

				log.debug(val + " row affected");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}

	}

	/**
	 * Gets an alert from the database by the alert ID.
	 *
	 * @param alertId The ID of the alert to retrieve from the database
	 * @return the class of the alert to retrieve
	 */
	static public CtAlert getAlert(String alertId) {

		CtAlert aCtAlert = new CtAlert();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select
			try {
				String sql = "SELECT * FROM " + dbName + ".alerts  ORDER BY instant DESC WHERE id = "
						+ alertId;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				if (res.next()) {

					aCtAlert = new CtAlert();
					//alert's id
					DtAlertID aId = new DtAlertID(new PtString(
							res.getString("id")));

					//alert's status -> [pending, valid, invalid]
					String theStatus = res.getString("status");
					EtAlertStatus aStatus = null;
					if (theStatus.equals(EtAlertStatus.pending.name()))
						aStatus = EtAlertStatus.pending;
					if (theStatus.equals(EtAlertStatus.invalid.name()))
						aStatus = EtAlertStatus.invalid;
					if (theStatus.equals(EtAlertStatus.valid.name()))
						aStatus = EtAlertStatus.valid;

					//alert's location
					DtLatitude aDtLatitude = new DtLatitude(new PtReal(
							res.getDouble("latitude")));
					DtLongitude aDtLongitude = new DtLongitude(new PtReal(
							res.getDouble("longitude")));
					DtGPSLocation aDtGPSLocation = new DtGPSLocation(
							aDtLatitude, aDtLongitude);

					//alert's instant
					Timestamp instant = res.getTimestamp("instant");
					Calendar cal = Calendar.getInstance();
					cal.setTime(instant);

					int d = cal.get(Calendar.DATE);
					int m = cal.get(Calendar.MONTH);
					int y = cal.get(Calendar.YEAR);
					DtDate aDtDate = ICrashUtils.setDate(y, m, d);
					int h = cal.get(Calendar.HOUR_OF_DAY);
					int min = cal.get(Calendar.MINUTE);
					int sec = cal.get(Calendar.SECOND);
					DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
					DtDateAndTime aInstant = new DtDateAndTime(aDtDate, aDtTime);
					

					//alert's comment  
					DtComment aDtComment = new DtComment(new PtString(
							res.getString("comment")));
					
					DtFamilyComment aDtFamilyComment = new DtFamilyComment(new PtString(res.getString("familyComment")));
					
					DtVictimFirstName aDtVictimFirstName = new DtVictimFirstName(new PtString(res.getString("victimFirstName")));
					
					DtVictimLastName aDtVictimLastName = new DtVictimLastName(new PtString(res.getString("victimLastName")));
					
					aCtAlert.init(aId, aStatus, aDtGPSLocation, aInstant,
							aDtComment, aDtFamilyComment,
							aDtVictimFirstName, aDtVictimLastName);

				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return aCtAlert;

	}

	/**
	 * Gets the current highest number used for an alert ID in the database.
	 *
	 * @return the highest number for an alert id
	 */
	static public int getMaxAlertID() {

		int maxAlertId = 0;

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT MAX(CAST(id AS UNSIGNED)) FROM " + dbName
						+ ".alerts";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				if (res.next()) {
					maxAlertId = res.getInt(1);
				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return maxAlertId;

	}

	/**
	 * Gets all of the alerts currently in the database.
	 *
	 * @return a hashtable of the alerts using the ID of the alert as a key
	 */
	static public Hashtable<String, CtAlert> getSystemAlerts() {

		Hashtable<String, CtAlert> cmpSystemCtAlert = new Hashtable<String, CtAlert>();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".alerts ORDER BY instant ASC";
				
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtAlert aCtAlert = null;

				while (res.next()) {

					aCtAlert = new CtAlert();
					//alert's id
					DtAlertID aId = new DtAlertID(new PtString(
							res.getString("id")));

					//alert's status -> [pending, valid, invalid]
					String theStatus = res.getString("status");
					EtAlertStatus aStatus = null;
					if (theStatus.equals(EtAlertStatus.pending.name()))
						aStatus = EtAlertStatus.pending;
					if (theStatus.equals(EtAlertStatus.invalid.name()))
						aStatus = EtAlertStatus.invalid;
					if (theStatus.equals(EtAlertStatus.valid.name()))
						aStatus = EtAlertStatus.valid;

					//alert's location
					DtLatitude aDtLatitude = new DtLatitude(new PtReal(
							res.getDouble("latitude")));
					DtLongitude aDtLongitude = new DtLongitude(new PtReal(
							res.getDouble("longitude")));
					DtGPSLocation aDtGPSLocation = new DtGPSLocation(
							aDtLatitude, aDtLongitude);

					//alert's instant
					Timestamp instant = res.getTimestamp("instant");
					Calendar cal = Calendar.getInstance();
					cal.setTime(instant);

					int d = cal.get(Calendar.DATE);
					int m = cal.get(Calendar.MONTH);
					int y = cal.get(Calendar.YEAR);
					DtDate aDtDate = ICrashUtils.setDate(y, m, d);
					int h = cal.get(Calendar.HOUR_OF_DAY);
					int min = cal.get(Calendar.MINUTE);
					int sec = cal.get(Calendar.SECOND);
					DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
					DtDateAndTime aInstant = new DtDateAndTime(aDtDate, aDtTime);

					//alert's comment  
					DtComment aDtComment = new DtComment(new PtString(
							res.getString("comment")));
					
					DtFamilyComment aDtFamilyComment = new DtFamilyComment(new PtString(res.getString("familyComment")));
					
					DtVictimFirstName aDtVictimFirstName = new DtVictimFirstName(new PtString(res.getString("victimFirstName")));
					
					DtVictimLastName aDtVictimLastName = new DtVictimLastName(new PtString(res.getString("victimLastName")));

					//init aCtAlert instance
					aCtAlert.init(aId, aStatus, aDtGPSLocation, aInstant,
							aDtComment, aDtFamilyComment, aDtVictimFirstName, aDtVictimLastName);

					//add instance to the hash
					cmpSystemCtAlert
					.put(aCtAlert.id.value.getValue(), aCtAlert);

				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return cmpSystemCtAlert;

	}

	/**
	 * Gets the Alerts and their associated crises and inserts them into a hashtable, using the alert as a key.
	 *
	 * @return the hashtable of the associated crises and alerts
	 */
	static public Hashtable<CtAlert, CtCrisis> getAssCtAlertCtCrisis() {

		Hashtable<CtAlert, CtCrisis> assCtAlertCtCrisis = new Hashtable<CtAlert, CtCrisis>();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".alerts "
						+ "INNER JOIN " + dbName + ".crises ON " + dbName
						+ ".alerts.crisis = " + dbName + ".crises.id";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtAlert aCtAlert = null;
				CtCrisis aCtCrisis = null;

				while (res.next()) {

					aCtAlert = new CtAlert();
					//alert's id
					DtAlertID aId = new DtAlertID(new PtString(
							res.getString("alerts.id")));

					//alert's status -> [pending, valid, invalid]
					String theStatus = res.getString("alerts.status");
					EtAlertStatus aStatus = null;
					if (theStatus.equals(EtAlertStatus.pending.name()))
						aStatus = EtAlertStatus.pending;
					if (theStatus.equals(EtAlertStatus.invalid.name()))
						aStatus = EtAlertStatus.invalid;
					if (theStatus.equals(EtAlertStatus.valid.name()))
						aStatus = EtAlertStatus.valid;

					//alert's location
					DtLatitude aDtLatitude = new DtLatitude(new PtReal(
							res.getDouble("alerts.latitude")));
					DtLongitude aDtLongitude = new DtLongitude(new PtReal(
							res.getDouble("alerts.longitude")));
					DtGPSLocation aDtGPSLocation = new DtGPSLocation(
							aDtLatitude, aDtLongitude);

					//alert's instant
					Timestamp instant = res.getTimestamp("alerts.instant");
					Calendar cal = Calendar.getInstance();
					cal.setTime(instant);

					int d = cal.get(Calendar.DATE);
					int m = cal.get(Calendar.MONTH);
					int y = cal.get(Calendar.YEAR);
					DtDate aDtDate = ICrashUtils.setDate(y, m, d);
					int h = cal.get(Calendar.HOUR_OF_DAY);
					int min = cal.get(Calendar.MINUTE);
					int sec = cal.get(Calendar.SECOND);
					DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
					DtDateAndTime aInstant = new DtDateAndTime(aDtDate, aDtTime);

					//alert's comment  
					DtComment aDtComment = new DtComment(new PtString(
							res.getString("alerts.comment")));
					
					DtFamilyComment aDtFamilyComment = new DtFamilyComment(new PtString(res.getString("alerts.familyComment")));
					
					DtVictimFirstName aDtVictimFirstName = new DtVictimFirstName(new PtString(res.getString("alerts.victimFirstName")));
					
					DtVictimLastName aDtVictimLastName = new DtVictimLastName(new PtString(res.getString("alerts.victimLastName")));

					//init aCtAlert instance
					aCtAlert.init(aId, aStatus, aDtGPSLocation, aInstant,
							aDtComment, aDtFamilyComment, aDtVictimFirstName, aDtVictimLastName);

					//*************************************
					aCtCrisis = new CtCrisis();
					//crisis's id
					DtCrisisID aCrisisId = new DtCrisisID(new PtString(
							res.getString("crises.id")));

					//crisis' type -> [small, medium, huge]
					String theCrisisType = res.getString("crises.type");
					EtCrisisType aCrisisType = null;
					if (theCrisisType.equals(EtCrisisType.small.name()))
						aCrisisType = EtCrisisType.small;
					if (theCrisisType.equals(EtCrisisType.medium.name()))
						aCrisisType = EtCrisisType.medium;
					if (theCrisisType.equals(EtCrisisType.huge.name()))
						aCrisisType = EtCrisisType.huge;

					//crisis's status -> [pending, handled, solved, closed]
					String theCrisisStatus = res.getString("crises.status");
					EtCrisisStatus aCrisisStatus = null;
					if (theCrisisStatus.equals(EtCrisisStatus.pending.name()))
						aCrisisStatus = EtCrisisStatus.pending;
					if (theCrisisStatus.equals(EtCrisisStatus.handled.name()))
						aCrisisStatus = EtCrisisStatus.handled;
					if (theCrisisStatus.equals(EtCrisisStatus.solved.name()))
						aCrisisStatus = EtCrisisStatus.solved;
					if (theCrisisStatus.equals(EtCrisisStatus.closed.name()))
						aCrisisStatus = EtCrisisStatus.closed;

					//crisis's location
					DtLatitude aCrisisDtLatitude = new DtLatitude(new PtReal(
							res.getDouble("crises.latitude")));
					DtLongitude aCrisisDtLongitude = new DtLongitude(
							new PtReal(res.getDouble("crises.longitude")));
					DtGPSLocation aCrisisDtGPSLocation = new DtGPSLocation(
							aCrisisDtLatitude, aCrisisDtLongitude);

					//crisis's instant
					instant = res.getTimestamp("crises.instant");
					cal.setTime(instant);

					d = cal.get(Calendar.DATE);
					m = cal.get(Calendar.MONTH);
					y = cal.get(Calendar.YEAR);
					aDtDate = ICrashUtils.setDate(y, m, d);
					h = cal.get(Calendar.HOUR_OF_DAY);
					min = cal.get(Calendar.MINUTE);
					sec = cal.get(Calendar.SECOND);
					aDtTime = ICrashUtils.setTime(h, min, sec);
					DtDateAndTime aCrisisInstant = new DtDateAndTime(aDtDate,
							aDtTime);

					//crisis's comment  
					DtComment aCrisisDtComment = new DtComment(new PtString(
							res.getString("crises.comment")));
					
					DtFamilyComment aFamilyComment = new DtFamilyComment(new PtString(res.getString("crises.familyComment")));
					
					DtVictimFirstName aVictimFirstName = new DtVictimFirstName(new PtString(res.getString("crises.victimFirstName")));
					DtVictimLastName aVictimLastName = new DtVictimLastName(new PtString(res.getString("crises.victimLastName")));

					aCtCrisis.init(aCrisisId, aCrisisType, aCrisisStatus,
							aCrisisDtGPSLocation, aCrisisInstant,
							aCrisisDtComment, aFamilyComment, aVictimFirstName,
							aVictimLastName);

					//add instances to the hash
					assCtAlertCtCrisis.put(aCtAlert, aCtCrisis);

				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return assCtAlertCtCrisis;

	}

	/**
	 * Gets alerts and their associated humans and inserts them into a hashtable, using the alert as a key.
	 *
	 * @return the hashtable of the associated humans and alerts
	 */
	static public Hashtable<CtAlert, CtHuman> getAssCtAlertCtHuman() {

		Hashtable<CtAlert, CtHuman> assCtAlertCtHuman = new Hashtable<CtAlert, CtHuman>();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".alerts "
						+ "INNER JOIN " + dbName + ".humans ON " + dbName
						+ ".alerts.human = " + dbName + ".humans.phone";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtAlert aCtAlert = null;
				CtHuman aCtHuman = null;

				while (res.next()) {

					aCtAlert = new CtAlert();
					//alert's id
					DtAlertID aId = new DtAlertID(new PtString(
							res.getString("alerts.id")));

					//alert's status -> [pending, valid, invalid]
					String theStatus = res.getString("alerts.status");
					EtAlertStatus aStatus = null;
					if (theStatus.equals(EtAlertStatus.pending.name()))
						aStatus = EtAlertStatus.pending;
					if (theStatus.equals(EtAlertStatus.invalid.name()))
						aStatus = EtAlertStatus.invalid;
					if (theStatus.equals(EtAlertStatus.valid.name()))
						aStatus = EtAlertStatus.valid;

					//alert's location
					DtLatitude aDtLatitude = new DtLatitude(new PtReal(
							res.getDouble("alerts.latitude")));
					DtLongitude aDtLongitude = new DtLongitude(new PtReal(
							res.getDouble("alerts.longitude")));
					DtGPSLocation aDtGPSLocation = new DtGPSLocation(
							aDtLatitude, aDtLongitude);

					//alert's instant
					Timestamp instant = res.getTimestamp("alerts.instant");
					Calendar cal = Calendar.getInstance();
					cal.setTime(instant);

					int d = cal.get(Calendar.DATE);
					int m = cal.get(Calendar.MONTH);
					int y = cal.get(Calendar.YEAR);
					DtDate aDtDate = ICrashUtils.setDate(y, m, d);
					int h = cal.get(Calendar.HOUR_OF_DAY);
					int min = cal.get(Calendar.MINUTE);
					int sec = cal.get(Calendar.SECOND);
					DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
					DtDateAndTime aInstant = new DtDateAndTime(aDtDate, aDtTime);

					//alert's comment  
					DtComment aDtComment = new DtComment(new PtString(
							res.getString("alerts.comment")));
					
					DtFamilyComment aDtFamilyComment = new DtFamilyComment(new PtString(res.getString("comment")));
					
					DtVictimFirstName aDtVictimFirstName = new DtVictimFirstName(new PtString(res.getString("first name")));
					
					DtVictimLastName aDtVictimLastName = new DtVictimLastName(new PtString(res.getString("last name")));

					//init aCtAlert instance
					aCtAlert.init(aId, aStatus, aDtGPSLocation, aInstant,
							aDtComment, aDtFamilyComment, aDtVictimFirstName, aDtVictimLastName);

					//*************************************
					aCtHuman = new CtHuman();
					//human's id
					DtPhoneNumber aId1 = new DtPhoneNumber(new PtString(
							res.getString("phone")));
					//human's kind  -> [witness,victim,anonym]
					String theKind = res.getString("kind");
					EtHumanKind aKind = null;
					if (theKind.equals(EtHumanKind.witness.name()))
						aKind = EtHumanKind.witness;
					if (theKind.equals(EtHumanKind.victim.name()))
						aKind = EtHumanKind.victim;
					if (theKind.equals(EtHumanKind.anonym.name()))
						aKind = EtHumanKind.anonym;

					aCtHuman.init(aId1, aKind);

					//add instances to the hash
					assCtAlertCtHuman.put(aCtAlert, aCtHuman);

				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return assCtAlertCtHuman;

	}

	/**
	 * Deletes an alert from the database, it will use the ID from the CtAlert to delete it.
	 *
	 * @param aCtAlert The alert to delete from the database
	 */
	static public void deleteAlert(CtAlert aCtAlert) {

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Delete

			try {
				String sql = "DELETE FROM " + dbName + ".alerts WHERE id = ?";
				String id = aCtAlert.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row deleted");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}

	/**
	 * Binds a crisis onto an alert in the database.
	 *
	 * @param aCtAlert The alert to bind the crisis to
	 * @param aCtCrisis The crisis to bind to to the alert
	 */
	static public void bindAlertCrisis(CtAlert aCtAlert, CtCrisis aCtCrisis) {
		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				String sql = "UPDATE " + dbName
						+ ".alerts SET crisis =? WHERE id = ?";
				String id = aCtAlert.id.value.getValue();
				String crisiId = aCtCrisis.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, crisiId);
				statement.setString(2, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row affected");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}

	}

	/**
	 * Binds a human onto an alert in the database.
	 *
	 * @param aCtAlert The alert to bind the human to
	 * @param aCtHuman The human to bind to the alert
	 */
	static public void bindAlertHuman(CtAlert aCtAlert, CtHuman aCtHuman) {
		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				String sql = "UPDATE " + dbName
						+ ".alerts SET human =? WHERE id = ?";
				String id = aCtAlert.id.value.getValue();
				String humanPhone = aCtHuman.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, humanPhone);
				statement.setString(2, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row affected");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}

	}

	/**
	 * Updates a alert in the database to have the same details as the CtAlert
	 * It will update the alert with the same ID as the ID in the CtAlert.
	 *
	 * @param aCtAlert The alert to update
	 */
	static public void updateAlert(CtAlert aCtAlert) {

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				log.debug("[DATABASE]-Update alert");
				String sql = "UPDATE "
						+ dbName
						+ ".alerts SET `status` = ?, `latitude` = ?, `longitude` = ?,"
						+ " `instant` = ?, `comment` = ?, `familyComment` = ?, `victimFirstName` = ?,"
						+ " `victimLastName` = ? WHERE id = ?";
				String id = aCtAlert.id.value.getValue();
				String status = aCtAlert.status.toString();
				double latitude = aCtAlert.location.latitude.value.getValue();
				double longitude = aCtAlert.location.longitude.value.getValue();

				int year = aCtAlert.instant.date.year.value.getValue();
				int month = aCtAlert.instant.date.month.value.getValue();
				int day = aCtAlert.instant.date.day.value.getValue();

				int hour = aCtAlert.instant.time.hour.value.getValue();
				int min = aCtAlert.instant.time.minute.value.getValue();
				int sec = aCtAlert.instant.time.second.value.getValue();

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Calendar calendar = new GregorianCalendar(year, month, day,
						hour, min, sec);
				String instant = sdf.format(calendar.getTime());

				String comment = aCtAlert.comment.value.getValue();
				
				String familyComment = aCtAlert.familyComment.value.getValue();
				
				String victimFirstName = aCtAlert.victimFirstName.value.getValue();
				String victimLastName = aCtAlert.victimLastName.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, status);
				statement.setDouble(2, latitude);
				statement.setDouble(3, longitude);
				statement.setString(4, instant);
				statement.setString(5, comment);
				statement.setString(6, familyComment);
				statement.setString(7, victimFirstName);
				statement.setString(8, victimLastName);
				statement.setString(9, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row affected");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}

	}
	
	public static String getAlertInfo(CtAlert theAlert) {
		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			Statement statement = null;

			try{
				log.debug("[DATABASE]-Get notification information");
				String query = "SELECT familyComment, victimFirstName, victimLastName FROM"
						+ dbName
						+ ".alerts WHERE id = " + theAlert.id.value.getValue() + ";";
				ResultSet rs = statement.executeQuery(query);
				String familyComment  = rs.getString("familyComment");
				String victimName = rs.getString("victimFirstName") + " " + rs.getString("victimLastName");	
				
				DtSMS sms = new DtSMS(new PtString(victimName + " has been in an accident. Here's his message:\n\n" + familyComment));
				
				return sms.value.getValue();
			}catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}finally {
		        if (statement != null) { statement.close(); }
		    }
			
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
		return "Error in DbAlerts";

	}

}
