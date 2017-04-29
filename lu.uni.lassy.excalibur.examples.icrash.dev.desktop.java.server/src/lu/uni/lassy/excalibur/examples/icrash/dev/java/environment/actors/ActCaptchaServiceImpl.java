package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaId;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaResponse;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaResponseMap;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

public class ActCaptchaServiceImpl extends UnicastRemoteObject implements ActCaptchaService{

	private static final long serialVersionUID = -5086057999828832836L;
	
	private DtCaptchaResponseMap responseMap = new DtCaptchaResponseMap();
	private static ActCaptchaServiceImpl instance;//TODO: RMI

	private ActCaptchaServiceImpl() throws RemoteException {
		super(RmiUtils.getInstance().getPort());
	}

	@Override
	public PtBoolean ieGenerateGaptcha() throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		
		log.info("Generating captcha test...");
		DtCaptchaId id = new DtCaptchaId(new PtInteger((int)(Integer.MAX_VALUE * Math.random())));
		DtCaptcha captchaTest = new DtCaptcha(id, new PtString("Select all the dogs"));//TODO: Captcha -> make variable
		DtCaptchaResponse captchaAnswer = new DtCaptchaResponse(id, new PtString("answer"));//TODO: Captcha -> make variable

		log.info("Saving captcha test and answer...");
		responseMap.register(captchaAnswer);
		
		iCrashSys_Server.oeSendCaptcha(captchaTest);
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean ieVerifyCaptcha(DtCaptchaResponse AdtCaptchaResponse) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PtBoolean oeSendCaptcha(DtCaptcha AdtCaptcha) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		return iCrashSys_Server.getCurrentRequestingAuthenticatedActor().ieConfirmCaptcha(AdtCaptcha);
	}

	@Override
	public PtBoolean oeCaptchaInvalid(DtCaptchaId AdtCaptchaId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PtBoolean oeCaptchaValid(DtCaptchaId AdtCaptchaId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static ActCaptchaService getInstance() throws RemoteException{//TODO: RMI
		if(instance == null){
			instance = new ActCaptchaServiceImpl();
		}
		return instance;
	}

}
