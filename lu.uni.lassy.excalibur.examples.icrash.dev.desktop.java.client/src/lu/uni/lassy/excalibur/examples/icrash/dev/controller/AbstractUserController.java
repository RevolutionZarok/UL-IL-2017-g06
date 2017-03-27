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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated.UserType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyAuthenticatedImpl;

/**
 * The Class AbstractUserController, used for generic functions that are done by both administrators and coordinators.
 */
public abstract class AbstractUserController implements HasListeners {
	
	/**
	 * Instantiates a new abstract user controller, this holds the methods that either a coordinator or an administrator should be able to access.
	 *
	 * @param auth The ActProxyAuth class that sets Actor associated with this controller
	 */
	protected AbstractUserController(ActProxyAuthenticated auth){
		this._auth = auth;
	}
	/**
	 * Parameter that has a ProxyActor that allows actors to do functions on the server. These are functions that require users to be
	 * logged in to perform, like the addCoordinator. It will be set at moment of initialisation
	**/
	private ActProxyAuthenticated _auth;
	/**
	 * The method that allows the user to logon.
	 *
	 * @param login The username to logon with
	 * @param password The password to use
	 * @return The success of the method
	 * @throws ServerOfflineException Thrown if the server is currently offline
	 * @throws ServerNotBoundException Thrown if the server hasn't been bound in the RMI settings
	 */
	public PtBoolean oeLogin(String login, String password) throws ServerOfflineException, ServerNotBoundException{
		DtLogin aDtLogin = new DtLogin(new PtString(login));
		DtPassword aDtPassword = new DtPassword(new PtString(password));
		try {
			return this.getAuth().oeLogin(aDtLogin, aDtPassword);
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	/**
	 * The method that allows the user to logoff.
	 *
	 * @return The success of the method
	 * @throws ServerOfflineException Thrown if the server is currently offline
	 * @throws ServerNotBoundException Thrown if the server hasn't been bound in the RMI settings
	 */
	public PtBoolean oeLogout() throws ServerOfflineException, ServerNotBoundException{
		try {
			return this.getAuth().oeLogout();
		} catch (RemoteException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	/**
	 * Gets the type of user that is currently logged on. It will return either a coordinator or a administrator
	 * @return Returns an enum of type UserType, found in the class ActProxyAuthenticatedImpl 
	 */
	public UserType getUserType(){
		try{
			return this._auth.getUserType();
		} catch (Exception e){
			return UserType.Unknown;
		}
	}
	/**
	 * Returns the ActProxyAuthenticated user, which is used to call methods that only a logged in user should be able to do. The user will need casting first,
	 * so use instanceof to confirm user type
	 * @return Returns an implemented ActProxyAuthenticated user type, that can be used to call methods that the user has done. An example is oeHandleCrisis 
	 */
	public ActProxyAuthenticated getAuth(){
		return this._auth;
	}
	/**
	 * Returns the ActProxyAuthenticatedImpl user, which is used to call methods that only a logged in user should be able to do. The user will need casting first,
	 * so use instanceof to confirm user type. This uses the Impl version for getting client specific methods, like the list of messages
	 * @return The client side actor proxy for the implemented authenticated
	 */
	public ActProxyAuthenticatedImpl getAuthImpl(){
		return (ActProxyAuthenticatedImpl) this._auth;
	}
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.controller.HasListeners#removeAllListeners()
	 */
	public void removeAllListeners() throws ServerOfflineException, ServerNotBoundException{
		try{
			_auth.destroy();
		}
		catch(RemoteException e){
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
		} catch (NotBoundException e) {
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
}
