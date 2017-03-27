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
package lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

/**
 * The exception ServerOfflineException which is thrown if the server is considered to be offline.
 * It gives the port number and the host address in the error message, so people can diagnose the issue 
 */
public class ServerOfflineException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5358564046214721617L;
	
	/**
	 * Instantiates a new server offline exception.
	 */
	public ServerOfflineException(){
		this._serverAddress = RmiUtils.getInstance().getHost();
		this._portNumber = RmiUtils.getInstance().getPort();
	}
	
	/**  The port number that was used in attempting access. */
	private int _portNumber;
	
	/**  The address of the server that was used in attempting access. */
	private String _serverAddress;
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		return "Cannot reach the server at " + _serverAddress + " using port number " + Integer.toString(_portNumber);
	}
}
