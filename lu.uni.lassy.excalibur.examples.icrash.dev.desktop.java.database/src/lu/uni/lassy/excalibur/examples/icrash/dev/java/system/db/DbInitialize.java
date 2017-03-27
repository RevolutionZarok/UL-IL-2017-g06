/*******************************************************************************
 * Copyright (c) 2016 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *    
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ScriptRunner;

public class DbInitialize extends DbAbstract {


	static public void initializeDatabase(File file) {
		Logger log = Log4JUtils.getInstance().getLogger();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url,userName,password);
			log.debug("Connected to the database");
			
			ScriptRunner runner = new ScriptRunner(conn, false, false);
			runner.runScript(new BufferedReader(new FileReader(file)));
			
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}






}
