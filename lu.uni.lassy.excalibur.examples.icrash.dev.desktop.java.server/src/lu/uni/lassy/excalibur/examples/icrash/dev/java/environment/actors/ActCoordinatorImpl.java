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
import java.util.Optional;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

/**
 * The Class ActCoordinatorImpl, which creates a server side actor of type coordinator.
 */
public class ActCoordinatorImpl extends ActAuthenticatedImpl implements ActCoordinator {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new server side actor of type coordinator.
	 *
	 * @param n The username of the actor, this is of type DtLogin. It is used in identifying the correct actor when working with their Class type (CtCoordinator)
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActCoordinatorImpl(DtLogin n) throws RemoteException {
		super(n);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeGetCrisisSet(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus)
	 */
	synchronized public PtBoolean oeGetCrisisSet(EtCrisisStatus aEtCrisisStatus) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();
	
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		
	
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeGetCrisisSet sent to system");
		PtBoolean res = iCrashSys_Server.oeGetCrisisSet(aEtCrisisStatus);
			
			
		if(res.getValue() == true)
			log.info("operation oeGetCrisisSet successfully executed by the system");


		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeSetCrisisHandler(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID)
	 */
	synchronized public PtBoolean oeSetCrisisHandler(DtCrisisID aDtCrisisID) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();
	
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
	
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSetCrisisHandler sent to system");
		PtBoolean res = iCrashSys_Server.oeSetCrisisHandler(aDtCrisisID);
			
			
		if(res.getValue() == true)
			log.info("operation oeSetCrisisHandler successfully executed by the system");


		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeValidateAlert(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID)
	 */
	synchronized public PtBoolean oeValidateAlert(DtAlertID aDtAlertID) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();
	
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
	
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeValidateAlert sent to system");
		PtBoolean res = iCrashSys_Server.oeValidateAlert(aDtAlertID);
			
			
		if(res.getValue() == true)
			log.info("operation oeValidateAlert successfully executed by the system");


		return res;
	}
		
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeInvalidateAlert(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID)
	 */
	synchronized public PtBoolean oeInvalidateAlert(DtAlertID aDtAlertID) throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeInvalidateAlert sent to system");
		PtBoolean res = iCrashSys_Server.oeInvalidateAlert(aDtAlertID);


		if(res.getValue() == true)
			log.info("operation oeInvalidateAlert successfully executed by the system");


		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeSetCrisisStatus(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus)
	 */
	synchronized public PtBoolean oeSetCrisisStatus(DtCrisisID aDtCrisisID, EtCrisisStatus aEtCrisisStatus) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();
	
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSetCrisisStatus sent to system");
		PtBoolean res = iCrashSys_Server.oeSetCrisisStatus(aDtCrisisID, aEtCrisisStatus);
			
			
		if(res.getValue() == true)
			log.info("operation oeSetCrisisStatus successfully executed by the system");


		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeSetCrisisType(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType)
	 */
	synchronized public PtBoolean oeSetCrisisType(DtCrisisID aDtCrisisID, EtCrisisType aEtCrisisType) throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSetCrisisType sent to system");
		PtBoolean res = iCrashSys_Server.oeSetCrisisType(aDtCrisisID, aEtCrisisType);


