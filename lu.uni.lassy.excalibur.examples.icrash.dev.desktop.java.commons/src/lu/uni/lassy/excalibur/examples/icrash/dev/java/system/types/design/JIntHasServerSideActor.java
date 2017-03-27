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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The Interface HasServerSideActor, means an Actor has an associated server side actor. If the window is closed, we should destroy the connection.
 */
public interface JIntHasServerSideActor {
	
	/**
	 * Destroy will terminate the connection with the server side actor, by removing the associated listener.
	 *
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in the RMI settings
	 */
	public void destroy() throws RemoteException, NotBoundException;
}
