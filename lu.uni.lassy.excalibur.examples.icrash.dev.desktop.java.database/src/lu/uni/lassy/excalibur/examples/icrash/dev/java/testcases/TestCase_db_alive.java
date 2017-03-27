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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbAbstract;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

import org.apache.log4j.Logger;

/**
 * A class testing the database is accessible.
 */
public class TestCase_db_alive extends DbAbstract {

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

		//Step 1
		log.info("----Step 1-------");

		log.info("Check MySQL connection...");
		conn = null;

		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			log.info("----Step 2-INSERT------");
			try{
				Statement st = conn.createStatement();
				int val = st.executeUpdate("INSERT "+ dbName+ ".comcompanies VALUES('1','tango')");
				val += st.executeUpdate("INSERT "+ dbName+ ".comcompanies VALUES('2','orange')");
				val += st.executeUpdate("INSERT "+ dbName+ ".comcompanies VALUES('3','post')");
				log.debug(val + " row(s) affected");
			}
			catch (SQLException s){
				log.error("SQL statement is not executed " + s);
			}


			/********************/
			//Select
			log.info("----Step 3-SELECT------");
			try{
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery("SELECT * FROM  "+ dbName+ ".comcompanies");
				log.info("ComCompany's ID" + "\t" + "ComCompany's Name");

				while (res.next()) {
					String name = res.getString("id");
					String pass = res.getString("name");
					
					log.info(name + "\t" + pass);
				}

			}
			catch (SQLException s){
				log.error("SQL code does not execute " + s);
			}  



			/********************/
			log.info("----Step 4-DELETE------");
			//Delete
			try{
				//Delete latest inserted element
				String sql = "DELETE FROM comcompanies WHERE id = ?";
				String id = "1";

				PreparedStatement statement = conn.prepareStatement(sql);

				 // Pass a value of a name that will tell the database which
	            // records in the database to be deleted. Remember that when
	            // using a statement object the index parameter is started from
	            // 1 not 0 as in the Java array data type index.
	            statement.setString(1, id);
	             
	            // Tell the statement to execute the command. The executeUpdate()
	            // method for a delete command returns number of records deleted
	            // as the command executed in the database. If no records was
	            // deleted it will simply return 0
	            int rows = statement.executeUpdate();
				log.debug(rows +" row(s) affected");

				//Repeat for id = 2 and 3
				statement.setString(1, "2");
				rows = statement.executeUpdate();
				log.debug(rows +" row(s) affected");

				statement.setString(1, "3");
				rows = statement.executeUpdate();
				log.debug(rows +" row(s) affected");

			}
			catch (SQLException s){
				log.error("SQL statement is not executed "+s);
			}



			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);
		}

	}


}