		if(res.getValue() == true)
			log.info("operation oeSetCrisisType successfully executed by the system");


		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeReportOnCrisis(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment)
	 */
	synchronized public PtBoolean oeReportOnCrisis(DtCrisisID aDtCrisisID,DtComment aDtComment) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);
		
		
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeReportOnCrisis sent to system");
		PtBoolean res = iCrashSys_Server.oeReportOnCrisis(aDtCrisisID, aDtComment);
			
			
		if(res.getValue() == true)
			log.info("operation oeReportOnCrisis successfully executed by the system");


		return res;

	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeCloseCrisis(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID)
	 */
	synchronized public PtBoolean oeCloseCrisis(DtCrisisID aDtCrisisID) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeCloseCrisis sent to system");
		PtBoolean res = iCrashSys_Server.oeCloseCrisis(aDtCrisisID);
			
			
		if(res.getValue() == true)
			log.info("operation oeCloseCrisis successfully executed by the system");


		return res;

		
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#ieSendACrisis(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis)
	 */
	public PtBoolean ieSendACrisis(CtCrisis aCtCrisis) {

		Logger log = Log4JUtils.getInstance().getLogger();

		log.info("message ActCoordinator.ieSendACrisis received from system");
		log.info("crisis id '"	+ aCtCrisis.id.value.getValue().toString() + "' "+
				"in status '"+ aCtCrisis.status.toString()+"'");
		
		for(ActProxyAuthenticated aProxy : listeners)
			try {
				if (aProxy instanceof ActProxyCoordinator)
					((ActProxyCoordinator)aProxy).ieSendACrisis(aCtCrisis);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
			}

		return new PtBoolean(true);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#ieSendAnAlert(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert)
	 */
	public PtBoolean ieSendAnAlert(CtAlert aCtAlert) {

		Logger log = Log4JUtils.getInstance().getLogger();

		log.info("message ActCoordinator.ieSendAnAlert received from system");
		log.info("alert id '"	+ aCtAlert.id.value.getValue().toString() + "' "+
				" with comment '"+ aCtAlert.comment.value.getValue() +"'"+
				" in status '"+ aCtAlert.status.toString()+"'");
		
		for(ActProxyAuthenticated aProxy : listeners)
			try {
				if (aProxy instanceof ActProxyCoordinator)
					((ActProxyCoordinator)aProxy).ieSendAnAlert(aCtAlert);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
			}

		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeGetAlertsSet(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus)
	 */
	synchronized public PtBoolean oeGetAlertsSet(EtAlertStatus aEtAlertStatus) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);
		log.info("message ActCoordinator.oeGetAlertsSet sent to system");
		PtBoolean res = iCrashSys_Server.oeGetAlertsSet(aEtAlertStatus);
		if(res.getValue() == true)
			log.info("operation oeGetAlertsSet successfully executed by the system");
		return res;
	}
	
	private Optional<CtCoordinator> getAssociatedCt() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
	 	IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		return iCrashSys_Server.getAllCtCoordinators().stream()
							.filter(ct -> ct.login.eq(getLogin()).getValue())
							.findFirst();
	}
	
	@Override
	public PtBoolean isAuthenticationLocked() throws RemoteException, NotBoundException{
		Optional<CtCoordinator> op = getAssociatedCt();
		if(op.isPresent()){
			return op.get().locked;
		}else{
			return super.isAuthenticationLocked();
		}
	}
	
	@Override
	public PtBoolean setAuthenticationLocked(PtBoolean aLocked) throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
	 	IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
	 	Optional<CtCoordinator> op = getAssociatedCt();
	 	if(op.isPresent()){
	 		CtCoordinator ctc = op.get();
	 		return iCrashSys_Server.oeUpdateCoordinator(ctc.id, ctc.login, ctc.pwd, ctc.mail, aLocked, ctc.resetCode);
	 	}else{
	 		return super.setAuthenticationLocked(aLocked);
	 	}
	}
	
	@Override
	protected PtBoolean notifyAboutLocking() throws RemoteException, NotBoundException{
		Optional<CtCoordinator> op = getAssociatedCt();
		if(op.isPresent()){
			CtCoordinator ctc = op.get();
			
			DtString reset_code = CtCoordinator.generateResetCode();
			Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		 	IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		 	
			if(!iCrashSys_Server.oeUpdateCoordinator(ctc.id, ctc.login, ctc.pwd, ctc.mail, ctc.locked, reset_code).getValue()){
				reset_code = ctc.resetCode;
				Log4JUtils.getInstance().getLogger().info("A new reset code could not be generated...");
			}
			
			PtString message = new PtString(
					"Your coordinator account \"" + ctc.login + "\" for iCrash.FX has been locked in order to guarantee your security. "
					+ "The reason it has been locked is that someone tried several times to log in "
					+ "with your coordinator account without success. As a safety measure, your account "
					+ "has been disabled temporarily.\n\n"
					+ "In order to reactivate your account, you will have to remember the following code:\n"
					+ reset_code + "\n\n"
					+ "To reactivate your account, go to the window you would normally log in with your "
					+ "coordinator credentials. Click then on \"Reactivate account\". A new window will then "
					+ "appear where you have to enter the code sent by this mail. After this, follow the "
					+ "given instructions.\n\n"
					+ "Best regards,\niCrash.FX Service Team"
					);
			return ActMailingServiceImpl.getInstance().ieSendMail(ctc.mail, new PtString("Your iCrash.FX coordinator account has been disabled"), message);
		}else{
			return super.notifyAboutLocking();
		}
	}

	@Override
	public PtBoolean oeTryPasswordReset(DtLogin aLogin, DtString aResetCode, DtPassword aNewPwd)
			throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
	 	IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
	 	return iCrashSys_Server.oeTryPasswordReset(aLogin, aResetCode, aNewPwd);
	}

	@Override
	public PtBoolean oeSendResetCodePerMail(DtLogin aLogin) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
	 	IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
	 	return iCrashSys_Server.oeSendResetCodePerMail(aLogin);
	}
}
