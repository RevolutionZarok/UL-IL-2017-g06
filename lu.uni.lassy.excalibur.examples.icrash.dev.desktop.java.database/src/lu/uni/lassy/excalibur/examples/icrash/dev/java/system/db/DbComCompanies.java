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

/**
 * The class for changing details of the ComCompanies on the database.
 */
public class DbComCompanies extends DbAbstract {

	/**
	 * Inserts a communication company into the database.
	 *
	 * @param idComCompany The ID of the communication company to create
	 * @param nameComCompany The name of communication company to create
	 */
	static public void insertComCompany(String idComCompany, String nameComCompany){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
	
				log.debug("[DATABASE]-Insert ComCompany");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".comcompanies" +
											"(id,name)" + 
											"VALUES("+"'"+idComCompany+"','"+nameComCompany+"')");
				
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
	 * Gets a communication company's name from the database from the ID provided.
	 *
	 * @param idComCompany The ID of the communication company to retrieve the name of
	 * @return The name of the communication company that was retrieved from the database
	 */
	static public String getComCompanyName(String idComCompany){
		
		String nameComCompany = "";
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".comcompanies WHERE id = " + idComCompany;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) 				
					nameComCompany = res.getString("name");
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			logException(e);
		}
				
		return nameComCompany;

	}
	/**
	 * Gets a communication company's ID from the database from the name provided.
	 *
	 * @param nameComCompany The name of the communication company to retrieve the ID of
	 * @return The ID of the communication company that was retrieved from the database
	 */
	static public String getComCompanyID(String nameComCompany){
		
		String idComCompany = "";
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".comcompanies WHERE name = '" + nameComCompany +"'";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) 				
					idComCompany = res.getString("id");
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			logException(e);
		}
				
		return idComCompany;

	}
	/**
	 * Deletes a communication company from the database.
	 *
	 * @param idComCompany The ID of the communication company to delete
	 */
	static public void deleteComCompany(String idComCompany){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".comcompanies WHERE id = ?";
				String id = idComCompany;

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
	
	
	
}
