package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCaptchaImage;
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
		
		CtCaptchaImage images[] = new CtCaptchaImage[]{
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_0.jpg")),
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_1.jpg")),
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_2.jpg")),
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_3.jpg")),
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_4.jpg")),
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_5.jpg")),
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_6.jpg")),
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_7.jpg")),
				new CtCaptchaImage(new PtString("http://kremi151.square7.ch/captcha/captcha_8.jpg"))
		};
		
		CtCaptcha captchaTest = new CtCaptcha(id, new PtString("Select the pictures showing a dog"), images);//TODO: Captcha -> make variable
		DtCaptchaResponse captchaAnswer = new DtCaptchaResponse(id, new PtString(buildBinaryAnswerString(new boolean[]{false, true, true, false, false, false, false, true, true})));//TODO: Captcha -> make variable

		log.info("Saving captcha test and answer...");
		responseMap.register(captchaAnswer);
		
		iCrashSys_Server.oeSendCaptcha(captchaTest);
		return new PtBoolean(true);
	}
	
	private String buildBinaryAnswerString(boolean answer[]){
		if(answer.length != 9){
			throw new IllegalArgumentException();
		}
		StringBuilder sb = new StringBuilder();
		int currentChar = 0;
		for(int i = 0 ; i < 9 ; i++){
			currentChar |= (answer[i] ? (1 << i) : 0);
		}
		sb.append((char)(currentChar & 255));
		sb.append((char)((currentChar >> 8) & 255));
		return sb.toString();
	}

	@Override
	public PtBoolean ieVerifyCaptcha(DtCaptchaResponse AdtCaptchaResponse) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		DtCaptchaResponse master = responseMap.get(AdtCaptchaResponse.getId());
		if(master.is().getValue()){
			if(master.compare(AdtCaptchaResponse).getValue()){
				oeCaptchaValid(master.getId());
				return new PtBoolean(true);
			}else{
				oeCaptchaInvalid(master.getId());
				return new PtBoolean(false);
			}
		}else{
			log.info("Unknown captcha test answer received");
		}
		return new PtBoolean(false);//TODO: Messir: Return true if captcha answer is correct
	}

	@Override
	public PtBoolean oeSendCaptcha(CtCaptcha AdtCaptcha) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		log.info("Sending ieConfirmCaptcha to actAuthenticated");
		return iCrashSys_Server.getCurrentRequestingAuthenticatedActor().ieConfirmCaptcha(AdtCaptcha);
	}

	@Override
	public PtBoolean oeCaptchaInvalid(DtCaptchaId AdtCaptchaId) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("Captcha answer is invalid");
		
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		iCrashSys_Server.getCurrentRequestingAuthenticatedActor().ieMessage(new PtString("Your submitted captcha response is invalid. Try again."));
		
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean oeCaptchaValid(DtCaptchaId AdtCaptchaId) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("Captcha answer is valid, retrying login attempt...");
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		iCrashSys_Server.getCurrentRequestingAuthenticatedActor().oeLogin(
				iCrashSys_Server.getCurrentRequestingAuthenticatedLogin(),
				iCrashSys_Server.getCurrentRequestingAuthenticatedPassword()
				);
		
		return new PtBoolean(true);
	}
	
	public static ActCaptchaService getInstance() throws RemoteException{//TODO: RMI
		if(instance == null){
			instance = new ActCaptchaServiceImpl();
		}
		return instance;
	}

}
