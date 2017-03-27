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
package lu.uni.lassy.excalibur.examples.icrash.dev.model;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

/**
 * The Class Server that allows access to the server without using an actor.
 * Should only be used if the actor is not required like getting data, but not modifying it.
 */
public class Server {
	
	/** The singleton of the server type, enforces that only one version of this class can be created. */
	private static Server singleton = new Server();
	
	/**
	 * Instantiates a new server.
	 */
	private Server(){
		System.setProperty("java.rmi.server.hostname", RmiUtils.getInstance().getHostClient());
	}
	
	/**
	 * Gets the single instance of Server.
	 *
	 * @return single instance of Server
	 */
	public static Server getInstance(){
		return singleton;
	}
	
	/** The _registry, which allows server lookup. */
	private Registry _registry;
	
	/**
	 * Connects to the server.
	 *
	 * @throws RemoteException If the server is down, this exception will be thrown
	 */
	private void connectServer() throws RemoteException{
		Log4JUtils.getInstance().getLogger().debug("Connecting registry");
		Log4JUtils.getInstance().getLogger().debug("Address: " +RmiUtils.getInstance().getHost());
		Log4JUtils.getInstance().getLogger().debug("Port: " +RmiUtils.getInstance().getPort());
		_registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(), RmiUtils.getInstance().getPort());
		Log4JUtils.getInstance().getLogger().debug("Registry connected");
	}
	
	/**
	 * Registry lookup.
	 *
	 * @param objectName the object name
	 * @return the object
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 * @throws RemoteException Thrown if the server is offline
	 */
	private Object registryLookup(String objectName) throws NotBoundException, RemoteException{
		if (_registry == null)
			connectServer();
		try {
			Log4JUtils.getInstance().getLogger().debug("Attempting RMI lookup");
			Object obj = _registry.lookup(objectName);
			Log4JUtils.getInstance().getLogger().debug("RMI lookup successful");
			return obj;
		} catch (RemoteException e) {
			//Remote exception at this point means the server has gone down. Let's try to reconnect
			connectServer();
			//If we get the error again, let's report it to the user
			return _registry.lookup(objectName);
		}
	}
	
	/**
	 * Gets the server environment and all of it's associated remote methods
	 *
	 * @return the iCrash environment retrieved from the server
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 * @throws RemoteException Thrown if the server is offline
	 */
	public IcrashEnvironment env() throws NotBoundException, RemoteException{
		return (IcrashEnvironment)registryLookup("iCrashEnvironment");
	}
	
	/**
	 * Gets the server system and all of it's associated remote methods
	 *
	 * @return the iCrash system retrieved from the server
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 * @throws RemoteException Thrown if the server is offline
	 */
	public IcrashSystem sys() throws NotBoundException, RemoteException{
		return (IcrashSystem)registryLookup("iCrashServer");
	}
	
	/**
	 * Disconnects this class from the server
	 */
	public void disconnectServer(){
		_registry = null;
	}
}

