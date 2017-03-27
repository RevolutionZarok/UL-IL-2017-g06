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
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;

/**
 * The Interface HasListeners, used when a controller has listeners that it should destroy when the client window is being closed.
 */
public interface HasListeners {
	
	/**
	 * Removes all the listeners associated with this window from the server.
	 * This helps shut down the system and means there should be no left over processes running
	 *
	 * @throws ServerOfflineException Thrown if the server is offline
	 * @throws ServerNotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public void removeAllListeners() throws ServerOfflineException, ServerNotBoundException;
}
