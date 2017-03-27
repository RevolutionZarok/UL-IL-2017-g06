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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
	/**
	 * Creates a properties class, used for reading in the properties file
	 */
	private Properties iCrashProperties = new Properties();
	
	/**
	 * Instantiates a new property utils for accessing the properties file of this java file.
	 */
	private PropertyUtils(){
		Log4JUtils log = Log4JUtils.getInstance();
		InputStream in = null;
		try {
			try{
				in = new FileInputStream(ICrashUtils.ICRASH_PROPERTIES_FILE_EXT);
			} catch(Exception e){
				log.getLogger().warn("Couldn't load external properties file");
				log.getLogger().warn(e.getMessage());
				log.getLogger().info("Loading internal properties file");
				in = this.getClass().getClassLoader().getResourceAsStream(ICrashUtils.ICRASH_PROPERTIES_FILE);
			}		
			iCrashProperties.load(in);
		} catch (IOException e) {
			log.getLogger().error("Couldn't load properties file");
			log.getLogger().error(e.getMessage());
			log.getLogger().error("Using default settings");
		} finally{
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				log.getLogger().error("Can't close property input stream");
				log.getLogger().error(e.getMessage());
			}
		}
	}
	/**
	 * The instance of the class, so there is only one and we all read from the same file
	 */
	private static PropertyUtils instance;
	/**
	 * Gets the singleton instance of this class
	 * @return An instance of the class, if none exisited before, it will create it and then return it
	 */
	public static PropertyUtils getInstance(){
		if (instance == null)
			instance = new PropertyUtils();
		return instance;
	}
	/**
	 * Gets the property from the property file read earlier. If there was an error, the default value is returned
	 * @param propertyName Name of the property to retrieve
	 * @param defaultValue The default value that will be returned in case of missing value or error
	 * @return The value read from the property file, or the default value provided
	 */
	public String getProperty(String propertyName, String defaultValue){
		return iCrashProperties.getProperty(propertyName, defaultValue);
	}
}
