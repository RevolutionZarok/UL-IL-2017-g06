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

package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaResponse;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;


/**
 * The Class ActAuthenticatedImpl, which creates a server side actor of type authenticated.
 * It is an abstract class, so must be created by another actor, like the coordinator
 */
public abstract class ActAuthenticatedImpl extends UnicastRemoteObject
		implements ActAuthenticated {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/** The login of the class type associated with this actor. */
	private DtLogin login;
	
	/** The internal counter for login attempts */
	private PtInteger loginCounter = new PtInteger(0);
	
	private PtBoolean exceptCaptcha = new PtBoolean(false);
	
	/**
	 * Instantiates a new server side actor of type authenticated.
	 *
	 * @param n The username that is associated with this authenticated actor. This helps tracking actors and their associated class type (Ct...)
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActAuthenticatedImpl(DtLogin n) throws RemoteException {
		super(RmiUtils.getInstance().getPort());
		this.login = n;
		
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated#getName()
	 */
	public DtLogin getLogin() {
		return login;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated#oeLogin(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword)
	 */
	synchronized public PtBoolean oeLogin(DtLogin aDtLogin,
			DtPassword aDtPassword) throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);
		
		if(isAuthenticationLocked().getValue()){
			log.info("operation oeLogin refusing to be executed due to too many failed login attempts");
			ieMessage(new PtString("Your account is blocked from further login attempts. Please contact an administrator to unblock it."));
			return new PtBoolean(false);
		}else if(loginCounter.getValue() >= 3 && !exceptCaptcha.getValue()){
			log.info("operation oeLogin failed more than 3 times. A captcha test is now imposed. (Attempt #" + loginCounter.getValue() + ")");
			iCrashSys_Server.setCurrentRequestingAuthenticatedLogin(aDtLogin);
			iCrashSys_Server.setCurrentRequestingAuthenticatedPassword(aDtPassword);
			ActCaptchaServiceImpl.getInstance().ieGenerateGaptcha();
			return new PtBoolean(false);
		}

		log.info("message ActAuthenticated.oeLogin sent to system");
		
		PtBoolean res = iCrashSys_Server.oeLogin(aDtLogin, aDtPassword);

		//Modify here for captcha
		
		if (res.getValue() == true){
			log.info("operation oeLogin successfully executed by the system");
			loginCounter = new PtInteger(0);
		}else{
			loginCounter = new PtInteger(loginCounter.getValue() + 1);
			log.info("operation oeLogin failed, this was the attempt #" + loginCounter.getValue());
			if(loginCounter.getValue() >= 8){
				setAuthenticationLocked(new PtBoolean(true));
				notifyAboutLocking();
			}
		}
		exceptCaptcha = new PtBoolean(false);
		
		return res;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated#oeLogout()
	 */
	synchronized public PtBoolean oeLogout() throws RemoteException,
			NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAuthenticated.oeLogout sent to system");
		PtBoolean res = iCrashSys_Server.oeLogout();

		if (res.getValue() == true)
			log.info("operation oeLogout successfully executed by the system");

		return res;
	}
	
	/** A list of listeners associated with this actor. */
	protected List<ActProxyAuthenticated> listeners = new ArrayList<ActProxyAuthenticated>();

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated#addListener(lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated)
	 */
	synchronized public void addListener(
			ActProxyAuthenticated aActProxyAuthenticated)
			throws RemoteException, NotBoundException {
		listeners.add(aActProxyAuthenticated);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated#removeListener(lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated)
	 */
	synchronized public void removeListener(
			ActProxyAuthenticated aActProxyAuthenticated)
			throws RemoteException, NotBoundException {
		listeners.remove(aActProxyAuthenticated);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAuthenticated#ieMessage(lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString)
	 */
	public PtBoolean ieMessage(PtString aMessage) {
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("message ActAuthenticated.ieMessage received from system");
		log.info("ieMessage is: " + aMessage.getValue());
		log.info("Message being sent to " + this.login.value.getValue());
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				aProxy.ieMessage(aMessage);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}
	
	@Override
	public PtBoolean ieConfirmCaptcha(CtCaptcha captcha){
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("message ActAuthenticated.ieConfirmCaptcha received from system");
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				aProxy.ieConfirmCaptcha(captcha);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean oeSubmitCaptcha(DtCaptchaResponse aResponse) throws RemoteException, NotBoundException{
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("actAuthenticated has submitted an answer to a captcha test");
		exceptCaptcha = new PtBoolean(true);
		if(!ActCaptchaServiceImpl.getInstance().ieVerifyCaptcha(aResponse).getValue()){
			loginCounter = new PtInteger(loginCounter.getValue() + 1);
			exceptCaptcha = new PtBoolean(false);
		}
		return new PtBoolean(true);
	}
	
	@Override
	public PtBoolean ieCaptchaAuthenticationSucceeded() throws RemoteException, NotBoundException{
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				aProxy.ieCaptchaAuthenticationSucceeded();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}

	protected PtBoolean isAuthenticationLocked() throws RemoteException, NotBoundException{
		return new PtBoolean(loginCounter.getValue() >= 8);
	}

	protected PtBoolean setAuthenticationLocked(PtBoolean aLocked) throws RemoteException, NotBoundException{
		return new PtBoolean(false);
	}
	
	protected PtBoolean notifyAboutLocking() throws RemoteException, NotBoundException{
		return new PtBoolean(true);
	}
	
	public PtBoolean notifyAboutUnlocking(){
		this.loginCounter = new PtInteger(0);
		return new PtBoolean(true);
	}
	
}