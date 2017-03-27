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
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompanyImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class used to update or retrieve information from the humans tables in the database.
 */
public class DbHumans extends DbAbstract{

	/**
	 * Insert human into the database.
	 *
	 * @param aCtHuman The CtHuman of which to use the information to insert into the database
	 * @param comCompany The communication company that is associated with the human
	 */
	static public void insertHuman(CtHuman aCtHuman, String comCompany){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
				
				String phone = aCtHuman.id.value.getValue();
				String kind = aCtHuman.kind.toString();
	
				log.debug("[DATABASE]-Insert human");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".humans" +
											"(phone,kind,comcompany)" + 
											"VALUES("+"'"+phone+"','"+kind+"','"+comCompany+"')");
				
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
	 * Gets a human details from the database, using the phone number to retrieve the data.
	 *
	 * @param phone The phone number to use to get the data from the database
	 * @return The human data that is retrieved from the database. This could be empty
	 */
	static public CtHuman getHuman(String phone){
		
		CtHuman aCtHuman = new CtHuman();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".humans WHERE phone = " + phone;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) {				
					
					aCtHuman = new CtHuman();
					//human's id
					DtPhoneNumber aId = new DtPhoneNumber(new PtString(res.getString("phone")));
					//human's kind  -> [witness,victim,anonym]
					String theKind = res.getString("kind");
					EtHumanKind aKind = null;
					if(theKind.equals(EtHumanKind.witness.name()))
						aKind = EtHumanKind.witness;
					if(theKind.equals(EtHumanKind.victim.name()))
						aKind = EtHumanKind.victim;
					if(theKind.equals(EtHumanKind.anonym.name()))
						aKind = EtHumanKind.anonym;

					aCtHuman.init(aId,aKind);
					
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
		
		return aCtHuman;

	}
	
	/**
	 * Gets all humans from the database.
	 *
	 * @return A hashtable of the humans using their phone number as a key value
	 */
	static public Hashtable<String, CtHuman> getSystemHumans(){
	
		Hashtable<String, CtHuman> cmpSystemCtHuman = new Hashtable<String, CtHuman>();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".humans ";
				

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				CtHuman aCtHuman = null;
				
				while(res.next()) {				
					
					aCtHuman = new CtHuman();
					
					//human's id
					DtPhoneNumber aId = new DtPhoneNumber(new PtString(res.getString("phone")));
					//human's kind  -> [witness,victim,anonym]
					String theKind = res.getString("kind");
					EtHumanKind aKind = null;
					if(theKind.equals(EtHumanKind.witness.name()))
						aKind = EtHumanKind.witness;
					if(theKind.equals(EtHumanKind.victim.name()))
						aKind = EtHumanKind.victim;
					if(theKind.equals(EtHumanKind.anonym.name()))
						aKind = EtHumanKind.anonym;

					aCtHuman.init(aId,aKind);
					
					//add instance to the hash
					cmpSystemCtHuman.put(aCtHuman.id.value.getValue(), aCtHuman);
					
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
		
		return cmpSystemCtHuman;
		
	}
	
	/**
	 * Gets the associated humans and their communication company.
	 *
	 * @param htActComCompany the ht act com company
	 * @return Hashtable of the humans and their associated communicaiton company. The human is used as the key value
	 */
	static public Hashtable<CtHuman, ActComCompany> getAssCtHumanActComCompany(Hashtable<String, ActComCompany> htActComCompany){
	
		Hashtable<CtHuman, ActComCompany> assCtHumanActComCompany = new Hashtable<CtHuman, ActComCompany>();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".humans "+ 
								"INNER JOIN "+ dbName + ".comcompanies ON "+
								 dbName + ".humans.comcompany = "+ dbName + ".comcompanies.id";
				

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				CtHuman aCtHuman = null;
				ActComCompany aActComCompany = null;
				
				while(res.next()) {				
					
					aCtHuman = new CtHuman();
					
					//human's id
					DtPhoneNumber aId = new DtPhoneNumber(new PtString(res.getString("phone")));
					//human's kind  -> [witness,victim,anonym]
					String theKind = res.getString("kind");
					EtHumanKind aKind = null;
					if(theKind.equals(EtHumanKind.witness.name()))
						aKind = EtHumanKind.witness;
					if(theKind.equals(EtHumanKind.victim.name()))
						aKind = EtHumanKind.victim;
					if(theKind.equals(EtHumanKind.anonym.name()))
						aKind = EtHumanKind.anonym;

					aCtHuman.init(aId,aKind);
					
					//*************************************
					
					aActComCompany = htActComCompany.get(res.getString("name"));
					if (aActComCompany == null){
						int id = 0;
						try{
							id = Integer.parseInt(res.getString("id"));
						}
						catch (NumberFormatException ex){
							//DO NOTHING
						}
						aActComCompany = new ActComCompanyImpl(id, res.getString("name"));
					}
					//add instances to the hash
					assCtHumanActComCompany.put(aCtHuman, aActComCompany);	
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

		
		return assCtHumanActComCompany;
		
	}
	
	/**
	 * Deletes a human from the database.
	 *
	 * @param aCtHuman The human to delete from the database, it will use the ID/phone number to delete the human
	 */
	static public void deleteHuman(CtHuman aCtHuman){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".humans WHERE phone = ?";
				String id = aCtHuman.id.value.getValue();

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
