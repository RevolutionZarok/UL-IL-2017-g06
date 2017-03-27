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
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class DbCoordinators for updating or retrieving information on the coordinators table.
 */
public class DbCoordinators extends DbAbstract{

	/**
	 * Creates a new Coordinator in the database using the data from the CtCoordinator passed.
	 *
	 * @param aCtCoordinator The CtCoordinator of which to use the data of to create the row in the database
	 */
	static public void insertCoordinator(CtCoordinator aCtCoordinator){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
				
				String id = aCtCoordinator.id.value.getValue();
				String login =  aCtCoordinator.login.value.getValue();
				String pwd =  aCtCoordinator.pwd.value.getValue();
	
				log.debug("[DATABASE]-Insert coordinator");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".coordinators" +
											"(id,login,pwd)" + 
											"VALUES("+"'"+id+"'"+",'"+login+"','"+pwd+"')");
				
				log.debug(val + " row affected");
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}

	
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
	
	/**
	 * Gets a coordinator details from the database, depending on the ID used and creates a CtCoordinator class to house the data.
	 *
	 * @param coordId The ID of which Coordinator to get the data of
	 * @return The coordinator that holds the data retrieved. It could be an empty class!
	 */
	static public CtCoordinator getCoordinator(String coordId){
		
		CtCoordinator aCtCoordinator = new CtCoordinator();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".coordinators WHERE id = " + coordId;
				

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) {				
					
					aCtCoordinator = new CtCoordinator();
					//coordinator's id
					DtCoordinatorID aId = new DtCoordinatorID(new PtString(res.getString("id")));
					//coordinator's login
					DtLogin aLogin = new DtLogin(new PtString(res.getString("login")));
					//coordinator's pwd
					DtPassword aPwd = new DtPassword(new PtString(res.getString("pwd")));

					aCtCoordinator.init(aId, aLogin,aPwd);
					
				}
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			logException(e);
		}
		
		return aCtCoordinator;

	}
	
	/**
	 * Delete a coordinator from the database.
	 *
	 * @param aCtCoordinator The coordinator to delete from the database
	 */
	static public void deleteCoordinator(CtCoordinator aCtCoordinator){
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".coordinators WHERE id = ?";
				String id = aCtCoordinator.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows+" row deleted");				
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}


			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
	
	/**
	 * Updates a coordinator with the data passed. It uses the ID to update the details in the database
	 *
	 * @param aCtCoordinator The coordinator to update
	 * @return the pt boolean
	 */
	static public PtBoolean updateCoordinator(CtCoordinator aCtCoordinator){
		PtBoolean success = new PtBoolean(false);
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//edit
			
			try{
				Statement st = conn.createStatement();
				String id = aCtCoordinator.id.value.getValue();
				String login =  aCtCoordinator.login.value.getValue();
				String pwd =  aCtCoordinator.pwd.value.getValue();
				String statement = "UPDATE "+ dbName+ ".coordinators" +
						" SET pwd='"+pwd+"',  login='"+ login+"' " +
						"WHERE id='"+id+"'";
				int val = st.executeUpdate(statement);
				log.debug(val+" row updated");
				success = new PtBoolean(val == 1);
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
		return success;
	}
	
	/**
	 * Gets a list of all coordinators from the database. It's stored in a hashtable of the ID and the coordinator class
	 *
	 * @return Return a hashtable of coordinator ID's and their class
	 */
	static public Hashtable<String, CtCoordinator> getSystemCoordinators(){
		Hashtable<String, CtCoordinator> cmpSystemCtCoord = new Hashtable<String, CtCoordinator>();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".coordinators ";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtCoordinator aCtCoord = null;

				while (res.next()) {

					aCtCoord = new CtCoordinator();
					//alert's id
					DtCoordinatorID aId = new DtCoordinatorID(new PtString(
							res.getString("id")));
					DtLogin aLogin = new DtLogin(new PtString(res.getString("login")));
					DtPassword aPwd = new DtPassword(new PtString(res.getString("pwd")));
					//init aCtAlert instance
					aCtCoord.init(aId, aLogin, aPwd);
					
					//add instance to the hash
					cmpSystemCtCoord
							.put(aCtCoord.id.value.getValue(), aCtCoord);
				}
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return cmpSystemCtCoord;
	}
	

}
