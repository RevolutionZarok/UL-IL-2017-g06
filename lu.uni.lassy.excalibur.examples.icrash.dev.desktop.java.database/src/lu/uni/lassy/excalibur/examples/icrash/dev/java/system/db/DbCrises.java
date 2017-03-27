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
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLatitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLongitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;


/**
 * The Class for updating or retrieving crises from the database.
 */
public class DbCrises extends DbAbstract {

	/**
	 * Inserts a crisis into the database, using the properties from the CtCrisis provided.
	 *
	 * @param aCtCrisis The crisis to insert into the database
	 */
	static public void insertCrisis(CtCrisis aCtCrisis) {

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Insert

			try {
				Statement st = conn.createStatement();

				String id = aCtCrisis.id.value.getValue();
				String type = aCtCrisis.type.toString();
				String status = aCtCrisis.status.toString();
				double latitude = aCtCrisis.location.latitude.value.getValue();
				double longitude = aCtCrisis.location.longitude.value
						.getValue();

				int year = aCtCrisis.instant.date.year.value.getValue();
				int month = aCtCrisis.instant.date.month.value.getValue();
				int day = aCtCrisis.instant.date.day.value.getValue();

				int hour = aCtCrisis.instant.time.hour.value.getValue();
				int min = aCtCrisis.instant.time.minute.value.getValue();
				int sec = aCtCrisis.instant.time.second.value.getValue();

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Calendar calendar = new GregorianCalendar(year, month, day,
						hour, min, sec);
				String instant = sdf.format(calendar.getTime());

				String comment = aCtCrisis.comment.value.getValue();

				log.debug("[DATABASE]-Insert crisis");
				int val = st.executeUpdate("INSERT INTO " + dbName + ".crises"
						+ "(id,type,status,latitude,longitude,instant,comment)"
						+ "VALUES(" + "'" + id + "'" + ",'" + type + "','"
						+ status + "', " + latitude + ", " + longitude + ", '"
						+ instant + "','" + comment + "')");

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
	 * Gets a single crisis from the database with the same ID as the one provided.
	 *
	 * @param crisisId The ID of the crisis to get
	 * @return The data retrieved from the database, this could be empty
	 */
	static public CtCrisis getCrisis(String crisisId) {

		CtCrisis aCtCrisis = new CtCrisis();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".crises WHERE id = "
						+ crisisId;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				if (res.next()) {

					aCtCrisis = new CtCrisis();
					//crisis's id
					DtCrisisID aId = new DtCrisisID(new PtString(
							res.getString("id")));

					//crisis' type -> [small, medium, huge]
					String theType = res.getString("type");
					EtCrisisType aType = null;
					if (theType.equals(EtCrisisType.small.name()))
						aType = EtCrisisType.small;
					if (theType.equals(EtCrisisType.medium.name()))
						aType = EtCrisisType.medium;
					if (theType.equals(EtCrisisType.huge.name()))
						aType = EtCrisisType.huge;

					//crisis's status -> [pending, handled, solved, closed]
					String theStatus = res.getString("status");
					EtCrisisStatus aStatus = null;
					if (theStatus.equals(EtCrisisStatus.pending.name()))
						aStatus = EtCrisisStatus.pending;
					if (theStatus.equals(EtCrisisStatus.handled.name()))
						aStatus = EtCrisisStatus.handled;
					if (theStatus.equals(EtCrisisStatus.solved.name()))
						aStatus = EtCrisisStatus.solved;
					if (theStatus.equals(EtCrisisStatus.closed.name()))
						aStatus = EtCrisisStatus.closed;

					//crisis's location
					DtLatitude aDtLatitude = new DtLatitude(new PtReal(
							res.getDouble("latitude")));
					DtLongitude aDtLongitude = new DtLongitude(new PtReal(
							res.getDouble("longitude")));
					DtGPSLocation aDtGPSLocation = new DtGPSLocation(
							aDtLatitude, aDtLongitude);

					//crisis's instant
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

					//crisis's comment  
					DtComment aDtComment = new DtComment(new PtString(
							res.getString("comment")));

					aCtCrisis.init(aId, aType, aStatus, aDtGPSLocation,
							aInstant, aDtComment);

				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return aCtCrisis;

	}

	/**
	 * Gets the current highest number used for an alert ID in the database.
	 *
	 * @return the highest number for a crisis id
	 */
	static public int getMaxCrisisID() {

		int maxCrisisId = 0;

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT MAX(CAST(id AS UNSIGNED)) FROM " + dbName
						+ ".crises";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				if (res.next()) {
					maxCrisisId = res.getInt(1);
				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return maxCrisisId;

	}

	/**
	 * Gets the associated crises and the coordinators from the database.
	 *
	 * @return The hashtable of the crises and coordinators, using the crisis as a key
	 */
	static public Hashtable<CtCrisis, CtCoordinator> getAssCtCrisisCtCoordinator() {

		Hashtable<CtCrisis, CtCoordinator> assCtCrisisCtCoordinator = new Hashtable<CtCrisis, CtCoordinator>();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".crises "
						+ "INNER JOIN " + dbName + ".coordinators ON " + dbName
						+ ".crises.coordinator = " + dbName
						+ ".coordinators.id";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtCrisis aCtCrisis = null;
				CtCoordinator aCtCoordinator = null;

				while (res.next()) {

					aCtCrisis = new CtCrisis();

					//crisis' id
					DtCrisisID aId = new DtCrisisID(new PtString(
							res.getString("id")));

					//crisis' type -> [small, medium, huge]
					String theType = res.getString("type");
					EtCrisisType aType = null;
					if (theType.equals(EtCrisisType.small.name()))
						aType = EtCrisisType.small;
					if (theType.equals(EtCrisisType.medium.name()))
						aType = EtCrisisType.medium;
					if (theType.equals(EtCrisisType.huge.name()))
						aType = EtCrisisType.huge;

					//crisis' status -> [pending, handled, solved, closed]
					String theStatus = res.getString("status");
					EtCrisisStatus aStatus = null;
					if (theStatus.equals(EtCrisisStatus.pending.name()))
						aStatus = EtCrisisStatus.pending;
					if (theStatus.equals(EtCrisisStatus.handled.name()))
						aStatus = EtCrisisStatus.handled;
					if (theStatus.equals(EtCrisisStatus.solved.name()))
						aStatus = EtCrisisStatus.solved;
					if (theStatus.equals(EtCrisisStatus.closed.name()))
						aStatus = EtCrisisStatus.closed;

					//crisis' location
					DtLatitude aDtLatitude = new DtLatitude(new PtReal(
							res.getDouble("latitude")));
					DtLongitude aDtLongitude = new DtLongitude(new PtReal(
							res.getDouble("longitude")));
					DtGPSLocation aDtGPSLocation = new DtGPSLocation(
							aDtLatitude, aDtLongitude);

					//crisis' instant
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

					//crisis' comment  
					DtComment aDtComment = new DtComment(new PtString(
							res.getString("comment")));

					aCtCrisis.init(aId, aType, aStatus, aDtGPSLocation,
							aInstant, aDtComment);

					//*************************************
					aCtCoordinator = new CtCoordinator();
					//coordinator's id
					DtCoordinatorID aId1 = new DtCoordinatorID(new PtString(
							res.getString("coordiantor")));
					//coordinator's login
					DtLogin aLogin = new DtLogin(new PtString(
							res.getString("login")));
					//coordinator's pwd
					DtPassword aPwd = new DtPassword(new PtString(
							res.getString("pwd")));

					aCtCoordinator.init(aId1, aLogin, aPwd);

					//add instances to the hash
					assCtCrisisCtCoordinator.put(aCtCrisis, aCtCoordinator);

				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return assCtCrisisCtCoordinator;

	}

	/**
	 * Gets all of the crises from the database.
	 *
	 * @return A hashtable of the crises using their ID as a key value
	 */
	static public Hashtable<String, CtCrisis> getSystemCrises() {

		Hashtable<String, CtCrisis> cmpSystemCtCrisis = new Hashtable<String, CtCrisis>();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".crises ";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtCrisis aCtCrisis = null;

				while (res.next()) {

					aCtCrisis = new CtCrisis();
					//crisis' id
					DtCrisisID aId = new DtCrisisID(new PtString(
							res.getString("id")));

					//crisis' type -> [small, medium, huge]
					String theType = res.getString("type");
					EtCrisisType aType = null;
					if (theType.equals(EtCrisisType.small.name()))
						aType = EtCrisisType.small;
					if (theType.equals(EtCrisisType.medium.name()))
						aType = EtCrisisType.medium;
					if (theType.equals(EtCrisisType.huge.name()))
						aType = EtCrisisType.huge;

					//crisis' status -> [pending, valid, invalid]
					String theStatus = res.getString("status");
					EtCrisisStatus aStatus = null;
					if (theStatus.equals(EtCrisisStatus.pending.name()))
						aStatus = EtCrisisStatus.pending;
					if (theStatus.equals(EtCrisisStatus.handled.name()))
						aStatus = EtCrisisStatus.handled;
					if (theStatus.equals(EtCrisisStatus.solved.name()))
						aStatus = EtCrisisStatus.solved;
					if (theStatus.equals(EtCrisisStatus.closed.name()))
						aStatus = EtCrisisStatus.closed;

					//crisis' location
					DtLatitude aDtLatitude = new DtLatitude(new PtReal(
							res.getDouble("latitude")));
					DtLongitude aDtLongitude = new DtLongitude(new PtReal(
							res.getDouble("longitude")));
					DtGPSLocation aDtGPSLocation = new DtGPSLocation(
							aDtLatitude, aDtLongitude);

					//crisis' instant
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

					//crisis' comment  
					DtComment aDtComment = new DtComment(new PtString(
							res.getString("comment")));

					aCtCrisis.init(aId, aType, aStatus, aDtGPSLocation,
							aInstant, aDtComment);

					//add instance to the hash
					cmpSystemCtCrisis.put(aCtCrisis.id.value.getValue(),
							aCtCrisis);

				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return cmpSystemCtCrisis;

	}

	/**
	 * Binds a coordinator to a crisis.
	 *
	 * @param aCtCrisis The crisis to bind the coordinator to
	 * @param aCtCoordinator The coordinator to bind to the crisis
	 */
	static public void bindCrisisCoordinator(CtCrisis aCtCrisis,
			CtCoordinator aCtCoordinator) {
		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				String sql = "UPDATE " + dbName
						+ ".crises SET coordinator =? WHERE id = ?";
				String id = aCtCrisis.id.value.getValue();
				String coordinatorId = aCtCoordinator.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, coordinatorId);
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
	 * Delete a crisis from the database.
	 *
	 * @param aCtCrisis The crisis to delete from a database, it will use the ID to delete from the database
	 */
	static public void deleteCrisis(CtCrisis aCtCrisis) {

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Delete

			try {
				String sql = "DELETE FROM " + dbName + ".crises WHERE id = ?";
				String id = aCtCrisis.id.value.getValue();

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
	 * Updates a crisis with the CtCrisis information provided.
	 *
	 * @param aCtCrisis Uses the ID to update the rest of the crisis with the details provided
	 */
	static public void updateCrisis(CtCrisis aCtCrisis) {

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");
			/********************/
			//Update

			try {
				log.debug("[DATABASE]-Update crisis");
				String sql = "UPDATE "
						+ dbName
						+ ".crises SET `type` = ?, `status` = ?, `latitude` = ?, `longitude` = ?,"
						+ " `instant` = ?, `comment` = ? WHERE id = ?";
				String id = aCtCrisis.id.value.getValue();
				String type = aCtCrisis.type.toString();
				String status = aCtCrisis.status.toString();
				double latitude = aCtCrisis.location.latitude.value.getValue();
				double longitude = aCtCrisis.location.longitude.value
						.getValue();

				int year = aCtCrisis.instant.date.year.value.getValue();
				int month = aCtCrisis.instant.date.month.value.getValue();
				int day = aCtCrisis.instant.date.day.value.getValue();

				int hour = aCtCrisis.instant.time.hour.value.getValue();
				int min = aCtCrisis.instant.time.minute.value.getValue();
				int sec = aCtCrisis.instant.time.second.value.getValue();

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Calendar calendar = new GregorianCalendar(year, month, day,
						hour, min, sec);
				String instant = sdf.format(calendar.getTime());

				String comment = aCtCrisis.comment.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, type);
				statement.setString(2, status);
				statement.setDouble(3, latitude);
				statement.setDouble(4, longitude);
				statement.setString(5, instant);
				statement.setString(6, comment);
				statement.setString(7, id);
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

}
